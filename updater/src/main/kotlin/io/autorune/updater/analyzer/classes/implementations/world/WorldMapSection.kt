package io.autorune.updater.analyzer.classes.implementations.world

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(Coordinates::class)
class WorldMapSection : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (!Modifier.isInterface(classNode.access))
                continue

            val match = classNode.methods.any { it.desc == String.format("(II)L%s;", getClassAnalyserName(Coordinates::class)) && !isMemberStatic(it.access) }

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {
    }


}