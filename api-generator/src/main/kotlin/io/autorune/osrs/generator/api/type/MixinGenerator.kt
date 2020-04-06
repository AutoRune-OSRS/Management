package io.autorune.osrs.generator.api.type

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeSpec
import io.autorune.osrs.generator.api.Generator
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.FieldNode
import org.objectweb.asm.tree.MethodNode

/**
 * @author Chris
 * Contains the logic for generating a method for a class in the osrs-api
 * @param typeSpec : [TypeSpec.Builder] The type spec builder for the class correlated to the mixin
 * @param mixinClassNode : [ClassNode] The class node for the mixin
 */
class MixinGenerator(private val typeSpec: TypeSpec.Builder, private val mixinClassNode: ClassNode) : Generator() {


    /**
     * The generation logic for one mixin in the osrs-api
     */
    override fun generate() {

        for (fieldNode in mixinClassNode.fields)
            generateMixinField(fieldNode)

        for (methodNode in mixinClassNode.methods)
        {
            val isInsertionMethod = methodNode.visibleAnnotations != null
                    && methodNode.visibleAnnotations.any { it.desc.contains("Insertion") }
            if (isInsertionMethod)
                continue
            generateMixinMethod(methodNode)
        }

    }

    /**
     * The generation logic for one field based on a [FieldNode] for a mixin in the osrs-api
     */
    private fun generateMixinField(fieldNode: FieldNode) {

        typeSpec.addMethod(getFieldGetterSpec(fieldNode))
        typeSpec.addMethod(getFieldSetterSpec(fieldNode))

    }

    /**
     * The generation logic for one method based on a [MethodNode] for a mixin in the osrs-api
     */
    private fun generateMixinMethod(methodNode: MethodNode) {

        typeSpec.addMethod(getMethodSpec(methodNode))

    }

    /**
     * Returns a [MethodSpec] for the getter built from the [FieldNode] parameter.
     */
    private fun getFieldGetterSpec(fieldNode: FieldNode) : MethodSpec
    {

        val fieldName = fieldNode.name.replace("Mixin", "")
        val fieldDesc = fieldNode.desc

        val getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

        return MethodSpec.methodBuilder(getterName)
                .addModifiers(javax.lang.model.element.Modifier.ABSTRACT, javax.lang.model.element.Modifier.PUBLIC)
                .returns(getPoetType(fieldDesc))
                .build()

    }

    /**
     * Returns a [MethodSpec] for the setter built from the [FieldNode] parameter.
     */
    private fun getFieldSetterSpec(fieldNode: FieldNode) : MethodSpec
    {

        val fieldName = fieldNode.name.replace("Mixin", "")
        val fieldDesc = fieldNode.desc

        val setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

        return MethodSpec.methodBuilder(setterName)
                .addModifiers(javax.lang.model.element.Modifier.ABSTRACT, javax.lang.model.element.Modifier.PUBLIC)
                .addParameter(getPoetType(fieldDesc), "value")
                .build()

    }

    /**
     * Returns a [MethodSpec] for the method built from the [MethodNode] parameter.
     */
    private fun getMethodSpec(methodNode: MethodNode) : MethodSpec
    {

        val params = getMethodParams(methodNode.desc.split("(")[1].split(")")[0])

        return MethodSpec.methodBuilder(methodNode.name.replace("Mixin", ""))
                .addModifiers(javax.lang.model.element.Modifier.ABSTRACT, javax.lang.model.element.Modifier.PUBLIC)
                .returns(getPoetType(methodNode.desc.split(")")[1]))
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

            val poetType = getPoetType(paramDesc)

            paramList.add(ParameterSpec.builder(poetType, "param$paramCount").build())

            paramsDescM = paramsDescM.substring(paramDesc.length)
            paramCount++

        }

        return paramList

    }

}