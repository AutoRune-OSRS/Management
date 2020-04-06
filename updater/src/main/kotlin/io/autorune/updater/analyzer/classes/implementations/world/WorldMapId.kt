package io.autorune.updater.analyzer.classes.implementations.world

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.io.RSByteBuffer
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(WorldMapChunkData::class, RSByteBuffer::class)
class WorldMapId : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        val matches = getClassAnalyser(WorldMapChunkData::class)?.classNode?.let {
            AnalyzerSearching.searchClassForField(it,
                listOf(INVOKEVIRTUAL, ISTORE, GETSTATIC, GETFIELD),
                String.format("(L%s;)V", getClassAnalyserName(RSByteBuffer::class)))
        }

        for (classNode in classPool) {

            if (classNode.interfaces.isNotEmpty() || classNode.superName != "java/lang/Object")
                continue

            if (matches?.get(0)?.owner == classNode.name)
                return classNode

        }

        return null
    }

    override fun getFields() {
    }


}