package io.autorune.updater.analyzer.classes.implementations.grandexchange

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.io.RSByteBuffer
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(7)
@CorrectMethodCount(0)
@DependsOn(RSByteBuffer::class)
class GrandExchangeOffer : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            val match = classNode.methods.any { it.desc == String.format("(L%s;Z)V", getClassAnalyserName(RSByteBuffer::class)) }
                    && classNode.fields.any { it.desc == descByte && !isMemberStatic(it.access) }

            if (!match)
                continue

            return classNode

        }

        return null

    }

    private fun getOfferFields() {

        val matches = AnalyzerSearching.searchClassForField(classNode,
                listOf(ALOAD, ALOAD, INVOKEVIRTUAL, PUTFIELD),
                String.format("(L%s;Z)V", getClassAnalyserName(RSByteBuffer::class)))

        addField("state", matches[0])
        addField("id", matches[1])
        addField("unitPrice", matches[2])
        addField("totalQuantity", matches[3])
        addField("currentQuantity", matches[4])
        addField("currentPrice", matches[5])

    }

    private fun getGrandExchangeOffers() {
        for (cN in classPool) {
            val field = cN.fields.find { it.desc == String.format("[L%s;", classNode.name) && isMemberStatic(it.access) }
            if (field != null) {
                addFieldToClient("grandExchangeOffers", cN.name, field)
                break
            }
        }
    }

    override fun getFields() {
        getOfferFields()
        getGrandExchangeOffers()
    }

}