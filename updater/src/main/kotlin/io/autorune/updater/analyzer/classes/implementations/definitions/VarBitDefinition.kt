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
@CorrectMethodCount(4)
@DependsOn(DoublyNode::class, RSByteBuffer::class)
class VarBitDefinition : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyser(DoublyNode::class)?.classNode?.name)
                continue

            if (classNode.interfaces.isNotEmpty())
                continue

            val match = classNode.fields.count { it.desc == descInt && !Modifier.isStatic(it.access) } == 3
                    && classNode.fields.count { !Modifier.isStatic(it.access) } == 3

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

//    private fun findFetchVarBitDefinition()
//    {
//
//        println(classNode.name)
//
//        val matches = AnalyzerSearching.searchAllClassesForMethod(listOf(), String.format("(I)L%s;", classNode.name))
//
//        addMethodToClient("fetchVarBitDefinition", matches[0].second.name, matches[0].first)
//
//    }

    private fun findVarBitValueMethods()
    {

        val decodeMethod = methods.first { it.newMethodName == "decode" }.methodNode

        val matches = AnalyzerSearching.searchAllClassesForMethodCall(listOf(INVOKEVIRTUAL)).filter {
            isMemberStatic(it.second.access)
                    && it.third.any { mc -> mc.name == decodeMethod.name && mc.owner == classNode.name && mc.desc == decodeMethod.desc }
        }

        var match = matches.first { it.second.desc == "(I)I" }

        addMethodToClient("fetchVarBitValue", match.first.name, match.second)

        match = matches.first { it.second.desc == "(II)V" }

        addMethodToClient("putVarBitValue", match.first.name, match.second)

    }

    override fun getFields() {
        findDecode()
        findDecodeOpcode()
        findVarBitValueMethods()
    }


}