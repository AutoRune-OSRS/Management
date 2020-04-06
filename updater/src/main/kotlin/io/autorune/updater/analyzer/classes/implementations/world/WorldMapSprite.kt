package io.autorune.updater.analyzer.classes.implementations.world

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(0)
class WorldMapSprite : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.interfaces.isNotEmpty() || classNode.superName != "java/lang/Object" || !Modifier.isFinal(classNode.access))
                continue

            val match = classNode.fields.count { it.desc == descIntArr && !isMemberStatic(it.access) } == 1
                    && classNode.fields.count { !isMemberStatic(it.access) } == 1

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {
    }


}