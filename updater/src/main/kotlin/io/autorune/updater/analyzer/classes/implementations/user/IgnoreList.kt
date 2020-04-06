package io.autorune.updater.analyzer.classes.implementations.user

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.chat.ClanChannel
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(UserList::class, FriendsList::class, ClanChannel::class)
class IgnoreList : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyserName(UserList::class)
                    || classNode.name == getClassAnalyserName(FriendsList::class)
                    || classNode.name == getClassAnalyserName(ClanChannel::class))
                continue

            return classNode

        }
        return null
    }

    override fun getFields() {

    }

}