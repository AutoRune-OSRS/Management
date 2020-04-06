package io.autorune.updater.analyzer.classes.implementations.dateandtime

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(0)
class AbstractTimer : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            if (!Modifier.isAbstract(classNode.access))
                continue

            val match = classNode.methods.any { Modifier.isAbstract(it.access) && it.desc == "(II)I" }

            if (!match)
                continue

            return classNode

        }
        return null
    }

    override fun getFields() {

    }

}