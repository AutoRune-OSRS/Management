package io.autorune.updater.analyzer.classes.implementations.system

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.collection.Node
import io.autorune.updater.analyzer.classes.implementations.io.RSByteBuffer
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(25)
@CorrectMethodCount(0)
@DependsOn(Node::class, RSByteBuffer::class)
class MachineInfo : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyser(Node::class)?.classNode?.name)
                continue

            val match = classNode.fields.count { it.desc == "[I" || it.desc == "I" } == 16
            if (!match)
                continue

            return classNode

        }
        return null
    }

    private fun getMachineInfo() {

        val matches = AnalyzerSearching.searchClassForField(classNode, listOf(ALOAD, ALOAD, GETFIELD),
                String.format("(L%s;)V", getClassAnalyser(RSByteBuffer::class)?.classNode?.name))

        //TODO: Update this

        addField("osType", matches[0])
        addField("isOS64Bit", matches[1])
        addField("osVersion", matches[2])
        addField("javaVendor", matches[3])
        addField("javaVersionMajor", matches[4])
        addField("javaVersionMinor", matches[5])
        addField("javaVersionBuild", matches[6])
        addField("usingConsole", matches[7])
        addField("maxMemory", matches[8])
        addField("availableProcessors", matches[9])
        addField("availableMemory", matches[10])
        addField("processorClockSpeed", matches[11])
        addField("graphicsString1", matches[12])
        addField("graphicsString2", matches[13])
        addField("graphicsString3", matches[14])
        addField("graphicsString4", matches[15])
        addField("graphicsDriverYear", matches[16])
        addField("graphicsDriverMonth", matches[17])
        addField("processorVendor", matches[18])
        addField("processorBrandString", matches[19])
        addField("processorCount", matches[20])
        addField("processorBrandId", matches[21])
        addField("processorFeatures", matches[22])
        addField("processorModel", matches[23])
        addField("unknownString", matches[24])

    }

    override fun getFields() {
        getMachineInfo()
    }

}