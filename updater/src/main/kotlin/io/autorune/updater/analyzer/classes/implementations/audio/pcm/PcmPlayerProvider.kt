package io.autorune.updater.analyzer.classes.implementations.audio.pcm

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(PcmPlayer::class)
class PcmPlayerProvider : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (!Modifier.isInterface(classNode.access))
                continue

            val match = classNode.methods.any { it.desc == String.format("()L%s;", getClassAnalyserName(PcmPlayer::class)) }

            if (match)
                return classNode

        }
        return null
    }

    override fun getFields() {

    }

}