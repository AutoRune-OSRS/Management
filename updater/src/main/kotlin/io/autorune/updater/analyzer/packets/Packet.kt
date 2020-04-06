package io.autorune.updater.analyzer.packets

data class Packet(val direction: PacketDirection, val id: Int, val size: Int, val structure: List<Int>) {

    enum class PacketDirection {
        IN, OUT
    }

}