package io.autorune.updater.analyzer.classes.implementations.io

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.cache.Archive
import io.autorune.updater.analyzer.classes.implementations.collection.DoublyNode
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(DoublyNode::class, Archive::class)
class NetFileRequest : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyserName(DoublyNode::class))
                continue

            val match = classNode.fields.count { it.desc == String.format("L%s;", getClassAnalyserName(Archive::class)) && !isMemberStatic(it.access) } in 1..2
                    && classNode.fields.count { it.desc == descInt && !isMemberStatic(it.access) } == 1
                    && classNode.fields.count { it.desc == descByte && !isMemberStatic(it.access) } == 1

            if (match)
                return classNode

        }
        return null
    }

    override fun getFields() {

    }

}