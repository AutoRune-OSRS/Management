package io.autorune.updater.hooks

import com.google.gson.*
import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.packets.PacketAnalyzer

object HookGenerator
{

	fun generateHooksJson(revision: String, classAnalyzers: List<ClassAnalyzer>, packetAnalyzers: List<PacketAnalyzer>) : JsonObject {

		val jsonObject = JsonObject()

		jsonObject.addProperty("client_revision", revision)

		jsonObject.add("class_hooks", ClassHooks.generate(classAnalyzers))

		jsonObject.add("packet_hooks", PacketHooks.generate(packetAnalyzers))

		return jsonObject

	}

}