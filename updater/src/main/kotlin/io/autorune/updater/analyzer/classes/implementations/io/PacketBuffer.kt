package io.autorune.updater.analyzer.classes.implementations.io

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(RSByteBuffer::class, IsaacCipher::class)
class PacketBuffer : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyserName(RSByteBuffer::class))
                continue

            val match = classNode.fields.any { it.desc == String.format("L%s;", getClassAnalyserName(IsaacCipher::class)) }

            if (match)
                return classNode

        }
        return null
    }

    override fun getFields() {

    }

}