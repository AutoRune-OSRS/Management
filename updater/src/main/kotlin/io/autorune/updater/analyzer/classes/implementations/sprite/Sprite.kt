package io.autorune.updater.analyzer.classes.implementations.sprite

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.graphics.Rasterizer2D
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode
import java.lang.Exception


@CorrectFieldCount(7)
@CorrectMethodCount(0)
@DependsOn(Rasterizer2D::class)
class Sprite : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {
            if (classNode.superName != getClassAnalyser(Rasterizer2D::class)?.classNode?.name)
                continue
            val intArrayNodeCount = classNode.fields.count { it.desc == "[I" }
            val byteArrayNodeCount = classNode.fields.count { it.desc == "[B" }
            if (intArrayNodeCount != 1 || byteArrayNodeCount != 0)
                continue
            return classNode
        }
        return null
    }

    private fun getSpritePixels() {
        for (field in classNode.fields) {
            if (field.desc == "[I") {
                addField("spritePixels", field)
                return
            }
        }
        throw Exception()
    }

    private fun getDimensionsAndOffsets() {
        val matches = AnalyzerSearching.searchClassForField(classNode, listOf(PUTFIELD, PUTFIELD, ALOAD, ALOAD, ILOAD, DUP_X1, PUTFIELD, PUTFIELD, ALOAD, ICONST_0, PUTFIELD, ALOAD, ICONST_0, PUTFIELD))
        addField("spriteMaxWidth", matches[0])
        addField("spriteWidth", matches[1])
        addField("spriteMaxHeight", matches[2])
        addField("spriteHeight", matches[3])
        addField("spriteOffsetY", matches[4])
        addField("spriteOffsetX", matches[5])
    }

    override fun getFields() {
        getSpritePixels()
        getDimensionsAndOffsets()
    }

}