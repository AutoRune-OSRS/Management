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
class FloorDecoration : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            val match = classNode.fields.count { it.desc == descInt && !Modifier.isStatic(it.access) } == 4
                    && classNode.fields.count { it.desc == String.format("L%s;", getClassAnalyser(Entity::class)?.classNode?.name) } == 1

            if (!match)
                continue

            return classNode

        }
        return null
    }

    override fun getFields() {

    }

}