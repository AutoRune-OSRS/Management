package io.autorune.updater.analyzer.classes.implementations.grandexchange

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.io.RSByteBuffer
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(3)
@CorrectMethodCount(0)
@DependsOn(RSByteBuffer::class, GrandExchangeOffer::class)
class GrandExchangeEvent : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            val match = classNode.methods.any { it.desc == String.format("(L%s;BI)V", getClassAnalyserName(RSByteBuffer::class)) }

            if (!match)
                continue

            return classNode

        }

        return null

    }

    private fun getWorld() {
        val field = classNode.fields.first { it.desc == descInt && !isMemberStatic(it.access) }
        addField("world", field)
    }

    private fun getAge() {
        val field = classNode.fields.first { it.desc == descLong && !isMemberStatic(it.access) }
        addField("age", field)
    }

    private fun getOffer() {
        val field = classNode.fields.first { it.desc == String.format("L%s;", getClassAnalyserName(GrandExchangeOffer::class)) && !isMemberStatic(it.access) }
        addField("offer", field)
    }

    override fun getFields() {
        getWorld()
        getAge()
        getOffer()
    }

}