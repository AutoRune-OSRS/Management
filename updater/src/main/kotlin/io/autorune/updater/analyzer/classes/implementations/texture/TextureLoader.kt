package io.autorune.updater.analyzer.classes.implementations.texture

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(0)
class TextureLoader : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (!Modifier.isInterface(classNode.access))
                continue

            val match = classNode.methods.count { it.desc == "(I)Z" && Modifier.isAbstract(it.access) } == 2

            if (match)
                return classNode

        }
        return null
    }

    override fun getFields() {

    }

}