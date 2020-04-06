package io.autorune.updater.analyzer.classes.implementations.url

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(1)
class UrlRequest : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.interfaces.isNotEmpty() || classNode.superName != "java/lang/Object")
                continue

            val match = classNode.fields.any { it.desc == String.format("L%s;", "java/net/URL") }

            if (match)
                return classNode

        }

        return null
    }

    private fun findIsDone() {

        val match = classNode.methods.first { it.desc == "()Z" && !isMemberStatic(it.access) }

        addMethod("isDone", match)

    }

    override fun getFields() {

        findIsDone()

    }


}