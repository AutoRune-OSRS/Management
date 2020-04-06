package io.autorune.updater.analyzer.classes.implementations.devices

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(1)
@CorrectMethodCount(0)
class KeyboardListener : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        return classPool.first { it.methods.any { mn -> !isMemberStatic(mn.access) && mn.name.contains("key") } }
    }

    private fun getKeyboardListener() {
        for (cN in classPool) {
            val field = cN.fields.find { it.desc == String.format("L%s;", classNode.name) && Modifier.isStatic(it.access) }
            if (field != null) {
                addFieldToClient("keyboardListener", cN.name, field)
                break
            }
        }
    }

    override fun getFields() {
        getKeyboardListener()
    }

}