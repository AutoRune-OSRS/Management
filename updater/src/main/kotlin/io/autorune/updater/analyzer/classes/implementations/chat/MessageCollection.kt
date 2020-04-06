package io.autorune.updater.analyzer.classes.implementations.chat

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.collection.IterableHashTable
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(IterableHashTable::class)
class MessageCollection : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            val match = classNode.fields.any { it.desc == "Ljava/util/Map;" }
                    && classNode.fields.any { it.desc == String.format("L%s;", getClassAnalyserName(IterableHashTable::class)) }

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {
    }


}