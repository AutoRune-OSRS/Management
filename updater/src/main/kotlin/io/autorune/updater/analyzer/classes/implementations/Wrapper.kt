package io.autorune.updater.analyzer.classes.implementations

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.collection.DoublyNode
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier


@CorrectFieldCount(0)
@CorrectMethodCount(2)
@DependsOn(DoublyNode::class)
class Wrapper : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (!Modifier.isAbstract(classNode.access))
                continue

            if (classNode.superName != getClassAnalyserName(DoublyNode::class))
                continue

            val match = classNode.methods.any { it.desc == "()Ljava/lang/Object;" && Modifier.isAbstract(it.access) }

            if (match)
                return classNode

        }
        return null
    }

    fun findObject()
    {

        val matches = AnalyzerSearching.searchClassForMethod(classNode, "()Ljava/lang/Object;")

        addMethod("instance", matches[0])

    }

    fun findIsSoft()
    {

        val matches = AnalyzerSearching.searchClassForMethod(classNode, "()Z")

        addMethod("isSoft", matches[0])

    }

    override fun getFields() {

        findObject()
        findIsSoft()

    }

}