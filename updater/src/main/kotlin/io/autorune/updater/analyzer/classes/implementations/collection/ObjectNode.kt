package io.autorune.updater.analyzer.classes.implementations.collection

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(1)
@CorrectMethodCount(0)
@DependsOn(Node::class)
class ObjectNode : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyser(Node::class)?.classNode?.name)
                continue

            val match = classNode.fields.count { it.desc == "Ljava/lang/Object;" && !Modifier.isStatic(it.access) } == 1

            if (match)
                return classNode

        }

        return null
    }

    private fun getObject() {
        addField("object", classNode.fields.first { it.desc == "Ljava/lang/Object;" && !Modifier.isStatic(it.access) })
    }

    override fun getFields() {
        getObject()
    }


}