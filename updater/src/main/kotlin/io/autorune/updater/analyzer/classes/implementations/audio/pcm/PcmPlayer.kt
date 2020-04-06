package io.autorune.updater.analyzer.classes.implementations.audio.pcm

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(0)
class PcmPlayer : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            val match = classNode.methods.count { Modifier.isSynchronized(it.access) } == 4
                    && classNode.fields.count { it.desc == descLong && !isMemberStatic(it.access) } == 3

            if (!match)
                continue

            return classNode

        }
        return null
    }

    override fun getFields() {

    }

}