package io.autorune.updater.analyzer.classes.implementations

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(2)
@CorrectMethodCount(3)
class SecureFuture : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            val match = classNode.fields.any { it.desc == String.format("L%s;", "java/util/concurrent/Future") }

            if (match)
                return classNode

        }

        return null
    }

    private fun findInstance()
    {

        val matches = AnalyzerSearching.searchClassForMethod(classNode, "()Ljava/security/SecureRandom;")

        addMethod("instance", matches[0])

    }

    private fun isDone()
    {

        val matches = AnalyzerSearching.searchClassForMethod(classNode, "()Z")

        addMethod("done", matches[0])

    }

    private fun findShutdown()
    {

        val match = classNode.methods.first { it.name != "<init>" && it.desc == "()V" && !isMemberStatic(it.access) }

        addMethod("shutdown", match)

    }

    private fun findFields()
    {

        val es = classNode.fields.first { it.desc == String.format("L%s;", "java/util/concurrent/ExecutorService") }

        addField("executorService", es)

        val future = classNode.fields.first { it.desc == String.format("L%s;", "java/util/concurrent/Future") }

        addField("future", future)

    }

    override fun getFields() {

        findInstance()
        isDone()
        findShutdown()
        findFields()
    }


}