package io.autorune.updater.analyzer.classes.implementations.audio.pcm

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(PcmStream::class)
class RawPcmStream : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyser(PcmStream::class)?.classNode?.name)
                continue

            val match = classNode.fields.count { it.desc == descInt && !isMemberStatic(it.access) } == 14

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {

    }


}