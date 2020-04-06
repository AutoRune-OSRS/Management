package io.autorune.updater.analyzer.classes.implementations.`var`

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
class Varcs : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.interfaces.isNotEmpty() || classNode.superName != "java/lang/Object")
                continue

            val match = classNode.fields.any { it.desc == String.format("L%s;", "java/util/Map") }
                    && classNode.fields.any { it.desc == descStringArr }

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {
    }


}