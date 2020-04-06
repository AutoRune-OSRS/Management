package io.autorune.updater.analyzer.classes.implementations.definitions

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.collection.DoublyNode
import io.autorune.updater.analyzer.classes.implementations.io.RSByteBuffer
import io.autorune.updater.analyzer.classes.implementations.sprite.Sprite
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(9)
@CorrectMethodCount(3)
@DependsOn(DoublyNode::class, Sprite::class, RSByteBuffer::class)
class HealthBarDefinition : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyser(DoublyNode::class)?.classNode?.name)
                continue

            if (classNode.interfaces.isNotEmpty())
                continue

            val match = classNode.fields.count { it.desc == descInt && !Modifier.isStatic(it.access) } == 10
                    && classNode.fields.count { !Modifier.isStatic(it.access) } == 10

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

    private fun findFetchHealthBarDefinition()
    {

        val matches = AnalyzerSearching.searchAllClassesForMethod(listOf(), String.format("(I)L%s;", classNode.name))

        addMethodToClient("fetchHealthBarDefinition", matches[0].second.name, matches[0].first)

    }


    fun hookFields()
    {

        val decodeMn = methods.first { it.newMethodName == "decodeOpcode" }.methodNode

        val matches = AnalyzerSearching.searchMethodForFieldsInCodeOrder(decodeMn, PUTFIELD)

        addField("unknown0", matches[0])
        addField("unknown1", matches[1])
        addField("unknown2", matches[2])
        addField("height", matches[3])
        addField("overlaySpriteId", matches[4])
        addField("underlaySpriteId", matches[5])
        addField("unknown3", matches[6])
        addField("width", matches[7])
        addField("widthPadding", matches[8])

    }

    fun getSprites()
    {

        val matches = AnalyzerSearching.searchClassForMethod(classNode, String.format(")L%s;", getClassAnalyserName(Sprite::class)))

        AnalyzerSearching.searchMethodForFields(matches[0], arrayListOf())

    }

    override fun getFields() {

        findDecode()
        findDecodeOpcode()
        findFetchHealthBarDefinition()
        hookFields()

    }


}