package io.autorune.updater.analyzer.classes.implementations.collection

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
class IntegerHashTable : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            val match = classNode.fields.count { it.desc == descIntArr && !isMemberStatic(it.access) } == 1
                    && classNode.methods.count { !isMemberStatic(it.access) } == 2
                    && classNode.methods.count { it.desc == "(I)I" && !isMemberStatic(it.access) } == 1

            if (match)
                return classNode

        }
        return null

    }

    override fun getFields() {

    }

}