package io.autorune.updater.analyzer.classes.implementations.definitions

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.collection.DoublyNode
import io.autorune.updater.analyzer.classes.implementations.io.RSByteBuffer
import io.autorune.updater.analyzer.classes.implementations.model.Model
import io.autorune.updater.analyzer.util.AnalyzerSearching
import io.autorune.updater.analyzer.classes.util.AnalyzerUtils
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(33)
@CorrectMethodCount(5)
@DependsOn(DoublyNode::class, RSByteBuffer::class, SequenceDefinition::class, Model::class)
class NpcDefinition : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyser(DoublyNode::class)?.classNode?.name)
                continue

            val match = classNode.fields.count { it.desc == "[S" && !Modifier.isStatic(it.access) } == 4
                    && classNode.fields.count { it.desc == "[Ljava/lang/String;" && !Modifier.isStatic(it.access) } == 1
                    && classNode.fields.count { it.desc == "Z" && !Modifier.isStatic(it.access) } < 6

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

        var matches = AnalyzerSearching.searchMethodForFieldsInCodeOrder(methodN, PUTFIELD)

        var filteredMatches = AnalyzerUtils.filterFields(matches, fields)

        addField("models", filteredMatches[0])
        addField("name", filteredMatches[1])
        addField("size", filteredMatches[2])
        addField("readySequence", filteredMatches[3])
        addField("walkSequence", filteredMatches[4])
        addField("turnLeftSequence", filteredMatches[5])
        addField("turnRightSequence", filteredMatches[6])
        addField("walkBackSequence", filteredMatches[7])
        addField("walkLeftSequence", filteredMatches[8])
        addField("walkRightSequence", filteredMatches[9])
        addField("recolorFrom", filteredMatches[10])
        addField("recolorTo", filteredMatches[11])
        addField("reTextureFrom", filteredMatches[12])
        addField("reTextureTo", filteredMatches[13])
        addField("headModels", filteredMatches[14])
        addField("drawMapDot", filteredMatches[15])
        addField("combatLevel", filteredMatches[16])
        addField("widthScale", filteredMatches[17])
        addField("heightScale", filteredMatches[18])
        addField("isVisible", filteredMatches[19])
        addField("ambient", filteredMatches[20])
        addField("contrast", filteredMatches[21])
        addField("headIconPrayer", filteredMatches[22])
        addField("rotation", filteredMatches[23])
        addField("canInteract", filteredMatches[24])
        addField("isClickable", filteredMatches[25])
        addField("isFollower", filteredMatches[26])
        addField("params", filteredMatches[27])
        addField("transformVarBit", filteredMatches[28])
        addField("transformVarp", filteredMatches[29])
        addField("transforms", filteredMatches[30])

        matches = AnalyzerSearching.searchMethodForFieldsInCodeOrder(methodN, GETFIELD)

        filteredMatches = AnalyzerUtils.filterFields(matches, fields)

        addField("actions", filteredMatches[0])

    }

    private fun findFetchNpcDefinition()
    {

        val matches = AnalyzerSearching.searchAllClassesForMethod(listOf(), String.format("(I)L%s;", classNode.name))

        addMethodToClient("fetchNpcDefinition", matches[0].second.name, matches[0].first)

    }

    fun findGetModel()
    {

        val methodN = classNode.methods.first { it.desc == String.format("(L%s;IL%s;I)L%s;", getClassAnalyserName(SequenceDefinition::class),
                getClassAnalyserName(SequenceDefinition::class), getClassAnalyserName(Model::class))}

        addMethod("model", methodN)

    }

    fun findNpcId()
    {

        val methodN = methods.first { it.newMethodName == "model" }.methodNode

        val matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(GETFIELD)).second

        addField("id", matches[1])

    }

    fun findTransform()
    {

        val mN = classNode.methods.first { it.desc == String.format("()L%s;", classNode.name) && !isMemberStatic(it.access) }

        addMethod("transform", mN)

    }

    override fun getFields()
    {

        findDecode()
        findDecodeOpcode()
        findFetchNpcDefinition()

        findGetModel()

        findNpcId()

        findTransform()
    }


}