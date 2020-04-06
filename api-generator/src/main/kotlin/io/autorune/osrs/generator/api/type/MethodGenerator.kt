package io.autorune.osrs.generator.api.type

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeSpec
import io.autorune.osrs.generator.api.Generator
import javax.lang.model.element.Modifier

/**
 * @author Chris
 * Contains the logic for generating a method for a class in the osrs-api
 * @param typeSpec : [TypeSpec.Builder] The type spec builder for the class owning the method
 * @param methodJson : [JsonObject] The method hook for the method being generated
 * @param classes : [JsonArray] Contains all class hooks for reference
 * @param basePackageName : [String] The package name of the owning class used to get poet type for the parameters
 */
class MethodGenerator(
        private val typeSpec: TypeSpec.Builder, private val methodJson: JsonObject, private val classes: JsonArray, private val basePackageName: String
) : Generator()
{

    /**
     * The generation logic for one method in the osrs-api
     */
    override  fun generate() {

        typeSpec.addMethod(getMethodSpec())

    }

    /**
     * Returns a [MethodSpec] for the method built from the [MethodGenerator] parameters.
     */
    private fun getMethodSpec() : MethodSpec?
    {

        val refName = methodJson.get("ref_name").asString
        val retTypeDesc = methodJson.get("descriptor").asString.split(")")[1]
        var paramsDesc = methodJson.get("descriptor").asString.split("(")[1].split(")")[0]

        val retTypeName = getPoetType(retTypeDesc, classes, basePackageName)

        val params = getMethodParams(paramsDesc)

        return MethodSpec.methodBuilder(refName)
                .addModifiers(Modifier.ABSTRACT, Modifier.PUBLIC)
                .returns(retTypeName)
                .addParameters(params)
                .build()
    }

    /**
     * Returns a [List] of [ParameterSpec] based on the [paramsDesc].
     */
    private fun getMethodParams(paramsDesc: String): List<ParameterSpec> {

        var paramsDescM = paramsDesc

        var paramCount = 0
        val paramList = mutableListOf<ParameterSpec>()

        while (paramsDescM.isNotEmpty()) {

            var paramDesc = paramsDescM.substring(0, 1)

            while (paramDesc.endsWith("["))
                paramDesc = paramsDescM.substring(0, paramDesc.length+1)

            if (paramDesc.endsWith("L"))
                paramDesc = paramsDescM.substring(0, paramsDescM.indexOfFirst { it == ';' } + 1)

            val poetType = getPoetType(paramDesc, classes, basePackageName)

            paramList.add(ParameterSpec.builder(poetType, "param$paramCount").build())

            paramsDescM = paramsDescM.substring(paramDesc.length)
            paramCount++

        }

        return paramList

    }

}