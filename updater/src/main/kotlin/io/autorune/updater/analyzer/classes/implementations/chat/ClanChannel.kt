package io.autorune.updater.analyzer.classes.implementations.chat

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.user.User
import io.autorune.updater.analyzer.classes.implementations.user.UserList
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(UserList::class, User::class)
class ClanChannel : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyserName(UserList::class))
                continue

            val match = classNode.fields.any { it.desc == String.format("L%s;", getClassAnalyserName(User::class)) }

            if (match)
                return classNode

        }
        return null
    }

    override fun getFields() {

    }

}