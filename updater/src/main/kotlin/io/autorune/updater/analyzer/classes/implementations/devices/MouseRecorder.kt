package io.autorune.updater.analyzer.classes.implementations.devices

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(1)
@CorrectMethodCount(0)
class MouseRecorder : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (!classNode.interfaces.contains("java/lang/Runnable"))
                continue

            val match = classNode.fields.count { it.desc == String.format("L%s;", "java/lang/Object") && !isMemberStatic(it.access) } == 1
                    && classNode.fields.count { it.desc == descLongArr && !isMemberStatic(it.access) } == 1

            if (match)
                return classNode

        }

        return null
    }

    private fun getMouseRecorder() {
        for (cN in classPool) {
            val field = cN.fields.find { it.desc == String.format("L%s;", classNode.name) && isMemberStatic(it.access) }
            if (field != null) {
                addFieldToClient("mouseRecorder", cN.name, field)
                break
            }
        }
    }

    override fun getFields() {

        getMouseRecorder()

    }


}