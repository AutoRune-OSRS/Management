package io.autorune.updater.analyzer.classes.implementations.audio.pcm

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.collection.Node
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(Node::class)
class PcmStreamMixerListener : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyser(Node::class)?.classNode?.name)
                continue

            val match = classNode.fields.size == 1 && classNode.methods.count { Modifier.isAbstract(it.access) } == 2

            if (!match)
                continue

            return classNode

        }
        return null
    }

    override fun getFields() {

    }

}