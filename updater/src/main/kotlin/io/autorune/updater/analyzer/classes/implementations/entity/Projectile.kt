package io.autorune.updater.analyzer.classes.implementations.entity

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(Entity::class)
class Projectile : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyserName(Entity::class))
                continue

            val match = classNode.fields.count { it.desc == descDouble && !isMemberStatic(it.access) } > 5

            if (match)
                return classNode

        }
        return null
    }

    override fun getFields() {

    }

}