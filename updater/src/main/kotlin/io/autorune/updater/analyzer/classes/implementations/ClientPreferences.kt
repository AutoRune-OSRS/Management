package io.autorune.updater.analyzer.classes.implementations

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.*
import io.autorune.updater.analyzer.classes.implementations.io.RSByteBuffer
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode
import java.lang.Exception

@CorrectFieldCount(7)
@CorrectMethodCount(1)
@DependsOn(RSByteBuffer::class)
class ClientPreferences : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            val match = classNode.fields.any { it.desc == String.format("L%s;", "java/util/LinkedHashMap") }

            if (!match)
                continue

            return classNode

        }
        return null
    }

    fun getBuffer()
    {

        val match = classNode.methods.first { it.desc.contains(String.format(")L%s;", getClassAnalyserName(RSByteBuffer::class)))}

        addMethod("toBuffer", match)

    }

    fun getParameters()
    {

        val decodeMn = methods.first { it.newMethodName == "toBuffer" }.methodNode

        val matches = AnalyzerSearching.searchMethodForFields(decodeMn, listOf(GETFIELD)).second

        addField("roofsHidden", matches[0])
        addField("titleMusicDisabled", matches[1])
        addField("windowMode", matches[2])
        addField("parameters", matches[3])
        addField("rememberedUsername", matches[5])
        addField("hideUsername", matches[7])
    }

    private fun getClientPreferences() {
        for (cN in classPool) {
            val field = cN.fields.find { it.desc == String.format("L%s;", classNode.name) && isMemberStatic(it.access) }
            if (field != null) {
                addFieldToClient("clientPreferences", cN.name, field)
                return
            }
        }
        throw Exception()
    }

    override fun getFields() {

        getBuffer()

        getParameters()

        getClientPreferences()

    }

}