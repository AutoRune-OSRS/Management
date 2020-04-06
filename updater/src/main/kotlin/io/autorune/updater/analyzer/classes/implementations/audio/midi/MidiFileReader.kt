package io.autorune.updater.analyzer.classes.implementations.audio.midi

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.io.RSByteBuffer
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(RSByteBuffer::class)
class MidiFileReader : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            val match = classNode.fields.any { it.desc == String.format("L%s;", getClassAnalyserName(RSByteBuffer::class)) }
                    && classNode.fields.count { it.desc == descLong && !isMemberStatic(it.access) } == 1
                    && classNode.fields.count { it.desc == descIntArr && !isMemberStatic(it.access) } == 4

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {
    }


}