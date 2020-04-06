package io.autorune.osrs.generator.api.type

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.squareup.javapoet.*
import io.autorune.osrs.generator.api.Generator
import org.objectweb.asm.tree.ClassNode
import java.nio.file.Paths
import javax.lang.model.element.Modifier

/**
 * @author Chris
 * Contains the logic for generating a class in the osrs-api
 * @param classJson : [JsonObject] The class hook for the class being generated
 * @param classes : [JsonArray] Contains all class hooks for reference
 * @param mixinClassNode : [ClassNode] The mixin class node related to the class being generated if it exists
 * @param clientInstanceHook : [JsonObject] The field hook for clientInstance is used in all classes
 */
class ClassGenerator(
        private val classJson: JsonObject, private val classes: JsonArray, private val mixinClassNode: ClassNode?, private val clientInstanceHook: JsonObject
) : Generator()
{

    /**
     * The generation logic for one class in the osrs-api
     */
    override fun generate() {

        val pkg = classJson.get("package").asString
        val refName = classJson.get("ref_name").asString
        val superName = classJson.get("super_name").asString
        var interfaces = listOf<String>()
        if (classJson.get("interfaces").asString.isNotEmpty())
            interfaces = classJson.get("interfaces").asString.split(":")

        val fieldHooks = classJson.get("field_hooks").asJsonArray
        val methodHooks = classJson.get("method_hooks").asJsonArray

        val basePackageName = "io.autorune.osrs.api"

        val typeSpec = TypeSpec.interfaceBuilder(refName).addModifiers(Modifier.PUBLIC)

        if (classes.any {  it.asJsonObject.get("obf_name").asString == superName }) {
            val superClass = classes.first { it.asJsonObject.get("obf_name").asString == superName }.asJsonObject
            val superPkg = superClass.get("package").asString
            val superRefName = superClass.get("ref_name").asString
            typeSpec.addSuperinterface(ClassName.get(basePackageName + superPkg, superRefName))
        }

        for (inter in interfaces) {
            if (!inter.contains("/") && classes.none {  it.asJsonObject.get("obf_name").asString == inter })
                continue
            val interDesc = String.format("L%s;", inter)
            val typeName = getPoetType(interDesc, classes, basePackageName)
            typeSpec.addSuperinterface(typeName)
        }

        for (field in fieldHooks) {
            val fieldJson = field.asJsonObject
            FieldGenerator(typeSpec, fieldJson, classes, basePackageName).generate()
        }

        if (refName != "Client")
            FieldGenerator(typeSpec, clientInstanceHook, classes, basePackageName, true).generate()

        for (method in methodHooks) {
            val methodJson = method.asJsonObject
            MethodGenerator(typeSpec, methodJson, classes, basePackageName).generate()
        }

        if (mixinClassNode != null)
            MixinGenerator(typeSpec, mixinClassNode).generate()

        val fullPackageName = basePackageName + pkg

        val basePath = "osrs-api/src/main/java/"

        JavaFile.builder(fullPackageName, typeSpec.build())
                .indent("\t")
                .build()
                .writeTo(Paths.get(basePath))

    }

}