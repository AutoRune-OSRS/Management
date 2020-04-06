package io.autorune.updater.analyzer.classes.implementations.widget

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.collection.Node
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(2)
@CorrectMethodCount(0)
@DependsOn(Node::class, Widget::class)
class WidgetNode : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyser(Node::class)?.classNode?.name)
                continue

            val match = classNode.fields.count { it.desc == "Z" && !Modifier.isStatic(it.access) } == 1 &&
                    classNode.fields.count { it.desc == "I" && !Modifier.isStatic(it.access) } == 2

            if (match)
                return classNode
        }
        return null
    }


    private fun findGroupField()
    {

        val methodN = getClassAnalyser(Widget::class)?.methods?.first { it.newMethodName == "runComponentCloseListeners" }?.methodNode ?: throw Exception()

        val matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(GETFIELD)).second

        val match = matches.first { it.owner == classNode.name }

        addField("group", match)

    }

    private fun findIdField()
    {

        val match = classNode.fields.first { fn -> fn.desc == descInt && !isMemberStatic(fn.access) && fn.name != fields.first { it.fieldName == "group" }.fieldNode.name }

        addField("id", match)

    }

    override fun getFields() {

        findGroupField()
        findIdField()

    }

}