package io.autorune.updater.analyzer.classes.implementations.devices

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(1)
@CorrectMethodCount(0)
class MouseWheelListener : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (node in classPool) {
            if (node.interfaces.contains("java/awt/event/MouseWheelListener"))
                return node
        }
        return null
    }

    private fun getMouseWheelRotation() {
        addField("mouseWheelRotation", classNode.fields.first { !isMemberStatic(it.access) && it.desc == "I" })
    }

    override fun getFields() {
        getMouseWheelRotation()
    }

}