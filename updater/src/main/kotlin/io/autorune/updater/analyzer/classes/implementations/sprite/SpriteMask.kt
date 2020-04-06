package io.autorune.updater.analyzer.classes.implementations.sprite

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.collection.DoublyNode
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(DoublyNode::class)
class SpriteMask : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyserName(DoublyNode::class))
                continue

            val match = classNode.fields.count { it.desc == descInt && !isMemberStatic(it.access) } == 2
                    && classNode.fields.count { it.desc == descIntArr && !isMemberStatic(it.access) } == 2
                    && classNode.fields.count { !isMemberStatic(it.access) } == 4

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {

    }


}