package io.autorune.updater.analyzer.classes.implementations.audio

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.collection.Deque
import io.autorune.updater.analyzer.classes.implementations.collection.Node
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(Node::class, Deque::class)
class AmbientSound : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyser(Node::class)?.classNode?.name)
                continue

            for (fieldNode in classNode.fields) {
                if (fieldNode.desc == String.format("L%s;", getClassAnalyser(Deque::class)?.classNode?.name))
                    return classNode
            }

        }

        return null
    }

    override fun getFields() {

    }


}