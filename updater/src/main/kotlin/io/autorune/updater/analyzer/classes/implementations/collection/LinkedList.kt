package io.autorune.updater.analyzer.classes.implementations.collection

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(Link::class)
class LinkedList : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            val linkClassName = getClassAnalyser(Link::class)?.classNode?.name

            if (classNode.name == linkClassName)
                continue

            val match = classNode.fields.count { it.desc == String.format("L%s;", linkClassName) } == 2

            if (match)
                return classNode

        }
        return null
    }



    override fun getFields() {

    }
}