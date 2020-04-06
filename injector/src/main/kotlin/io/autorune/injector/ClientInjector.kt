package io.autorune.injector

import com.google.gson.JsonObject
import io.autorune.injector.transform.CallbackTransform
import io.autorune.injector.type.mixin.MixinInjector
import io.autorune.injector.transform.ReflectionTransform
import io.autorune.injector.type.ClassInjector
import io.autorune.injector.type.FieldInjector
import io.autorune.injector.type.MethodInjector
import io.autorune.injector.util.DescriptorUtils
import org.objectweb.asm.tree.ClassNode

object ClientInjector
{

    fun runInjection(classPool: List<ClassNode>, mixinClassPool: List<ClassNode>, hooksJson: JsonObject)
    {

        val classes = hooksJson.getAsJsonArray("class_hooks")

        val clientClassNode = classPool.first { it.name == "client" }

        val clientInstanceFieldHook = classes.flatMap { it.asJsonObject.getAsJsonArray("field_hooks") }.first { it.asJsonObject.get("ref_name").asString == "clientInstance" }.asJsonObject

        classes.forEach { clazz ->

            val classJson = clazz.asJsonObject
            val obfClassName = classJson.get("obf_name").asString
            val refClassName = classJson.get("ref_name").asString

            val owningClassNode = classPool.first { it.name == obfClassName }

            ClassInjector(classJson, owningClassNode).runInjection()

            val fieldHooks = classJson.getAsJsonArray("field_hooks")
            val methodHooks = classJson.getAsJsonArray("method_hooks")

            for (field in fieldHooks) {
                val fieldJson = field.asJsonObject
                val retDesc = fieldJson.get("ret_type").asString
                FieldInjector(fieldJson, owningClassNode, DescriptorUtils.getClassJsonFromDesc(retDesc, classes)).runInjection()
            }

            //Inject clientInstanceHook into all classes
            if (owningClassNode != clientClassNode)
                FieldInjector(clientInstanceFieldHook, owningClassNode, DescriptorUtils.getClassJsonFromDesc(clientInstanceFieldHook.get("ret_type").asString, classes), true).runInjection()

            for (method in methodHooks) {
                val methodJson = method.asJsonObject
                val descriptor = methodJson.get("descriptor").asString
                val retTypeDesc = descriptor.split(")")[1]
                var paramsDesc = descriptor.split("(")[1].split(")")[0]
                MethodInjector(methodJson, owningClassNode, DescriptorUtils.getClassJsonFromDesc(retTypeDesc, classes),
                        DescriptorUtils.getMethodParamJsons(paramsDesc, classes)).runInjection()
            }

            val mixinClassNode = mixinClassPool.find { it.name.split("/").last().replace("Mixin", "") == refClassName }

            if (mixinClassNode != null)
                MixinInjector(classJson, mixinClassNode, owningClassNode, classes, classPool).runInjection()

            owningClassNode.visitEnd()

        }

        val reflectMethodJson = classes.first {
            it.asJsonObject.get("ref_name").asString == "Client"
        }.asJsonObject.getAsJsonArray("method_hooks").first { it.asJsonObject.get("ref_name").asString == "loadClassFromDescriptor" }.asJsonObject
        val reflectClassNode = classPool.first { it.name == reflectMethodJson.get("owner").asString }

        //Reflection Spoof Injection
        ReflectionTransform.injectTransform(reflectClassNode, clientClassNode)

        CallbackTransform.injectTransform(clientClassNode)

    }

}