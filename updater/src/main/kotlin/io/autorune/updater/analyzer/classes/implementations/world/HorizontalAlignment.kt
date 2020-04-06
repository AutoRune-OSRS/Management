package io.autorune.updater.analyzer.classes.implementations.world

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.RSEnum
import io.autorune.updater.analyzer.classes.implementations.definitions.WorldMapElementDefinition
import io.autorune.updater.analyzer.classes.implementations.io.RSByteBuffer
import io.autorune.updater.analyzer.classes.implementations.sprite.FillMode
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.AbstractInsnNode
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.FieldInsnNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(RSEnum::class, FillMode::class, RSByteBuffer::class, WorldMapElementDefinition::class)
class HorizontalAlignment : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        val worldMapElementDecode = getClassAnalyser(WorldMapElementDefinition::class)?.methods?.first { it.newMethodName == "decodeOpcode" }?.methodNode

        val horizontalAlignmentBlockStart = worldMapElementDecode?.let { AnalyzerSearching.searchMethodForInts(it, listOf(BIPUSH)) }?.second?.first { it.operand == 29 }

        var methodInsn = horizontalAlignmentBlockStart as AbstractInsnNode

        while (methodInsn !is FieldInsnNode) {
            methodInsn = methodInsn.next
        }

        val horizontalAlignmentField = methodInsn

        val horizontalAlignmentClassName = horizontalAlignmentField.desc.replace("L", "").replace(";", "")

        return classPool.first { it.name == horizontalAlignmentClassName }

    }

    override fun getFields() {

    }

}