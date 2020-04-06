package io.autorune.updater.analyzer.classes.implementations.sprite

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.io.RSByteBuffer
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(RSByteBuffer::class)
class SpriteArchiveNames : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            val match = classNode.fields.count { !isMemberStatic(it.access) } == 11
                    && classNode.fields.count { it.desc == descInt && !isMemberStatic(it.access) } == 11

            if (!match)
                continue

            return classNode

        }

        return null

    }

    override fun getFields() {

    }

}