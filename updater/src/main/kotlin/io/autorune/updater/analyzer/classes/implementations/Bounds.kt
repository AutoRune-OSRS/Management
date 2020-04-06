package io.autorune.updater.analyzer.classes.implementations

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(0)
class Bounds : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            val match = classNode.fields.count { it.desc == descInt && !Modifier.isStatic(it.access) } == 4
                    && classNode.fields.count { !Modifier.isStatic(it.access) } == 4
                    && classNode.methods.count { it.desc == "(II)V" } == 3

            if (!match)
                continue

            return classNode

        }
        return null
    }

    override fun getFields() {

    }

}