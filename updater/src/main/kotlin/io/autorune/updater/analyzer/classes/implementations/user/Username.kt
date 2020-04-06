package io.autorune.updater.analyzer.classes.implementations.user

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(2)
@CorrectMethodCount(1)
class Username : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (!classNode.interfaces.contains("java/lang/Comparable"))
                continue

            val match = classNode.fields.count { it.desc == descString && !Modifier.isStatic(it.access) } == 2

            if (!match)
                continue

            return classNode

        }
        return null
    }

    private fun findCleanNameFields() {

        val methodMatch = classNode.methods.first { it.desc == "()Z" && !isMemberStatic(it.access) }

        addMethod("hasCleanName", methodMatch)

        val matches = AnalyzerSearching.searchMethodForFields(methodMatch, listOf(GETFIELD)).second

        addField("cleanName", matches[0])

    }

    private fun findNameField() {

        val field = classNode.fields.first { fn -> fn.desc == descString && fields.none { it.fieldNode.name == fn.name } }

        addField("name", field)

    }

    override fun getFields() {

        findCleanNameFields()
        findNameField()

    }

}