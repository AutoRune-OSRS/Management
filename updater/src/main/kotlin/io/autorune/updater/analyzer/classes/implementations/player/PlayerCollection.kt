package io.autorune.updater.analyzer.classes.implementations.player

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.entity.Player
import io.autorune.updater.analyzer.classes.implementations.io.PacketBuffer
import io.autorune.updater.analyzer.classes.implementations.io.RSByteBuffer
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(RSByteBuffer::class, PacketBuffer::class, Player::class)
class PlayerCollection : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {


            val match = classNode.fields.any { it.desc == String.format("L%s;", getClassAnalyserName(RSByteBuffer::class))  }
                    && classNode.fields.any { it.desc == String.format("[L%s;", getClassAnalyserName(RSByteBuffer::class))  }

            if (match)
                return classNode

        }
        return null
    }

    override fun getFields()
    {

    }

}