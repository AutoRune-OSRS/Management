package io.autorune.updater.analyzer.classes.implementations.graphics

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.collection.DoublyNode
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier


@CorrectFieldCount(7)
@CorrectMethodCount(0)
@DependsOn(DoublyNode::class)
class Rasterizer2D : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {
            if (classNode.superName != getClassAnalyser(DoublyNode::class)?.classNode?.name)
                continue
            if (classNode.methods.count { !Modifier.isStatic(it.access) } > 1)
                continue
            for (fieldNode in classNode.fields)
                if (fieldNode.desc == "[I")
                    return classNode
        }
        return null
    }

    private fun getDrawingAreaPixels() {
        for (fieldNode in classNode.fields) {
            if (fieldNode.desc == "[I") {
                addField("drawingAreaPixels", fieldNode)
                return
            }
        }
    }

    private fun getDrawingAreaDimensions() {
        val matches = AnalyzerSearching.searchClassForField(classNode, listOf(ALOAD, PUTSTATIC, ILOAD, PUTSTATIC, ILOAD, PUTSTATIC))
        val widthField = matches[1]
        val heightField = matches[2]
        addField("drawingAreaWidth", widthField)
        addField("drawingAreaHeight", heightField)
    }

    private fun getDrawingAreaBounds() {
        val matches = AnalyzerSearching.searchClassForField(classNode, listOf(ILOAD, PUTSTATIC, ILOAD, PUTSTATIC, ILOAD, PUTSTATIC, ILOAD, PUTSTATIC))
        val leftField = matches[0]
        val topField = matches[1]
        val bottomField = matches[2]
        val rightField = matches[3]
        addField("drawingAreaLeft", leftField)
        addField("drawingAreaTop", topField)
        addField("drawingAreaBottom", bottomField)
        addField("drawingAreaRight", rightField)
    }

    override fun getFields() {
        getDrawingAreaPixels()
        getDrawingAreaDimensions()
        getDrawingAreaBounds()
    }


}