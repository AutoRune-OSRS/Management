package io.autorune.updater.analyzer.classes.implementations.login

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.sprite.IndexedSprite
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(IndexedSprite::class)
class LoginMembers : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            val matches = AnalyzerSearching.searchClassForConstants(classNode, listOf(LDC), "()V").filter { it.second.name == "<clinit>" }

            val match = matches.any { match -> match.third.any { insn -> insn.cst == "##0.00" } }

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {
    }


}