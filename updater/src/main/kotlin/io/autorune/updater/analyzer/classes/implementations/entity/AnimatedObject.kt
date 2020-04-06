package io.autorune.updater.analyzer.classes.implementations.entity

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(Entity::class)
class AnimatedObject : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyserName(Entity::class))
                continue

            val match = classNode.fields.count { it.desc == descInt && !Modifier.isStatic(it.access) } == 8
                    && classNode.fields.count { !Modifier.isStatic(it.access) } == 9

            if (!match)
                continue

            return classNode

        }
        return null
    }

    override fun getFields() {

    }

}