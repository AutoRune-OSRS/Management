package io.autorune.updater.analyzer.classes.implementations.entity

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.definitions.NpcDefinition
import io.autorune.updater.analyzer.classes.implementations.model.Model
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(1)
@CorrectMethodCount(1)
@DependsOn(Actor::class, NpcDefinition::class, Model::class)
class Npc : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyserName(Actor::class))
                continue

            val match = classNode.fields.any { it.desc == String.format("L%s;", getClassAnalyserName(NpcDefinition::class)) }

            if (match)
                return classNode

        }
        return null
    }

    fun findDefinitionField()
    {

        val match = classNode.fields.first { String.format("L%s;", getClassAnalyserName(NpcDefinition::class)) == it.desc && !isMemberStatic(it.access) }

        addField("definition", match)

    }

    fun findGetModel()
    {

        val methodN = classNode.methods.first { it.desc == String.format("()L%s;", getClassAnalyserName(Model::class)) }

        addMethod("model", methodN)

    }

    override fun getFields() {

        findDefinitionField()

        findGetModel()

    }

}