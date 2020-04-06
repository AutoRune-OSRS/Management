package io.autorune.updater.analyzer.classes.implementations.task

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(Task::class)
class TaskHandler : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (!classNode.interfaces.contains("java/lang/Runnable") || classNode.superName != "java/lang/Object")
                continue

            val match = classNode.fields.any { it.desc == String.format("L%s;", getClassAnalyserName(Task::class)) }

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {
    }


}