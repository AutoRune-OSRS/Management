package io.autorune.updater.analyzer.classes.implementations.audio.music

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.collection.Node
import io.autorune.updater.analyzer.classes.implementations.io.RSByteBuffer
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(RSByteBuffer::class, Node::class)
class MusicTrack : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyser(Node::class)?.classNode?.name)
                continue

            val match =
                    classNode.methods.count { it.desc == String.format("(L%s;)V", getClassAnalyser(RSByteBuffer::class)?.classNode?.name) && !Modifier.isStatic(it.access) } == 1
                            && classNode.fields.count { !Modifier.isStatic(it.access) } == 2

            if (match)
                return classNode

        }

        return null
    }


    override fun getFields() {

    }


}