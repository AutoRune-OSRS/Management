package io.autorune.updater.analyzer.classes.implementations.`var`

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
class Varps : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.interfaces.isNotEmpty() || classNode.superName != "java/lang/Object")
                continue

            var matches = AnalyzerSearching.searchClassForMethod(classNode, listOf(IF_ICMPGE, GETSTATIC, ILOAD, ILOAD, ICONST_1, ISUB, IASTORE, ILOAD, ILOAD, IADD, ISTORE, IINC), "()V")

            val match = classNode.methods.none { !isMemberStatic(it.access) }
                    && matches.isNotEmpty()
                    && AnalyzerSearching.searchMethodForFields(matches[0].first, listOf(SIPUSH)).first

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields()
    {



    }


}