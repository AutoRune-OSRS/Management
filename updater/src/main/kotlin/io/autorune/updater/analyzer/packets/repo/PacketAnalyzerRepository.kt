package io.autorune.updater.analyzer.packets.repo

import io.autorune.updater.analyzer.packets.PacketAnalyzer
import io.autorune.updater.analyzer.packets.implementations.PacketDummy
import java.io.File
import java.util.*

object PacketAnalyzerRepository {

    lateinit var packetAnalyzers: List<PacketAnalyzer>

    fun initialize() {
        packetAnalyzers = getAllAnalyzers()
    }

    private fun getAllAnalyzers(): List<PacketAnalyzer> {

        val packageName = PacketDummy::class.java.packageName

        val path = packageName.replace(".", File.separator)

        val classes = ArrayList<PacketAnalyzer>()

        val classPathEntries = System.getProperty("java.class.path").split(System.getProperty("path.separator").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        for (classpathEntry in classPathEntries) {
            if (!classpathEntry.contains("updater"))
                continue
            try {
                val base = File(classpathEntry + File.separatorChar + path)
                classes.addAll(searchDirectory(packageName, base))
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        }

        return classes
    }

    @Throws(Exception::class)
    private fun searchDirectory(packageName: String, base: File): List<PacketAnalyzer> {
        val classes = ArrayList<PacketAnalyzer>()
        if (base.path.contains(".jar"))
            return classes
        var name: String
        for (file in base.listFiles()) {
            if (file.isDirectory)
                classes.addAll(searchDirectory(packageName + "." + file.name, file))
            else {
                name = file.name
                if (name.endsWith(".class")) {
                    name = name.substring(0, name.length - 6)
                    val clazz = Class.forName("$packageName.$name").getDeclaredConstructor().newInstance()
                    if (clazz is PacketAnalyzer)
                    classes.add(clazz)
                }
            }
        }
        return classes
    }

}