package io.autorune.updater.analyzer.classes.implementations.array

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(0)
class AbstractByteArrayCopier : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (!Modifier.isAbstract(classNode.access))
                continue

            val match = classNode.methods.count { Modifier.isAbstract(it.access) } == 2
                    && classNode.methods.count { it.desc.contains(descByteArr) && Modifier.isAbstract(it.access) } == 2
                    && classNode.fields.count { !Modifier.isStatic(it.access) } == 0

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {
    }


}