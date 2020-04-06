package io.autorune.updater.jar

import com.google.gson.JsonObject
import io.autorune.injector.ClientInjector
import io.autorune.osrs.mixin.MixinFetcher
import io.autorune.utilities.jar.JarLoader
import io.autorune.utilities.preferences.SystemPreferences
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.tree.ClassNode
import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import java.util.jar.*

object InjectedDumper {

	fun dumpInjectedJar(deobLoc: Path, hooksJson: JsonObject) {

		val injectedGamepackFile = SystemPreferences.getInjectedJarLocation().toFile()
		injectedGamepackFile.delete()
		injectedGamepackFile.createNewFile()

		lateinit var classNodes: List<ClassNode>

		val jarLoader = JarLoader()

		classNodes = jarLoader.loadJar(deobLoc)

		val mixinClassNodes = MixinFetcher.getAllMixinClassNodes()

		ClientInjector.runInjection(classNodes, mixinClassNodes, hooksJson)

		dumpInjectedClient(injectedGamepackFile, classNodes.toArray(arrayOf<ClassNode>()))

	}

	private fun dumpInjectedClient(injectedFile : File, classes : Array<ClassNode>)
	{
		try
		{
			val out = JarOutputStream(FileOutputStream(injectedFile))
			for(node in classes)
			{
				val entry = JarEntry(node.name + ".class")
				out.putNextEntry(entry)
				val writer = ClassWriter( ClassWriter.COMPUTE_MAXS)
				node.accept(writer)
				out.write(writer.toByteArray())
			}
			out.close()
		}
		catch(e : IOException)
		{
			e.printStackTrace()
		}
	}

}