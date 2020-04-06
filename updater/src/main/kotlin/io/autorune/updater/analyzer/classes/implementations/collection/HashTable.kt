package io.autorune.updater.analyzer.classes.implementations.collection

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(4)
@DependsOn(Node::class)
class HashTable : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != "java/lang/Object" || classNode.interfaces.isNotEmpty())
                continue

            val nodeClassName = getClassAnalyser(Node::class)?.classNode?.name

            val match = classNode.fields.count { it.desc == String.format("L%s;", nodeClassName) && !Modifier.isStatic(it.access) } == 2
                    && classNode.fields.count { it.desc == String.format("[L%s;", nodeClassName) && !Modifier.isStatic(it.access) } == 1

            if (match)
                return classNode

        }

        return null
    }

    private fun findGet()
    {

        val match = classNode.methods.first { it.desc == String.format("(J)L%s;", getClassAnalyserName(Node::class)) }

        addMethod("fetch", match)

    }

    private fun findPut()
    {

        val match = classNode.methods.first { it.desc == String.format("(L%s;J)V", getClassAnalyserName(Node::class)) }

        addMethod("insert", match)

    }

    private fun findNext()
    {

        val matches = AnalyzerSearching.searchClassForMethod(classNode, listOf(DUP, GETFIELD, DUP_X1, ICONST_1), String.format("()L%s;", getClassAnalyserName(Node::class)))

        addMethod("next", matches[0].first)

    }

    private fun findFirst()
    {

        val match = classNode.methods.first { it.desc == String.format("()L%s;", getClassAnalyserName(Node::class)) && it != methods.first { m -> m.newMethodName == "next" }.methodNode }

        addMethod("first", match)

    }

    override fun getFields() {
        findGet()
        findPut()
        findNext()
        findFirst()
    }


}