package io.autorune.updater.analyzer.classes.implementations.user

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(Username::class)
class BasicUser : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (!classNode.interfaces.contains("java/lang/Comparable"))
                continue

            val match = classNode.fields.count { it.desc == String.format("L%s;", getClassAnalyserName(Username::class)) } == 2

            if (!match)
                continue

            return classNode

        }
        return null
    }

    override fun getFields() {

    }

}