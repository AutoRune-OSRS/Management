package io.autorune.updater.analyzer.classes.implementations.grandexchange

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(GrandExchangeEvent::class, GrandExchangeOffer::class)
class GrandExchangeOfferTotalQuantityComparator : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (!classNode.interfaces.contains("java/util/Comparator"))
                continue

            val matches = AnalyzerSearching.searchClassForField(classNode, listOf(ALOAD, GETFIELD, GETFIELD, ALOAD, GETFIELD, GETFIELD),
                    String.format("(L%s;L%s;)I", getClassAnalyserName(GrandExchangeEvent::class), getClassAnalyserName(GrandExchangeEvent::class)))

            if (matches.size == 0 || matches.none { sf -> sf.name == getClassAnalyser(GrandExchangeOffer::class)?.fields?.first { it.fieldName == "totalQuantity" }?.fieldNode?.name })
                continue

            return classNode

        }
        return null
    }

    override fun getFields() {

    }

}