package io.autorune.updater.analyzer.classes.implementations.graphics

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(0)
class AbstractRasterProvider : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (!Modifier.isAbstract(classNode.access))
                continue

            val match = classNode.methods.count { Modifier.isAbstract(it.access) } == 2
                    && classNode.fields.count { it.desc == descInt && !Modifier.isStatic(it.access) } == 2
                    && classNode.fields.count { it.desc == descIntArr && !Modifier.isStatic(it.access) } == 1

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {
    }


}