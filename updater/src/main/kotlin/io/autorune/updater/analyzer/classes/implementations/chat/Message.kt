package io.autorune.updater.analyzer.classes.implementations.chat

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.TriBool
import io.autorune.updater.analyzer.classes.implementations.collection.DoublyNode
import io.autorune.updater.analyzer.classes.implementations.user.Username
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(DoublyNode::class, Username::class, TriBool::class)
class Message : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyserName(DoublyNode::class))
                continue

            val match = classNode.fields.count { it.desc == String.format("L%s;", getClassAnalyserName(Username::class)) } == 1
                    && classNode.fields.count { it.desc == String.format("L%s;", getClassAnalyserName(TriBool::class)) } == 2

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {
    }


}