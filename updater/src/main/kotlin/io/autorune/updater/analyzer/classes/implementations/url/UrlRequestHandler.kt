package io.autorune.updater.analyzer.classes.implementations.url

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(UrlRequest::class)
class UrlRequestHandler : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (!classNode.interfaces.contains("java/lang/Runnable") || classNode.superName != "java/lang/Object")
                continue

            val match = classNode.methods.any { it.desc == String.format("(Ljava/net/URL;)L%s;", getClassAnalyserName(UrlRequest::class)) }

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {
    }


}