package io.autorune.updater.analyzer.classes.implementations.io

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.collection.Node
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.*

@CorrectFieldCount(4)
@CorrectMethodCount(0)
@DependsOn(Node::class)
class RSByteBuffer : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyser(Node::class)?.classNode?.name)
                continue

            val match = classNode.fields.count { it.desc == "[B" || it.desc == "I" } == 2
            if (!match)
                continue

            val matches = AnalyzerSearching.searchClassForMethod(classNode, "BigInteger")

            if (matches.isNotEmpty())
                return classNode

        }
        return null
    }

    private fun getBuffer() {
        addField("buffer", classNode.fields.first { it.desc == "[B" && !isMemberStatic(it.access) })
    }

    private fun getOffset() {
        addField("offset", classNode.fields.first { it.desc == "I" && !isMemberStatic(it.access) })
    }

    private fun getCrcTables() {
        addField("crcTable32", classNode.fields.first { it.desc == "[I" })
        addField("crcTable64", classNode.fields.first { it.desc == "[J" })
    }

    override fun getFields() {
        getBuffer()
        getOffset()
        getCrcTables()
    }
}