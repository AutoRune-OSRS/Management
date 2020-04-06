package io.autorune.updater.analyzer.classes.implementations.container

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.collection.HashTable
import io.autorune.updater.analyzer.classes.implementations.collection.Node
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(3)
@CorrectMethodCount(0)
@DependsOn(Node::class, HashTable::class)
class ItemContainer : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyser(Node::class)?.classNode?.name)
                continue

            val match = classNode.fields.count { it.desc == "[I" && !Modifier.isStatic(it.access) } == 2

            if (match)
                return classNode
        }
        return null
    }

    private fun findTable()
    {

        val match = classNode.fields.first { it.desc == String.format("L%s;", getClassAnalyserName(HashTable::class)) }

        addFieldToClient("itemContainers", classNode.name, match)

    }

    private fun findFields()
    {

        var match = AnalyzerSearching.searchClassForField(classNode, listOf(ICONST_M1, IASTORE, PUTFIELD))

        addField("itemIds", match.first())

        match = AnalyzerSearching.searchClassForField(classNode, listOf(ICONST_0, IASTORE, PUTFIELD))

        addField("itemQuantities", match.first())

    }

    override fun getFields()
    {
        findFields()
        findTable()
    }

}

