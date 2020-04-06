package io.autorune.updater.analyzer.classes.implementations.comparator

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(0)
class AbstractComparator : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (!Modifier.isAbstract(classNode.access))
                continue

            val match = classNode.fields.any { it.desc == "Ljava/util/Comparator;" }
                    && classNode.fields.none { it.desc == "Ljava/util/HashMap;" }

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {
    }


}