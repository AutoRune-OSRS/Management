package io.autorune.updater.analyzer.classes.implementations.audio.music

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.audio.midi.MidiPcmStream
import io.autorune.updater.analyzer.classes.implementations.audio.pcm.PcmStream
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(PcmStream::class, MidiPcmStream::class)
class MusicPatchPcmStream : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyserName(PcmStream::class))
                continue

            val match = classNode.fields.any { it.desc == String.format("L%s;", getClassAnalyserName(MidiPcmStream::class)) }

            if (match)
                return classNode

        }
        return null
    }

    override fun getFields() {

    }

}