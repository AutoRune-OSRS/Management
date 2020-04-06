package io.autorune.updater.analyzer.classes.implementations.model

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.entity.Entity
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(Entity::class)
class ModelHeader : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyserName(Entity::class))
                continue

            val match = classNode.fields.count { it.desc == descByteArr && !isMemberStatic(it.access) } == 5

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {
    }


}