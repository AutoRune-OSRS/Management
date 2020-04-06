package io.autorune.updater.analyzer.classes.implementations.combat

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.collection.Node
import io.autorune.updater.analyzer.classes.implementations.definitions.HealthBarDefinition
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(2)
@CorrectMethodCount(3)
@DependsOn(Node::class, HealthBarUpdate::class, HealthBarDefinition::class)
class HealthBar : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyser(Node::class)?.classNode?.name)
                continue

            val match = classNode.methods.count { it.desc == "(IIII)V" } == 1

            if (!match)
                continue

            return classNode

        }
        return null
    }

    private fun applyHitUpdate()
    {

        val match = classNode.methods.first { it.desc == "(IIII)V" && !isMemberStatic(it.access) }

        addMethod("applyHitUpdate", match)

    }

    private fun fetchHealthBarUpdate()
    {

        val match = classNode.methods.first { it.desc.contains(String.format(")L%s;", getClassAnalyserName(HealthBarUpdate::class))) && !isMemberStatic(it.access) }

        addMethod("fetchHealthBarUpdate", match)

    }

    private fun hasUpdates()
    {

        val match = classNode.methods.first { it.desc == "()Z" && !isMemberStatic(it.access) }

        addMethod("hasNoUpdates", match)

    }

    private fun findUpdatesField()
    {

        val methodN = methods.first { it.newMethodName == "hasNoUpdates" }.methodNode

        val matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(GETFIELD)).second

        addField("updates", matches[0])

    }

    private fun findDefinitionField()
    {

        val match = classNode.fields.first { it.desc == String.format("L%s;", getClassAnalyserName(HealthBarDefinition::class)) }

        addField("definition", match)

    }

    override fun getFields() {

        applyHitUpdate()
        fetchHealthBarUpdate()
        hasUpdates()
        findUpdatesField()
        findDefinitionField()
    }

}