package io.autorune.osrs.generator.api.type

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import io.autorune.osrs.generator.api.Generator
import org.objectweb.asm.tree.ClassNode
import javax.lang.model.element.Modifier

/**
 * @author Chris
 * Contains the logic for generating a field for a class in the osrs-api
 * @param typeSpec : [TypeSpec.Builder] The type spec builder for the class owning the field
 * @param fieldJson : [JsonObject] The field hook for the field being generated
 * @param classes : [JsonArray] Contains all class hooks for reference
 * @param basePackageName : [String] The package name of the owning class used to get poet type
 * @param noSetter : [Boolean] If true, then do no generate a setter for the field
 */
class FieldGenerator(
        private val typeSpec: TypeSpec.Builder, private val fieldJson: JsonObject, private val classes: JsonArray, private val basePackageName: String, private val noSetter: Boolean = false
) : Generator()
{

    /**
     * The generation logic for one field in the osrs-api
     */
    override fun generate() {

        val isFinal = java.lang.reflect.Modifier.isFinal(fieldJson.get("access").asInt)
        typeSpec.addMethod(getFieldGetterSpec())
        if (!isFinal && !noSetter)
            typeSpec.addMethod(getFieldSetterSpec())

    }

    /**
     * Returns a [MethodSpec] for the getter built from the [FieldGenerator] parameters.
     */
    private fun getFieldGetterSpec() : MethodSpec
    {

        val refName = fieldJson.get("ref_name").asString
        val retTypeDesc = fieldJson.get("ret_type").asString

        val retTypeName = getPoetType(retTypeDesc, classes, basePackageName)

        val getterName = "get" + refName.substring(0, 1).toUpperCase() + refName.substring(1);

        return MethodSpec.methodBuilder(getterName)
                .addModifiers(Modifier.ABSTRACT, Modifier.PUBLIC)
                .returns(retTypeName)
                .build()
    }

    /**
     * Returns a [MethodSpec] for the setter built from the [FieldGenerator] parameters.
     */
    private fun getFieldSetterSpec() : MethodSpec
    {

        val refName = fieldJson.get("ref_name").asString
        val retTypeDesc = fieldJson.get("ret_type").asString

        val retTypeName = getPoetType(retTypeDesc, classes, basePackageName)

        val setterName = "set" + refName.substring(0, 1).toUpperCase() + refName.substring(1);

        return MethodSpec.methodBuilder(setterName)
                .addModifiers(Modifier.ABSTRACT, Modifier.PUBLIC)
                .addParameter(retTypeName, "value")
                .build()
    }

}