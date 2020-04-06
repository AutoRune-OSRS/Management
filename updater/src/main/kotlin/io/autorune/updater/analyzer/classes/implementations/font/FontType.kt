package io.autorune.updater.analyzer.classes.implementations.font

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
class FontType : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            val matches = AnalyzerSearching.searchClassForField(classNode, listOf(NEW, DUP, LDC, INVOKESPECIAL, PUTSTATIC))

            if (matches.size > 5)
                return classNode

        }
        return null
    }

    override fun getFields() {

    }

}