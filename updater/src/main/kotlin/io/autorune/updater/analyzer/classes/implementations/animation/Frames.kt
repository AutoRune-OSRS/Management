package io.autorune.updater.analyzer.classes.implementations.animation

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.collection.DoublyNode
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(1)
@CorrectMethodCount(1)
@DependsOn(Frame::class, DoublyNode::class)
class Frames : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyser(DoublyNode::class)?.classNode?.name)
                continue

            val match = classNode.fields.any { it.desc == String.format("[L%s;", getClassAnalyserName(Frame::class)) }

            if (match)
                return classNode

        }

        return null
    }

    private fun findHasAlphaTransform()
    {

        val match = AnalyzerSearching.searchClassForMethod(classNode, "(I)Z")

        addMethod("hasAlphaTransformation", match[0])

    }

    private fun findFields()
    {

        val match = classNode.fields.first { it.desc == String.format("[L%s;", getClassAnalyserName(Frame::class)) && !isMemberStatic(it.access) }

        addField("frames", match)

    }

    override fun getFields() {

        findHasAlphaTransform()

        findFields()

    }


}