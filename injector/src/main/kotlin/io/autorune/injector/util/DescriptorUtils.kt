package io.autorune.injector.util

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.objectweb.asm.Type

object DescriptorUtils {

    fun getClassJsonFromDesc(typeDesc: String, classes: JsonArray): JsonObject? {

        var type = Type.getType(typeDesc)

        var baseType = type.baseType

        var typeString = baseType.className

        if (baseType.sort == Type.OBJECT && !typeString.contains("."))
            return classes.first { it.asJsonObject.get("obf_name").asString == typeString }.asJsonObject

        return null

    }

    fun getMethodParamJsons(paramsDesc: String, classes: JsonArray): List<JsonObject> {

        var paramsDescM = paramsDesc

        val paramList = mutableListOf<JsonObject>()

        while (paramsDescM.isNotEmpty()) {

            var paramDesc = paramsDescM.substring(0, 1)

            while (paramDesc.endsWith("["))
                paramDesc = paramsDescM.substring(0, paramDesc.length+1)

            if (paramDesc.endsWith("L"))
                paramDesc = paramsDescM.substring(0, paramsDescM.indexOfFirst { it == ';' }+1)

            val classJson = getClassJsonFromDesc(paramDesc, classes)

            if (classJson != null && paramList.none { it.get("obf_name") == classJson.get("obf_name") })
                paramList.add(classJson)

            paramsDescM = paramsDescM.substring(paramDesc.length)

        }

        return paramList

    }

    fun getParamStrings(paramsDesc: String): List<String> {

        var paramsDescM = paramsDesc

        val paramList = mutableListOf<String>()

        while (paramsDescM.isNotEmpty()) {

            var paramDesc = paramsDescM.substring(0, 1)

            while (paramDesc.endsWith("["))
                paramDesc = paramsDescM.substring(0, paramDesc.length+1)

            if (paramDesc.endsWith("L"))
                paramDesc = paramsDescM.substring(0, paramsDescM.indexOfFirst { it == ';' }+1)

            paramList.add(paramDesc)

            paramsDescM = paramsDescM.substring(paramDesc.length)

        }

        return paramList

    }

    fun getParamCount(paramsDesc: String): Int {

        var paramsDescM = paramsDesc

        var paramCount = 0

        while (paramsDescM.isNotEmpty()) {

            var paramDesc = paramsDescM.substring(0, 1)

            while (paramDesc.endsWith("["))
                paramDesc = paramsDescM.substring(0, paramDesc.length+1)

            if (paramDesc.endsWith("L"))
                paramDesc = paramsDescM.substring(0, paramsDescM.indexOfFirst { it == ';' }+1)

            paramCount++

            paramsDescM = paramsDescM.substring(paramDesc.length)

        }

        return paramCount

    }

}