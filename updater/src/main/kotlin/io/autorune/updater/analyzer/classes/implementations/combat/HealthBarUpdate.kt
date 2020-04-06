package io.autorune.updater.analyzer.classes.implementations.combat

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.collection.Node
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(4)
@CorrectMethodCount(1)
@DependsOn(Node::class)
class HealthBarUpdate : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyser(Node::class)?.classNode?.name)
                continue

            val match = classNode.methods.count { it.desc == "(IIII)V" } == 2

            if (!match)
                continue

            return classNode

        }
        return null
    }

    fun update()
    {

        val match = classNode.methods.first { it.name != "<init>" && it.desc == "(IIII)V" && !isMemberStatic(it.access) }

        addMethod("update", match)

    }

    fun hookFields()
    {

        val methodNode = methods.first { it.newMethodName == "update" }.methodNode

        val matches = AnalyzerSearching.searchMethodForFields(methodNode, listOf(PUTFIELD)).second

        addField("cycle", matches[0])
        addField("health", matches[1])
        addField("health2", matches[2])
        addField("cycleOffset", matches[3])

    }

    override fun getFields() {

        update()

        hookFields()

    }

}