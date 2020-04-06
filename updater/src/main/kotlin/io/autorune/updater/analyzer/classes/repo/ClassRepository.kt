package io.autorune.updater.analyzer.classes.repo


import org.objectweb.asm.tree.ClassNode
import org.runestar.client.updater.deob.util.readJar
import java.nio.file.Path

object ClassRepository {

    lateinit var classPool: List<ClassNode>

    fun initialize(jarPath: Path) {
        classPool = readJar(jarPath).toList()
    }

}