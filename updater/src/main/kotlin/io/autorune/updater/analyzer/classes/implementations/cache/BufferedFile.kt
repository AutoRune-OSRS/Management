package io.autorune.updater.analyzer.classes.implementations.cache

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(0)
class BufferedFile : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            val match = classNode.fields.count { it.desc == descLong && !Modifier.isStatic(it.access) } == 6

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {
    }


}