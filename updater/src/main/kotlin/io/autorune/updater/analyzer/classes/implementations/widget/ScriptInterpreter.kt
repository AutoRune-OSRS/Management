package io.autorune.updater.analyzer.classes.implementations.widget

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.dateandtime.Calendar
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(Calendar::class)
class ScriptInterpreter : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            if (classNode.name == getClassAnalyserName(Calendar::class))
                continue

            val match = classNode.fields.any { it.desc == "Ljava/util/Calendar;" }

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {

    }


}