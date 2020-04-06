package io.autorune.updater.analyzer.classes.implementations.collection

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(3)
@CorrectMethodCount(0)
@DependsOn(Node::class)
class DoublyNode : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {
            if (classNode.fields.count { !isMemberStatic(it.access) } != 3 ||
                    classNode.fields.count { it.desc == String.format("L%s;", classNode.name) } != 2)
                continue
            if (classNode.methods.count { !isMemberStatic(it.access) } != 2)
                continue
            if (classNode.superName != getClassAnalyser(Node::class)?.classNode?.name)
                continue
            return classNode
        }
        return null
    }

    private fun getNext() {
        val matches = AnalyzerSearching.searchClassForField(classNode, listOf(ALOAD, GETFIELD, IFNONNULL))
        addField("next", matches[0])
    }

    private fun getPrevious() {
        addField("previous", classNode.fields.first { !isMemberStatic(it.access) && it.desc != "J" && it.name != getField("next").name })
    }

    private fun getHash() {
        addField("hash", classNode.fields.first { !isMemberStatic(it.access) && it.desc == "J" })
    }

    override fun getFields() {
        getNext()
        getPrevious()
        getHash()
    }


}
