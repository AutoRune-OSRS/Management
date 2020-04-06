package io.autorune.updater.analyzer.classes.implementations.dateandtime

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(AbstractTimer::class, MilliTimer::class)
class NanoTimer : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyserName(AbstractTimer::class) || classNode.name == getClassAnalyserName(MilliTimer::class))
                continue

            return classNode

        }

        return null
    }

    override fun getFields() {
    }


}