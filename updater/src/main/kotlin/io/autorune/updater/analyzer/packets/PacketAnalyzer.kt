package io.autorune.updater.analyzer.packets

import io.autorune.updater.analyzer.AsmConstants

abstract class PacketAnalyzer : AsmConstants() {

    lateinit var packet: Packet

    val analyzerName: String = javaClass.simpleName

    var readyForDependencies: Boolean = false

    protected abstract fun findPacket(): Packet

    fun execute() {
        packet = findPacket()
    }

    fun javaName() : String
    {
        return this.javaClass.simpleName
    }

    override fun toString(): String {
        val s = ""
        return "\n[- " + this.javaClass.simpleName + " identified as: " + packet
    }

}