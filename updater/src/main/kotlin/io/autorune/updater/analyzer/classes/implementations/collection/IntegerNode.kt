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
class IntegerNode : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyser(Node::class)?.classNode?.name)
                continue

            val match = classNode.fields.count { it.desc == "I" && !Modifier.isStatic(it.access) } == 1
                     && classNode.methods.count { it.desc == "(I)V" && !Modifier.isStatic(it.access) } == 1
                    && classNode.methods.count { !Modifier.isStatic(it.access) } == 1

            if (match)
                return classNode

        }

        return null
    }

    private fun getIntegerValue() {
        addField("integer", classNode.fields.first { it.desc == "I" && !isMemberStatic(it.access) })
    }

    override fun getFields() {
        getIntegerValue()
    }


}