package io.autorune.updater.analyzer.classes.implementations.region

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
class Occluder : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            val match = classNode.fields.count { it.desc == descInt && !isMemberStatic(it.access) } == 18
                    && classNode.fields.count { !isMemberStatic(it.access) } == 18

            if (match)
                return classNode

        }
        return null
    }

    override fun getFields() {

    }

}