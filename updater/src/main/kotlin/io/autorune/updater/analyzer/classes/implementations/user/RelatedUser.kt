package io.autorune.updater.analyzer.classes.implementations.user

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(3)
@CorrectMethodCount(0)
@DependsOn(BasicUser::class)
class RelatedUser : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyserName(BasicUser::class))
                continue

            val match = classNode.fields.count { it.desc == descInt && !Modifier.isStatic(it.access) } == 3

            if (!match)
                continue

            return classNode

        }
        return null
    }

    private fun getWorldId() {
        val matches = AnalyzerSearching.searchClassForField(classNode, listOf(PUTFIELD, ALOAD, ILOAD, PUTFIELD), "(II)V")
        addField("world", matches[0])
        addField("position", matches[1])
    }

    private fun getRankId() {
        val matches = AnalyzerSearching.searchClassForField(classNode, listOf(PUTFIELD, ALOAD, ILOAD, PUTFIELD), "(II)V")
        val field = classNode.fields.first { fn -> matches.none { fisn -> fn.name == fisn.name } }
        addField("rank", field)
    }

    override fun getFields() {
        getWorldId()
        getRankId()
    }

}