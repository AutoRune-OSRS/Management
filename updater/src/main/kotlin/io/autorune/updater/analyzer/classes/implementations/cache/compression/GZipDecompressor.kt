package io.autorune.updater.analyzer.classes.implementations.cache.compression

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
class GZipDecompressor : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            val match = classNode.fields.any { it.desc == "Ljava/util/zip/Inflater;" }

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {
    }


}