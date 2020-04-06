package io.autorune.updater.hooks

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.autorune.updater.analyzer.classes.ClassAnalyzer

object ClassHooks {

    fun generate(classAnalyzers: List<ClassAnalyzer>) : JsonArray {

        val classHooksJsonArray = JsonArray()

        val clientClassPendingFieldHooks = JsonArray()
        val clientClassPendingMethodHooks = JsonArray()

        for (analyzer in classAnalyzers) {

            val classHookJson = JsonObject()

            val packageString = analyzer.javaClass.`package`.toString()

            var cleanPackageString = packageString.split("implementations")[1]

            classHookJson.addProperty("package", cleanPackageString)
            classHookJson.addProperty("obf_name", analyzer.classNode.name)
            classHookJson.addProperty("ref_name", analyzer.analyzerName)
            classHookJson.addProperty("super_name", analyzer.classNode.superName)
            classHookJson.addProperty("interfaces", analyzer.classNode.interfaces.joinToString(":"))

            val classFieldHooksJsonArray = JsonArray()

            for (field in analyzer.fields) {

                val fieldHookJson = JsonObject()
                fieldHookJson.addProperty("obf_name", field.fieldNode.name)
                fieldHookJson.addProperty("ref_name", field.fieldName)
                fieldHookJson.addProperty("ret_type", field.fieldNode.desc)
                fieldHookJson.addProperty("owner", field.owner)
                fieldHookJson.addProperty("access", field.fieldNode.access)

                val multiplier = field.multiplier
                val multiplierType = if (multiplier == null) "none" else if (multiplier is Int) "int" else "long"

                fieldHookJson.addProperty("multiplier_type", multiplierType )
                fieldHookJson.addProperty("multiplier", multiplier?.toString() ?: "none")

                if (field.isClientMember)
                    clientClassPendingFieldHooks.add(fieldHookJson)
                else
                    classFieldHooksJsonArray.add(fieldHookJson)

            }

            val classMethodHooksJsonArray = JsonArray()

            for (method in analyzer.methods) {

                val methodHookJson = JsonObject()
                methodHookJson.addProperty("obf_name", method.methodNode.name)
                methodHookJson.addProperty("ref_name", method.newMethodName)
                methodHookJson.addProperty("owner", method.owner)
                methodHookJson.addProperty("access", method.methodNode.access)
                methodHookJson.addProperty("descriptor", method.methodNode.desc)

                if (method.isClientMember)
                    clientClassPendingMethodHooks.add(methodHookJson)
                else
                    classMethodHooksJsonArray.add(methodHookJson)

            }

            classHookJson.add("field_hooks", classFieldHooksJsonArray)
            classHookJson.add("method_hooks", classMethodHooksJsonArray)

            classHooksJsonArray.add(classHookJson)

        }

        val clientClassHook = classHooksJsonArray.first { it.asJsonObject.get("ref_name").asString == "Client" }.asJsonObject

        val clientClassFieldHooks = clientClassHook.get("field_hooks").asJsonArray
        val clientClassMethodHooks = clientClassHook.get("method_hooks").asJsonArray

        clientClassFieldHooks.addAll(clientClassPendingFieldHooks)
        clientClassMethodHooks.addAll(clientClassPendingMethodHooks)

        return classHooksJsonArray

    }

}