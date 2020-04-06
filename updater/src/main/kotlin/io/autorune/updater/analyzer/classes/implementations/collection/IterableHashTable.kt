package io.autorune.updater.analyzer.classes.implementations.collection

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(Node::class)
class IterableHashTable : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != "java/lang/Object" || classNode.interfaces.isEmpty())
                continue

            val nodeClassName = getClassAnalyser(Node::class)?.classNode?.name

            val match = classNode.fields.count { it.desc == String.format("L%s;", nodeClassName) && !Modifier.isStatic(it.access) } == 2
                    && classNode.fields.count { it.desc == String.format("[L%s;", nodeClassName) && !Modifier.isStatic(it.access) } == 1

            if (match)
                return classNode

        }

        return null
    }



    override fun getFields() {

    }


}