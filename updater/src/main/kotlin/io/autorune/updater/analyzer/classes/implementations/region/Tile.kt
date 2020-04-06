package io.autorune.updater.analyzer.classes.implementations.region

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.collection.Node
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(6)
@CorrectMethodCount(0)
@DependsOn(Node::class, Scene::class)
class Tile : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyser(Node::class)?.classNode?.name)
                continue

            val match = classNode.methods.count { it.desc == "(III)V" && !Modifier.isStatic(it.access) } == 1

            if (!match)
                continue

            return classNode

        }
        return null
    }

    private fun findFields()
    {

        val methodN = getClassAnalyser(Scene::class)?.methods?.first { it.newMethodName == "setLinkBelow" }?.methodNode ?: return

        val matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(GETFIELD)).second

        addField("plane", matches[3])
        addField("gameObjectCount", matches[4])
        addField("gameObjects", matches[5])
        addField("tag", matches[6])
        addField("startX", matches[7])
        addField("startY", matches[8])
    }

    override fun getFields()
    {
        findFields()
    }

}