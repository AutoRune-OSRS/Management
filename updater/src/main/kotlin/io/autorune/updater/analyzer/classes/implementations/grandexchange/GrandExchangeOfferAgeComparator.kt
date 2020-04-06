package io.autorune.updater.analyzer.classes.implementations.grandexchange

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(GrandExchangeEvent::class)
class GrandExchangeOfferAgeComparator : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (!classNode.interfaces.contains("java/util/Comparator"))
                continue

            val matches = AnalyzerSearching.searchClassForField(classNode, listOf(ALOAD, GETFIELD, ALOAD, GETFIELD))

            if (matches.size == 0 || matches[0].name != getClassAnalyser(GrandExchangeEvent::class)?.fields?.first { it.fieldName == "age" }?.fieldNode?.name)
                continue

            return classNode

        }
        return null
    }

    override fun getFields() {

    }

}

