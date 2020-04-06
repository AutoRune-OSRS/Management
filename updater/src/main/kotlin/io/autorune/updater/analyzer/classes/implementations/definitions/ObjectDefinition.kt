package io.autorune.updater.analyzer.classes.implementations.definitions

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.collection.DoublyNode
import io.autorune.updater.analyzer.classes.implementations.io.RSByteBuffer
import io.autorune.updater.analyzer.util.AnalyzerSearching
import io.autorune.updater.analyzer.classes.util.AnalyzerUtils
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(42)
@CorrectMethodCount(3)
@DependsOn(DoublyNode::class, RSByteBuffer::class)
class ObjectDefinition : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyser(DoublyNode::class)?.classNode?.name)
                continue

            val match = classNode.fields.count { it.desc == "[S" && !Modifier.isStatic(it.access) } == 4
                    && classNode.fields.count { it.desc == "[Ljava/lang/String;" && !Modifier.isStatic(it.access) } == 1
                    && classNode.fields.count { it.desc == "Z" && !Modifier.isStatic(it.access) } > 5

            if (match)
                return classNode

        }

        return null
    }

    private fun findDecode()
    {

        val match = classNode.methods.first { it.desc == String.format("(L%s;)V", getClassAnalyserName(RSByteBuffer::class)) && !isMemberStatic(it.access) }

        addMethod("decode", match)

    }

    private fun findDecodeOpcode()
    {

        val methodN = AnalyzerSearching.searchClassForMethod(classNode, String.format("(L%s;I)V", getClassAnalyserName(RSByteBuffer::class))).first()

        addMethod("decodeOpcode", methodN)

    }

    private fun findFetchNpcDefinition()
    {

        val matches = AnalyzerSearching.searchAllClassesForMethod(listOf(), String.format("(I)L%s;", classNode.name))

        addMethodToClient("fetchObjectDefinition", matches[0].second.name, matches[0].first)

    }

    private fun findOpcodeFields()
    {

        val node = methods.first { it.newMethodName == "decodeOpcode" }.methodNode

        val matches = AnalyzerSearching.searchMethodForFieldsInCodeOrder(node, PUTFIELD)

        val filteredMatches = AnalyzerUtils.filterFields(matches, fields)

        addField("models", filteredMatches[0])
        addField("modelIds", filteredMatches[1])
        addField("name", filteredMatches[2])
        addField("sizeX", filteredMatches[3])
        addField("sizeY", filteredMatches[4])
        addField("interactionClipping", filteredMatches[5])
        addField("projectileClipped", filteredMatches[6])
        addField("solid", filteredMatches[7])
        addField("terrainClipping", filteredMatches[8])
        addField("nonFlatShading", filteredMatches[9])
        addField("modelClipped", filteredMatches[10])
        addField("animationId", filteredMatches[11])
        addField("offsetMultiplier", filteredMatches[12])
        addField("ambient", filteredMatches[13])
        addField("contrast", filteredMatches[14])
        addField("recolorFrom", filteredMatches[15])
        addField("recolorTo", filteredMatches[16])
        addField("reTextureFrom", filteredMatches[17])
        addField("reTextureTo", filteredMatches[18])
        addField("rotated", filteredMatches[19])
        addField("clipped", filteredMatches[20])
        addField("modelSizeX", filteredMatches[21])
        addField("modelHeight", filteredMatches[22])
        addField("modelSizeY", filteredMatches[23])
        addField("mapSceneId", filteredMatches[24])
        addField("offsetX", filteredMatches[25])
        addField("offsetHeight", filteredMatches[26])
        addField("offsetY", filteredMatches[27])
        addField("isInteractive", filteredMatches[28])
        addField("isSolid", filteredMatches[29])
        addField("unknownField1", filteredMatches[30])
        addField("ambientSoundId", filteredMatches[31])
        addField("unknownSoundField1", filteredMatches[32])
        addField("unknownSoundField2", filteredMatches[33])
        addField("unknownSoundField3", filteredMatches[34])
        addField("soundEffectIds", filteredMatches[35])
        addField("mapIconId", filteredMatches[36])
        addField("params", filteredMatches[37])
        addField("transformVarBit", filteredMatches[38])
        addField("transformVarp", filteredMatches[39])
        addField("transforms", filteredMatches[40])

        val getFieldMatches = AnalyzerSearching.searchMethodForFieldsInCodeOrder(node, GETFIELD)

        val getFieldFilteredMatches = AnalyzerUtils.filterFields(getFieldMatches, fields)

        addField("actions", getFieldFilteredMatches[0])

    }

    override fun getFields() {
        findDecode()
        findDecodeOpcode()
        findFetchNpcDefinition()
        findOpcodeFields()
    }


}