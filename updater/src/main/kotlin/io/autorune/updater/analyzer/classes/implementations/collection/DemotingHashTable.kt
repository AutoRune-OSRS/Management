package io.autorune.updater.analyzer.classes.implementations.collection

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(IterableDoublyQueue::class, IterableHashTable::class, NodeCache::class)
class DemotingHashTable : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            val match = classNode.fields.any { it.desc == String.format("L%s;", getClassAnalyserName(IterableDoublyQueue::class)) }
                    && classNode.fields.any { it.desc == String.format("L%s;", getClassAnalyserName(IterableHashTable::class)) }
                    && classNode.fields.count { it.desc == descInt && !isMemberStatic(it.access) } == 2
                    && classNode.name != getClassAnalyserName(NodeCache::class)

            if (!match)
                continue

            return classNode

        }
        return null
    }

    override fun getFields() {

    }

}