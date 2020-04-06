package io.autorune.updater.analyzer.classes.implementations.io

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
class BufferedSink : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (!classNode.interfaces.contains("java/lang/Runnable"))
                continue

            val match = classNode.fields.any { it.desc == "Ljava/io/OutputStream;" }
                    && classNode.fields.none { it.desc == "Ljava/io/InputStream;" }

            if (match)
                return classNode

        }
        return null
    }

    override fun getFields() {

    }

}