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

@CorrectFieldCount(46)
@CorrectMethodCount(3)
@DependsOn(DoublyNode::class, RSByteBuffer::class)
class ItemDefinition : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyser(DoublyNode::class)?.classNode?.name)
                continue

            val match = classNode.fields.count { it.desc == "[S" && !Modifier.isStatic(it.access) } == 4
                    && classNode.fields.count { it.desc == "[Ljava/lang/String;" && !Modifier.isStatic(it.access) } == 2

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

    private fun findOpcodeFields()
    {

        val node = methods.first { it.newMethodName == "decodeOpcode" }.methodNode

        val matches = AnalyzerSearching.searchMethodForFieldsInCodeOrder(node, PUTFIELD)

        val filteredMatches = AnalyzerUtils.filterFields(matches, fields)

        addField("model", filteredMatches[0])
        addField("name", filteredMatches[1])
        addField("zoom2d", filteredMatches[2])
        addField("xan2d", filteredMatches[3])
        addField("yan2d", filteredMatches[4])
        addField("offsetX2d", filteredMatches[5])
        addField("offsetY2d", filteredMatches[6])
        addField("stackable", filteredMatches[7])
        addField("price", filteredMatches[8])
        addField("members", filteredMatches[9])
        addField("maleModel", filteredMatches[10])
        addField("maleOffset", filteredMatches[11])
        addField("maleModel1", filteredMatches[12])
        addField("femaleModel", filteredMatches[13])
        addField("femaleOffset", filteredMatches[14])
        addField("femaleModel1", filteredMatches[15])
        addField("recolorFrom", filteredMatches[16])
        addField("recolorTo", filteredMatches[17])
        addField("reTextureFrom", filteredMatches[18])
        addField("reTextureTo", filteredMatches[19])
        addField("shiftClickIndex", filteredMatches[20])
        addField("isTradable", filteredMatches[21])
        addField("maleModel2", filteredMatches[22])
        addField("femaleModel2", filteredMatches[23])
        addField("maleHeadModel", filteredMatches[24])
        addField("femaleHeadModel", filteredMatches[25])
        addField("maleHeadModel2", filteredMatches[26])
        addField("femaleHeadModel2", filteredMatches[27])
        addField("zan2d", filteredMatches[28])
        addField("note", filteredMatches[29])
        addField("noteTemplate", filteredMatches[30])
        addField("stackIds", filteredMatches[31])
        addField("stackAmounts", filteredMatches[32])
        addField("resizeX", filteredMatches[33])
        addField("resizeY", filteredMatches[34])
        addField("resizeZ", filteredMatches[35])
        addField("ambient", filteredMatches[36])
        addField("contrast", filteredMatches[37])
        addField("team", filteredMatches[38])
        addField("unNotedId", filteredMatches[39])
        addField("notedId", filteredMatches[40])
        addField("placeholder", filteredMatches[41])
        addField("placeHolderTemplate", filteredMatches[42])
        addField("params", filteredMatches[43])

        val getFieldMatches = AnalyzerSearching.searchMethodForFieldsInCodeOrder(node, GETFIELD)

        val getFieldFilteredMatches = AnalyzerUtils.filterFields(getFieldMatches, fields)

        addField("groundActions", getFieldFilteredMatches[0])
        addField("inventoryActions", getFieldFilteredMatches[1])

    }

    private fun findFetchItemDefinition()
    {

        val matches = AnalyzerSearching.searchAllClassesForMethod(listOf(), String.format("(I)L%s;", classNode.name))

        addMethodToClient("fetchItemDefinition", matches[0].second.name, matches[0].first)


    }

    override fun getFields() {
        findDecode()
        findDecodeOpcode()
        findFetchItemDefinition()
        findOpcodeFields()
    }


}