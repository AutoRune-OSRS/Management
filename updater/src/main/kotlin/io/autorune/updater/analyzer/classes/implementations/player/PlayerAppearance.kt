package io.autorune.updater.analyzer.classes.implementations.player

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
class PlayerAppearance : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            val matches = AnalyzerSearching.searchClassForMethod(classNode, "([I[IZI)V")

            if (matches.isNotEmpty())
                return classNode

        }
        return null
    }

    override fun getFields() {

    }

}