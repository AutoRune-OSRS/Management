package io.autorune.updater.analyzer.classes.implementations.devices

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(MouseWheelListener::class)
class MouseWheel : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            if (getClassAnalyser(MouseWheelListener::class)?.classNode?.interfaces?.contains(classNode.name) == true)
                return classNode

        }

        return null
    }

    override fun getFields() {
    }


}