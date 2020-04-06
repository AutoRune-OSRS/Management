package io.autorune.updater.analyzer.classes.implementations.io

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(0)
class AbstractSocket : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (!Modifier.isAbstract(classNode.access))
                continue

            val match = classNode.methods.count { Modifier.isAbstract(it.access) && it.desc.contains("[BII") } == 2
                    && classNode.methods.count { Modifier.isAbstract(it.access) } == 6

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {
    }


}