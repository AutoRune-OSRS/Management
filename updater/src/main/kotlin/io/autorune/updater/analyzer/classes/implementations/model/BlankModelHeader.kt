package io.autorune.updater.analyzer.classes.implementations.model

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(0)
class BlankModelHeader : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            if (classNode.superName != "java/lang/Object"
                    || classNode.interfaces.isNotEmpty()
                    || Modifier.isAbstract(classNode.access)
                    || Modifier.isInterface(classNode.access))
                continue

            val match = classNode.fields.count { !isMemberStatic(it.access) } == 0
                    && classNode.methods.count { !isMemberStatic(it.access) } == 1
                    && classNode.fields.none { it.desc == String.format("L%s;", classNode.name) }

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {
    }


}