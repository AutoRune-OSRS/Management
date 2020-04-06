package io.autorune.osrs.generator.api

import com.google.gson.JsonObject
import io.autorune.osrs.generator.api.type.ClassGenerator
import io.autorune.osrs.mixin.MixinFetcher
import org.apache.commons.io.FileUtils
import java.io.File

/**
 * @author Chris
 * Osrs api generator
 */
object ApiGenerator
{

    /**
     * Generates the api in the osrs-api module from the [hooksJson] provided.
     */
    fun generateApi(hooksJson: JsonObject)
    {

        FileUtils.cleanDirectory(File("osrs-api/src/main/java/io/autorune/osrs/api/"));

        val classes = hooksJson.getAsJsonArray("class_hooks")

        val mixinClassNodes = MixinFetcher.getAllMixinClassNodes()

        val clientInstanceFieldHook = classes.flatMap { it.asJsonObject.getAsJsonArray("field_hooks") }.first { it.asJsonObject.get("ref_name").asString == "clientInstance" }.asJsonObject

        classes.forEach { clazz ->

            val classJson = clazz.asJsonObject

            val refName = classJson.get("ref_name").asString
            val mixinClassNode = mixinClassNodes.find { it.name.split("/").last().replace("Mixin", "") == refName }

	        ClassGenerator(classJson, classes, mixinClassNode, clientInstanceFieldHook).generate()

        }

    }

}