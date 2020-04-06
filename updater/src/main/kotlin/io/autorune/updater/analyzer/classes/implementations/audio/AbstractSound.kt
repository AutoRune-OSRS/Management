package io.autorune.updater.analyzer.classes.implementations.audio

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.collection.Node
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(1)
@CorrectMethodCount(0)
@DependsOn(Node::class)
class AbstractSound : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyser(Node::class)?.classNode?.name)
                continue

            val match = classNode.fields.count { !isMemberStatic(it.access) } == 1
                    && classNode.fields.count { it.desc == descInt && !isMemberStatic(it.access) } == 1
                    && classNode.methods.count { !isMemberStatic(it.access) } == 1
                    && classNode.methods.count { it.desc == "()V" && !isMemberStatic(it.access) } == 1

            if (match)
                return classNode

        }

        return null
    }

    private fun getPosition() {
        addField("position", classNode.fields.first { !isMemberStatic(it.access) })
    }

    override fun getFields() {
        getPosition()
    }


}