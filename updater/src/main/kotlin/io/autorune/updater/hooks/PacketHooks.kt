package io.autorune.updater.hooks

import com.google.gson.JsonArray
import io.autorune.updater.analyzer.packets.PacketAnalyzer

object PacketHooks {

    fun generate(packetAnalyzers: List<PacketAnalyzer>) : JsonArray {

        val packetHooksJsonArray = JsonArray()

        return packetHooksJsonArray

    }

}