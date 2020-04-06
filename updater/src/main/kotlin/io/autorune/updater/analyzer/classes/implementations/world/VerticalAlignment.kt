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
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.TypeInsnNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(RSEnum::class, FillMode::class, RSByteBuffer::class, WorldMapElementDefinition::class)
class VerticalAlignment : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        val methodToSearch = getClassAnalyser(WorldMapElementDefinition::class)?.classNode?.let { cn ->
            cn.methods.first {
                it.desc == String.format("(L%s;I)V", getClassAnalyserName(RSByteBuffer::class)) && !isMemberStatic(it.access)
            }
        }

        val matches = methodToSearch?.let { AnalyzerSearching.searchMethodForFields(it, listOf(ANEWARRAY, DUP, ICONST_0, GETSTATIC)) }?.second

        lateinit var matchesCast: MutableList<TypeInsnNode>

        if (matches?.size ?: 0 < 2) {

            matchesCast = methodToSearch?.let { AnalyzerSearching.searchMethodForCast(it, listOf(INVOKESTATIC, ALOAD, INVOKEVIRTUAL, INVOKESTATIC, CHECKCAST)) }?.second!!

        }

        for (classNode in classPool) {

            if (!classNode.interfaces.contains(getClassAnalyserName(RSEnum::class)) || classNode.name == getClassAnalyserName(FillMode::class))
                continue

            if ((matches?.size ?: 0 > 1 && classNode.name == matches?.get(1)?.owner) || matchesCast[0].desc.contains(classNode.name))
                return classNode

        }
        return null
    }

    override fun getFields() {

    }

}