package io.autorune.updater.analyzer.classes.implementations

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(RSEnum::class)
class GameType : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (!classNode.interfaces.contains(getClassAnalyserName(RSEnum::class)))
                continue

            val match = classNode.methods.any { it.desc == "("+descString+descString+"I)V" }

            if (match)
                return classNode

        }

        return null
    }


    override fun getFields() {
    }


}