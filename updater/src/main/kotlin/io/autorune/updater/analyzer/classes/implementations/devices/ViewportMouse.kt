package io.autorune.updater.analyzer.classes.implementations.devices

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(0)
class ViewportMouse : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.interfaces.isNotEmpty()
                    || classNode.superName != "java/lang/Object"
                    || Modifier.isInterface(classNode.access)
                    || Modifier.isAbstract(classNode.access))
                continue

            //TODO: This is garbage

            val match = classNode.fields.none { !isMemberStatic(it.access) }
                    && classNode.methods.none { !isMemberStatic(it.access) }
                    && classNode.methods.any { it.name == "<clinit>" }
                    && AnalyzerSearching.searchMethodForFields(classNode.methods.first { it.name == "<clinit>" },
                    listOf(ICONST_0, PUTSTATIC, ICONST_0, PUTSTATIC, ICONST_0, PUTSTATIC, ICONST_0, PUTSTATIC, ICONST_0, PUTSTATIC, SIPUSH, NEWARRAY, PUTSTATIC)).first

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {
    }


}