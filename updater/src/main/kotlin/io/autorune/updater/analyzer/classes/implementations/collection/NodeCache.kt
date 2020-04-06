package io.autorune.updater.analyzer.classes.implementations.collection

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(5)
@CorrectMethodCount(0)
@DependsOn(DoublyNode::class, IterableHashTable::class, IterableDoublyQueue::class)
class NodeCache : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != "java/lang/Object")
                continue

            val match = classNode.fields.count { it.desc == "I" && !Modifier.isStatic(it.access) } == 2
                    && classNode.fields.count { it.desc == String.format("L%s;", getClassAnalyser(DoublyNode::class)?.classNode?.name) } == 1

            if (match)
                return classNode

        }

        return null
    }

    private fun getDoublyNode() {
        addField("doublyNode", classNode.fields.first { !isMemberStatic(it.access) && it.desc == String.format("L%s;", getClassAnalyserName(DoublyNode::class)) })
    }

    private fun getCircularDoublyNode() {
        addField("doublyNodeList", classNode.fields.first { !isMemberStatic(it.access) && it.desc == String.format("L%s;", getClassAnalyserName(IterableDoublyQueue::class)) })
    }

    private fun getIterableHashTable() {
        addField("iterableHashTable", classNode.fields.first { !isMemberStatic(it.access) && it.desc == String.format("L%s;", getClassAnalyserName(IterableHashTable::class)) })
    }

    private fun getSizeAndRemaining() {

        val matches = AnalyzerSearching.searchClassForField(classNode, listOf(ALOAD, GETFIELD, PUTFIELD), "()V")

        addField("size", matches[0])
        addField("remaining", matches[1])

    }


    override fun getFields() {
        getDoublyNode()
        getSizeAndRemaining()
        getIterableHashTable()
        getCircularDoublyNode()
    }


}