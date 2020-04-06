package io.autorune.updater.analyzer.classes.implementations.chat

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.user.RelatedUser
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(RelatedUser::class)
class ClanMember : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyserName(RelatedUser::class))
                continue

            val match = classNode.fields.count { !isMemberStatic(it.access) } == 2
                    && classNode.fields.none { it.desc == descBool && !isMemberStatic(it.access) }

            if (match)
                return classNode

        }
        return null
    }

    override fun getFields() {

    }

}