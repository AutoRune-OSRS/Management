package io.autorune.updater.analyzer.classes.implementations.array

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.collection.Node
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(1)
@CorrectMethodCount(0)
@DependsOn(Node::class)
class ByteArrayNode : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyser(Node::class)?.classNode?.name)
                continue

            val match = classNode.fields.count { it.desc == "[B" && !Modifier.isStatic(it.access) } == 1
                    && classNode.methods.count { it.desc == "([B)V" && !Modifier.isStatic(it.access) } == 1
                    && classNode.methods.count { !Modifier.isStatic(it.access) } == 1

            if (match)
                return classNode

        }

        return null
    }

    private fun getByteArray() {
        addField("byteArray", classNode.fields[0])
    }

    override fun getFields() {
        getByteArray()
    }


}