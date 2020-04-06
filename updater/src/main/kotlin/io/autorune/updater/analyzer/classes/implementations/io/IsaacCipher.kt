package io.autorune.updater.analyzer.classes.implementations.io

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
class IsaacCipher : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            val matches = AnalyzerSearching.searchClassForMethod(classNode, listOf(ICONST_4, IF_ICMPGE, ILOAD, ILOAD, BIPUSH, ISHL, IXOR), "()V")

            if (matches.isEmpty())
                continue

            return classNode
        }

        return null
    }

    override fun getFields() {

    }


}