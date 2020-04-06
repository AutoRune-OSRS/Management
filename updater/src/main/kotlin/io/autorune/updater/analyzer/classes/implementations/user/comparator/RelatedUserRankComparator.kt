package io.autorune.updater.analyzer.classes.implementations.user.comparator

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.comparator.AbstractComparator
import io.autorune.updater.analyzer.classes.implementations.user.RelatedUser
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(AbstractComparator::class, RelatedUser::class)
class RelatedUserRankComparator : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyserName(AbstractComparator::class))
                continue

            val match = classNode.fields.count { it.desc == descBool && !isMemberStatic(it.access) } == 1
                    && classNode.fields.count { !isMemberStatic(it.access) } == 1

            if (!match)
                continue

            val matches = AnalyzerSearching.searchClassForField(classNode, listOf(ALOAD, GETFIELD, ALOAD, GETFIELD, IF_ICMPEQ, ALOAD, GETFIELD, IFEQ))

            if (matches.size == 0 || matches[0].name != getClassAnalyser(RelatedUser::class)?.fields?.first { it.fieldName == "rank" }?.fieldNode?.name)
                continue

            return classNode

        }
        return null
    }

    override fun getFields() {

    }

}