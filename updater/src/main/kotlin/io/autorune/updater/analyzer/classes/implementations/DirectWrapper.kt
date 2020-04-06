package io.autorune.updater.analyzer.classes.implementations

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(Wrapper::class)
class DirectWrapper : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyserName(Wrapper::class))
                continue

            val match = classNode.fields.any { it.desc == descObject && !isMemberStatic(it.access) }

            if (match)
                return classNode

        }
        return null
    }

    override fun getFields() {

    }

}