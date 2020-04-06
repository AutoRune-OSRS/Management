package io.autorune.updater.analyzer.classes.implementations

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
class StringConstants : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != "java/lang/Object" || classNode.interfaces.isNotEmpty())
                continue

            val match = classNode.fields.count { it.desc == descString } > 20
                    && classNode.fields.none { !isMemberStatic(it.access) }

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {

    }


}