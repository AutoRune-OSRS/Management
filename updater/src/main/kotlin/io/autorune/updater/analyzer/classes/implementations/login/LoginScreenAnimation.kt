package io.autorune.updater.analyzer.classes.implementations.login

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
class LoginScreenAnimation : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            val match = classNode.fields.count { it.desc == descIntArr && !isMemberStatic(it.access) } == 9

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {
    }


}