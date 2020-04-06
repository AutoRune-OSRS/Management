package io.autorune.updater.analyzer.classes.implementations.system

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(MachineInfoProvider::class)
class DesktopMachineInfoProvider : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            if (!classNode.interfaces.contains(getClassAnalyserName(MachineInfoProvider::class)))
                continue

            return classNode

        }
        return null
    }

    override fun getFields() {

    }

}