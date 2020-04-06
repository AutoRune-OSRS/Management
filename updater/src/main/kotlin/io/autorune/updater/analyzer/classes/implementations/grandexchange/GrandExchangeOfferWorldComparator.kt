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
class GrandExchangeOfferWorldComparator : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (!classNode.interfaces.contains("java/util/Comparator"))
                continue

            val matches = AnalyzerSearching.searchClassForField(classNode, listOf(GETFIELD),
                    String.format("(L%s;L%s;)I", getClassAnalyserName(GrandExchangeEvent::class), getClassAnalyserName(GrandExchangeEvent::class)))

            if (matches.size == 0 || matches.any { match-> match.name != getClassAnalyser(GrandExchangeEvent::class)?.fields?.first { f -> f.fieldName == "world" }?.fieldNode?.name } )
                continue

            return classNode

        }
        return null
    }

    override fun getFields() {

    }

}