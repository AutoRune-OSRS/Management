package io.autorune.updater.analyzer.classes.implementations.audio.pcm

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.collection.Deque
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(PcmStream::class, Deque::class)
class PcmStreamMixer : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyserName(PcmStream::class))
                continue

            val match = classNode.fields.count { it.desc == String.format("L%s;", getClassAnalyserName(Deque::class)) } == 2

            if (match)
                return classNode

        }
        return null
    }

    override fun getFields() {

    }

}