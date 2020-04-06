package io.autorune.updater.analyzer.classes.implementations.chat

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(Message::class)
class ClanMessages : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            val match = classNode.fields.count { it.desc == descInt && !isMemberStatic(it.access) } == 1
                    && classNode.fields.count { it.desc == String.format("[L%s;", getClassAnalyserName(Message::class)) } == 1

            if (match)
                return classNode


        }
        return null
    }

    override fun getFields() {

    }

}