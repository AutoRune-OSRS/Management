package io.autorune.updater.analyzer.classes.implementations

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
class DefaultsGroup : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            if (classNode.methods.none { it.name == "<clinit>" })
                continue


            val clInitMethod = classNode.methods.first { it.name == "<clinit>" }

            val match = AnalyzerSearching.searchMethodForFields(clInitMethod, listOf(NEW, DUP, ICONST_3, INVOKESPECIAL)).first
                    && classNode.fields.count { it.desc == String.format("L%s;", classNode.name) } == 1

            if (!match)
                continue

            return classNode

        }
        return null
    }

    override fun getFields() {

    }

}