package io.autorune.updater.analyzer.classes.implementations.user

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(RelatedUser::class)
class Friend : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyserName(RelatedUser::class))
                continue

            val match = classNode.fields.count { it.desc == descBool && !isMemberStatic(it.access) } == 2
                    && classNode.fields.count { !isMemberStatic(it.access) } == 2

            if (!match)
                continue

            return classNode

        }
        return null
    }

    override fun getFields() {

    }

}