package io.autorune.updater.analyzer.classes.implementations.collection

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(DoublyNode::class)
class IterableDoublyQueue : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (!classNode.interfaces.contains("java/lang/Iterable"))
                continue

            val match = classNode.fields.count { it.desc == String.format("L%s;", getClassAnalyser(DoublyNode::class)?.classNode?.name) && !Modifier.isStatic(it.access) } == 2

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {
    }


}