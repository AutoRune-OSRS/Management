package io.autorune.updater.analyzer.classes.implementations.font

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.array.AbstractByteArrayCopier
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(AbstractByteArrayCopier::class)
class AbstractFont : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (!Modifier.isAbstract(classNode.access))
                continue

            if (getClassAnalyser(AbstractByteArrayCopier::class)?.classNode?.name == classNode.name)
                continue

            val match = classNode.methods.count { Modifier.isAbstract(it.access) } == 2
                    && classNode.methods.count { it.desc.contains(descByteArr) && Modifier.isAbstract(it.access) } == 2

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {
    }


}