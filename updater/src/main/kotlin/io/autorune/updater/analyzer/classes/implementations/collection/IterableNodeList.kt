package io.autorune.updater.analyzer.classes.implementations.collection

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(2)
@CorrectMethodCount(3)
class IterableNodeList : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (!classNode.interfaces.containsAll(listOf("java/lang/Iterable", "java/util/Collection")))
                continue


            return classNode

        }

        return null
    }


    private fun findClear()
    {

        val methodN = AnalyzerSearching.searchClassForMethod(classNode, "()V").first { it.name != "<init>" }

        addMethod("clear", methodN)

    }

    private fun findDequeFields()
    {

        val methodN = methods.first { it.newMethodName == "clear" }.methodNode

        val matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(GETFIELD)).second

        addField("sentinel", matches[0])

        val foundField = classNode.fields.first { it.desc == String.format("L%s;", getClassAnalyserName(Node::class)) && it.name != fields.first { f -> f.fieldName == "sentinel" }.fieldNode.name}

        addField("current", foundField)

    }

    private fun findFirst()
    {

        val methodN = AnalyzerSearching.searchClassForMethod(classNode, listOf(ALOAD, ALOAD, GETFIELD, GETFIELD, PUTFIELD),
                String.format("(L%s;)V", getClassAnalyserName(Node::class))).first().first

        addMethod("addFirst", methodN)

    }

    private fun findLast()
    {

        val methodN = AnalyzerSearching.searchClassForMethod(classNode, listOf(GETFIELD, GETFIELD, PUTFIELD, ALOAD, GETFIELD),
                String.format("(L%s;)V", getClassAnalyserName(Node::class))).first().first

        addMethod("addLast", methodN)

    }

    override fun getFields()
    {

        findClear()

        findDequeFields()

        findFirst()

        findLast()

    }


}