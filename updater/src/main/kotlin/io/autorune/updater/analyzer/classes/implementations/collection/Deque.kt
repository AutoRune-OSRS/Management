package io.autorune.updater.analyzer.classes.implementations.collection

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(Node::class)
class Deque : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {
            val match = classNode.methods.count { it.desc == String.format("()L%s;", getClassAnalyser(Node::class)?.classNode?.name) } == 6
            if (match)
                return classNode
        }
        return null
    }

    override fun getFields()
    {

    }


}