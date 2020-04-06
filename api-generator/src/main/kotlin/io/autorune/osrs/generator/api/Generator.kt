package io.autorune.osrs.generator.api

import com.google.gson.JsonArray
import com.squareup.javapoet.ArrayTypeName
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.TypeName
import io.autorune.osrs.generator.api.type.MixinGenerator
import org.objectweb.asm.Type

/**
 * @author Chris
 * Abstract api generator class
 * Contains some helpers for generators to use
 */
abstract class Generator {

    /**
     * Generators are required to implement this abstract function
     * as the entry point of their logic
     */
    abstract fun generate()

    /**
     * Returns the [TypeName] associated with the [typeDesc] and [pkgName]
     * Requires the class hooks [classes] for non-primitive classes
     */
    fun getPoetType(typeDesc: String, classes: JsonArray, pkgName : String): TypeName {

        var type = Type.getType(typeDesc)

        var baseType = type.baseType

        var typeString = baseType.className

        lateinit var typeName: TypeName

        if (baseType.sort == Type.OBJECT && !typeString.contains("."))
        {
            val classJson = classes.first { it.asJsonObject.get("obf_name").asString == typeString }.asJsonObject

            typeString = classJson.get("ref_name").asString
            val typePkg = pkgName+classJson.get("package").asString

            typeName = ClassName.get(typePkg, typeString)
        } else {
            val klass = ClassLoader.getSystemClassLoader().loadClassFromDescriptor(baseType.descriptor)
            typeName = TypeName.get(klass)
        }
        var name = typeName
        repeat(type.arrayDimensions) {
            name = ArrayTypeName.of(name)
        }

        return name

    }

    /**
     * Returns the [TypeName] associated with the [typeDesc]
     * Used primarily by [MixinGenerator]
     */
    fun getPoetType(typeDesc: String): TypeName {

        var type = Type.getType(typeDesc)

        var baseType = type.baseType

        lateinit var typeName: TypeName

        val klass = ClassLoader.getSystemClassLoader().loadClassFromDescriptor(baseType.descriptor)
        typeName = TypeName.get(klass)

        var name = typeName
        repeat(type.arrayDimensions) {
            name = ArrayTypeName.of(name)
        }

        return name

    }

}