package io.autorune.updater.analyzer.classes.implementations.collection

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.*

@CorrectFieldCount(3)
@CorrectMethodCount(0)
class Node : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {
            if (classNode.fields.size != 3)
                continue
            if (classNode.methods.size != 3)
                continue
            if (classNode.superName.equals("java/lang/Object")) {
                val matches = AnalyzerSearching.searchClassForField(classNode, listOf(ALOAD, GETFIELD, IFNONNULL))
                if (matches.size != 0)
                    return classNode
            }
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
