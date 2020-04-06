package io.autorune.updater.analyzer.classes.implementations.grandexchange

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.io.PacketBuffer
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(GrandExchangeEvent::class, PacketBuffer::class)
class GrandExchangeOfferNameComparator : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (!classNode.interfaces.contains("java/util/Comparator"))
                continue

            val matches = AnalyzerSearching.searchClassForMethod(classNode,
                    listOf(ALOAD, INVOKEVIRTUAL, ALOAD, INVOKEVIRTUAL, INVOKEVIRTUAL),
                    String.format("(L%s;L%s;)I", getClassAnalyserName(GrandExchangeEvent::class), getClassAnalyserName(GrandExchangeEvent::class)))

            if (matches.size == 0)
                continue

            return classNode

        }
        return null
    }

    override fun getFields() {


    }

}