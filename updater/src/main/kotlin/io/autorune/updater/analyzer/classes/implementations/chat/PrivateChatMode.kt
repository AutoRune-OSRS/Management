package io.autorune.updater.analyzer.classes.implementations.chat

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
class PrivateChatMode : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            val match = classNode.fields.count { it.desc == String.format("L%s;", classNode.name) } == 3
                    && classNode.fields.count { it.desc == descInt && !isMemberStatic(it.access) } == 1
                    && classNode.fields.count { !isMemberStatic(it.access) } == 1

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {
    }


}