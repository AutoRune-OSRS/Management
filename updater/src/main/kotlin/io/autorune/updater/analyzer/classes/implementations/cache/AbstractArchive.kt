package io.autorune.updater.analyzer.classes.implementations.cache

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.cache.compression.GZipDecompressor
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(GZipDecompressor::class)
class AbstractArchive : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (!Modifier.isAbstract(classNode.access))
                continue

            val match = classNode.fields.any {
                it.desc == String.format("L%s;", getClassAnalyser(GZipDecompressor::class)?.classNode?.name)
                        && Modifier.isStatic(it.access)
            }

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {
    }


}