package io.autorune.updater.analyzer.classes.implementations.model

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(0)
class VertexNormal : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            val match = classNode.fields.count { it.desc == descInt && !Modifier.isStatic(it.access) } == 4
                    && classNode.fields.count { !Modifier.isStatic(it.access) } == 4
                    && classNode.methods.count { !isMemberStatic(it.access) } == 2
                    && classNode.methods.any { it.desc == String.format("(L%s;)V", classNode.name) && !isMemberStatic(it.access) }

            if (!match)
                continue

            return classNode

        }
        return null
    }

    override fun getFields() {

    }

}