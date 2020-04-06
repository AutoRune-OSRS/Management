package io.autorune.updater.analyzer.classes.implementations

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(2)
@CorrectMethodCount(0)
class RSException : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            if (classNode.superName != "java/lang/RuntimeException")
                continue

            return classNode

        }

        return null
    }

    fun findFields()
    {

        val throwable = classNode.fields.first { it.desc == String.format("L%s;", "java/lang/Throwable") }

        addField("throwable", throwable)

        val reason = classNode.fields.first { !isMemberStatic(it.access) && it.desc == String.format("L%s;", "java/lang/String") }

        addField("reason", reason)

    }

    override fun getFields() {
        findFields()
    }


}