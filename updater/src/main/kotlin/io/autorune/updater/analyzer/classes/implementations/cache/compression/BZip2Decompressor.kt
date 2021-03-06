package io.autorune.updater.analyzer.classes.implementations.cache.compression

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(BZip2State::class)
class BZip2Decompressor : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            val match = classNode.fields.any { it.desc == String.format("L%s;", getClassAnalyser(BZip2State::class)?.classNode?.name) }

            if (match)
                return classNode

        }

        return null
    }


    override fun getFields() {

    }


}