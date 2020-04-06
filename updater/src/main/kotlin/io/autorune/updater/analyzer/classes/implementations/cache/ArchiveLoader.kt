package io.autorune.updater.analyzer.classes.implementations.cache

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(Archive::class)
class ArchiveLoader : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            val match = classNode.fields.count { it.desc == String.format("L%s;", getClassAnalyserName(Archive::class)) && !isMemberStatic(it.access) } == 1
                    && classNode.fields.count { it.desc == descInt && !isMemberStatic(it.access) } == 2
                    && classNode.fields.count { !isMemberStatic(it.access) } == 3

            if (match)
                return classNode

        }

        return null
    }


    override fun getFields() {

    }


}