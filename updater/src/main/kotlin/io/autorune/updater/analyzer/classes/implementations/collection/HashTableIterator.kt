package io.autorune.updater.analyzer.classes.implementations.collection

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(IterableHashTable::class)
class HashTableIterator : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (!classNode.interfaces.contains("java/util/Iterator"))
                continue

            val match = classNode.fields.count { it.desc == String.format("L%s;", getClassAnalyserName(IterableHashTable::class)) } == 1

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {
    }


}