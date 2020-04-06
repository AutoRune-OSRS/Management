package io.autorune.updater.analyzer.classes.implementations.sprite

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.RSEnum
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(RSEnum::class)
class FillMode : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (!classNode.interfaces.contains(getClassAnalyserName(RSEnum::class)))
                continue

            val match = classNode.fields.count { it.desc == descInt && !isMemberStatic(it.access) } == 2
                    && classNode.fields.count { !isMemberStatic(it.access) } == 2
                    && classNode.fields.count { it.desc == String.format("L%s;", classNode.name) } == 3
                    && classNode.fields.count { it.desc == String.format("L%s;", classNode.name) && Modifier.isPublic(it.access) } == 1//TODO: Find better hook

            if (!match)
                continue

            return classNode

        }
        return null
    }

    override fun getFields() {

    }

}