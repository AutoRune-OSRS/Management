package io.autorune.updater.analyzer.classes.implementations.dateandtime

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
class Calendar : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            val match = classNode.fields.any { it.desc == "Ljava/util/Calendar;" }
                    && classNode.fields.any { it.desc == descStringArrArr }

            if (match)
                return classNode

        }
        return null
    }

    override fun getFields() {

    }

}