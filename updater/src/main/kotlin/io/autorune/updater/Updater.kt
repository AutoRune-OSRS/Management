package io.autorune.updater

import io.autorune.osrs.generator.api.ApiGenerator
import io.autorune.updater.analyzer.classes.repo.ClassAnalyzerRepository
import io.autorune.updater.analyzer.classes.repo.ClassRepository
import io.autorune.updater.analyzer.classes.util.AnalyzerStaging
import io.autorune.updater.analyzer.classes.util.AnalyzerUtils
import io.autorune.updater.analyzer.packets.repo.PacketAnalyzerRepository
import io.autorune.updater.hooks.HookGenerator
import io.autorune.updater.jar.InjectedDumper
import io.autorune.utilities.jar.JarDownloader
import io.autorune.utilities.resource.JagexResourceFetcher
import org.runestar.client.updater.deob.Transformer
import java.nio.file.Paths

object Updater
{

	private fun runUpdater()
	{
		println("Starting AutoRune Updater.")

		val useLocalFile = true

		var revision = 189

		var deobLocation = Paths.get("./$revision.jar.deob")

		if(!useLocalFile)
		{

			val jResource = JagexResourceFetcher.getJagexResource(false)

			val jarInfo = JarDownloader.fetchJar(jResource.gamePack.url)

			Transformer.DEFAULT.transform(jarInfo.second, deobLocation)

		}

		ClassRepository.initialize(deobLocation)

		println("Loaded class repository.")

		ClassAnalyzerRepository.initialize()

		println("Loaded analyzer repository.")

		var foundField = 0
		var foundMethod = 0
		var foundClass = 0

		val begin = System.currentTimeMillis()

		for(analyzer in ClassAnalyzerRepository.classAnalyzers)
		{

			if(analyzer.readyForDependencies)
				continue
			val counts = AnalyzerStaging.runStaging(analyzer)
			foundClass += counts.first
			foundField += counts.second
			foundMethod += counts.third

		}

		val totalField = AnalyzerUtils.getTotalCorrectFields()
		val totalMethod = AnalyzerUtils.getTotalCorrectMethods()

		for(analyzer in ClassAnalyzerRepository.classAnalyzers)
		{
			analyzer.print(AnalyzerUtils.getCorrectFields(analyzer), AnalyzerUtils.getCorrectMethods(analyzer))
		}

		val end = System.currentTimeMillis() - begin

		println()
		println("Identified " + foundClass + "/" + ClassAnalyzerRepository.classAnalyzers.size + " classes")
		println("Identified $foundField/$totalField fields")
		println("Identified $foundMethod/$totalMethod methods\n")

		for(analyzer in ClassAnalyzerRepository.classAnalyzers)
		{
			if (analyzer.getFoundFields() != AnalyzerUtils.getCorrectFields(analyzer))
				println(analyzer.javaClass.simpleName+" field count is incorrect")

			if (analyzer.getFoundMethods() != AnalyzerUtils.getCorrectMethods(analyzer))
				println(analyzer.javaClass.simpleName+" method count is incorrect")
		}

		println("\nFinished analyzing in $end ms\n")

		//val hooksJson = HookGenerator.generateHooksJson(revision.toString(), ClassAnalyzerRepository.classAnalyzers, PacketAnalyzerRepository.packetAnalyzers)

		//InjectedDumper.dumpInjectedJar(deobLocation, hooksJson)

		//ApiGenerator.generateApi(hooksJson)

	}


	@JvmStatic
	fun main(args : Array<String>)
	{
		runUpdater()
	}

}