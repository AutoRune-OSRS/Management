package io.autorune.updater.analyzer.classes.implementations.world

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(0)
class AbstractWorldMapIcon : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            if (!Modifier.isAbstract(classNode.access) || Modifier.isInterface(classNode.access))
                continue

            val match = classNode.methods.count { Modifier.isAbstract(it.access) } == 4

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {
    }


}