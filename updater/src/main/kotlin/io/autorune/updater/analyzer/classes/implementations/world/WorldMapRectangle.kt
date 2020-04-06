package io.autorune.updater.analyzer.classes.implementations.world

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(WorldMapManager::class)
class WorldMapRectangle : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.interfaces.isNotEmpty() || classNode.superName != "java/lang/Object")
                continue

            val match = classNode.fields.any { it.desc == String.format("L%s;", getClassAnalyserName(WorldMapManager::class)) && !isMemberStatic(it.access) }
                    && classNode.fields.count { it.desc == descInt && !isMemberStatic(it.access) } == 4

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {
    }


}