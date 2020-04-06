package io.autorune.updater.analyzer.classes.implementations

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(2)
@CorrectMethodCount(2)
@DependsOn(RSEnum::class)
class Language : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (!classNode.interfaces.contains(getClassAnalyserName(RSEnum::class)))
                continue

            val match = classNode.fields.count { it.desc == String.format("L%s;", classNode.name) } == 7

            if (match)
                return classNode

        }

        return null
    }

    fun findLanguage()
    {

        val match = classNode.methods.first { it.desc == "()Ljava/lang/String;" }

        addMethod("language", match)

    }

    fun findOrdinal()
    {

        val match = classNode.methods.first { !isMemberStatic(it.access) && it.desc == "()I" }

        addMethod("ordinal", match)

    }

    fun findFields()
    {

        val langMn = methods.first { it.newMethodName == "language" }.methodNode

        val languageMatches = AnalyzerSearching.searchMethodForFields(langMn, listOf(GETFIELD)).second

        addField("language", languageMatches[0])

        val ordinalMn = methods.first { it.newMethodName == "ordinal" }.methodNode

        val ordinalMatches = AnalyzerSearching.searchMethodForFields(ordinalMn, listOf(GETFIELD)).second

        addField("id", ordinalMatches[0])

    }

    override fun getFields() {

        findLanguage()
        findOrdinal()

        findFields()

    }


}