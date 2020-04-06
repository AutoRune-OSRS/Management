package io.autorune.updater.analyzer.classes.implementations.audio

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.cache.AbstractArchive
import io.autorune.updater.analyzer.classes.implementations.collection.HashTable
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(HashTable::class, AbstractArchive::class)
class SoundLoader : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            val match = classNode.fields.count { it.desc == String.format("L%s;", getClassAnalyserName(AbstractArchive::class)) } == 2
                    && classNode.fields.count { it.desc == String.format("L%s;", getClassAnalyserName(HashTable::class)) } == 2

            if (match)
                return classNode

        }

        return null

    }

    override fun getFields() {

    }

}