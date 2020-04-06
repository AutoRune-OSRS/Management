package io.autorune.updater.analyzer.classes.implementations.collection

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(DoublyNode::class)
class DoublyNodeDeque : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            val match = classNode.fields.count { !isMemberStatic(it.access) } == 1
                    && classNode.fields.any { it.desc == String.format("L%s;", getClassAnalyserName(DoublyNode::class)) && !isMemberStatic(it.access) }

            if (match)
                return classNode

        }
        return null
    }

    override fun getFields() {

    }

}