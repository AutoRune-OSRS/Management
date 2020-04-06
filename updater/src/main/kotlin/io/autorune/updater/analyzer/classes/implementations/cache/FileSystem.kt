package io.autorune.updater.analyzer.classes.implementations.cache

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import org.objectweb.asm.tree.ClassNode


@CorrectFieldCount(0)
@CorrectMethodCount(0)
class FileSystem : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            val match =  classNode.fields.count { it.desc == String.format("L%s;", "java/util/Hashtable") && isMemberStatic(it.access) } == 1

            if (!match)
                continue

            return classNode

        }
        return null
    }

    override fun getFields() {

    }

}