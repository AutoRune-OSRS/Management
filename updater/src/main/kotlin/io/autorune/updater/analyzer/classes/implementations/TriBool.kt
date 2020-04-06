package io.autorune.updater.analyzer.classes.implementations

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.chat.ClanMember
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(ClanMember::class)
class TriBool : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        val fieldRef = getClassAnalyser(ClanMember::class)?.classNode?.fields?.first { !isMemberStatic(it.access) }
        return classPool.first { fieldRef?.desc == String.format("L%s;", it.name)}
    }

    override fun getFields() {

    }

}