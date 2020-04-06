package io.autorune.updater.analyzer.classes.implementations.widget

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.collection.Node
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(Node::class)
class ScriptEvent : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyser(Node::class)?.classNode?.name)
                continue

            val match = classNode.methods.any { it.desc == "([Ljava/lang/Object;)V" }

            if (match)
                return classNode
        }
        return null
    }

    override fun getFields() {

    }

}