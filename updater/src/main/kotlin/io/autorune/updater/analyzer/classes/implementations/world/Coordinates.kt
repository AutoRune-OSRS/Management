package io.autorune.updater.analyzer.classes.implementations.world

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode


@CorrectFieldCount(3)
@CorrectMethodCount(1)
class Coordinates : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            if (classNode.superName != "java/lang/Object" || classNode.interfaces.isNotEmpty())
                continue

            val matches = AnalyzerSearching.searchClassForMethod(classNode, listOf(BIPUSH, ISHL, ALOAD, GETFIELD, BIPUSH, ISHL, IOR, ALOAD, GETFIELD, IOR), "()I")

            if (matches.size == 0)
                continue

            return classNode

        }
        return null
    }

    private fun findCoordinateFields()
    {

        val mN = classNode.methods.first { it.name == "<init>" && it.desc == "(III)V" }

        val matches = AnalyzerSearching.searchMethodForFields(mN, listOf(PUTFIELD)).second

        addField("plane", matches[0])
        addField("x", matches[1])
        addField("y", matches[2])

    }

    private fun findPack()
    {

        val matches = AnalyzerSearching.searchClassForInts(classNode, listOf(BIPUSH), "()I")

        val match = matches.first { it.second.any { insn -> insn.operand == 28 } }

        addMethod("pack", match.first)

    }

    override fun getFields()
    {

        findCoordinateFields()
        findPack()

    }

}