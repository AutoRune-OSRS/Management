package io.autorune.updater.analyzer.classes.implementations.definitions

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.collection.DoublyNode
import io.autorune.updater.analyzer.classes.implementations.io.RSByteBuffer
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(3)
@DependsOn(DoublyNode::class, RSByteBuffer::class)
class SpotAnimationDefinition : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyser(DoublyNode::class)?.classNode?.name)
                continue

            if (classNode.interfaces.isNotEmpty())
                continue

            val match = classNode.fields.count { it.desc == descShortArr && !Modifier.isStatic(it.access) } == 4
                    && classNode.fields.count { it.desc == descInt && !Modifier.isStatic(it.access) } >= 8
                    && classNode.fields.all { Modifier.isStatic(it.access) || (it.desc == descShortArr || it.desc == descInt) }

            if (match)
                return classNode

        }

        return null
    }

    private fun findDecode()
    {

        val match = classNode.methods.first { it.desc == String.format("(L%s;)V", getClassAnalyserName(RSByteBuffer::class)) && !isMemberStatic(it.access) }

        addMethod("decode", match)

    }

    private fun findDecodeOpcode()
    {

        val methodN = AnalyzerSearching.searchClassForMethod(classNode, String.format("(L%s;I)V", getClassAnalyserName(RSByteBuffer::class))).first()

        addMethod("decodeOpcode", methodN)

    }

    private fun findFetchSpotAnimDefinition()
    {

        val matches = AnalyzerSearching.searchAllClassesForMethod(listOf(), String.format("(I)L%s;", classNode.name))

        addMethodToClient("fetchSpotAnimDefinition", matches[0].second.name, matches[0].first)


    }

    override fun getFields() {
        findDecode()
        findDecodeOpcode()
        findFetchSpotAnimDefinition()
    }


}