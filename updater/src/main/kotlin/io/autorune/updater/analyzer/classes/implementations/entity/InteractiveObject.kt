package io.autorune.updater.analyzer.classes.implementations.entity

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.region.Scene
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(12)
@CorrectMethodCount(0)
@DependsOn(Entity::class, Scene::class)
class InteractiveObject : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            val match = classNode.fields.any { it.desc == String.format("L%s;", getClassAnalyserName(Entity::class)) }
                    && classNode.fields.count { it.desc == descInt && !isMemberStatic(it.access) } == 12

            if (!match)
                continue

            return classNode

        }

        return null

    }

    private fun findFields()
    {

        val methodN = getClassAnalyser(Scene::class)?.methods?.first { it.newMethodName == "addInteractiveObject" }?.methodNode ?: return

        val matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(PUTFIELD)).second

        addField("hash", matches[0])
        addField("flags", matches[1])
        addField("plane", matches[2])
        addField("centerX", matches[3])
        addField("centerY", matches[4])
        addField("height", matches[5])
        addField("entity", matches[6])
        addField("orientation", matches[7])
        addField("startX", matches[8])
        addField("startY", matches[9])
        addField("endX", matches[10])
        addField("endY", matches[11])
    }

    override fun getFields() {
        findFields()
    }

}