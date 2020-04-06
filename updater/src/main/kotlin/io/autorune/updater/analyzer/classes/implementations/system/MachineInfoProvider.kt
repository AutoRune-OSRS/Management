package io.autorune.updater.analyzer.classes.implementations.system

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(MachineInfo::class)
class MachineInfoProvider : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            if (!Modifier.isInterface(classNode.access))
                continue

            val match = classNode.methods.any { it.desc == String.format("()L%s;", getClassAnalyserName(MachineInfo::class)) }

            if (!match)
                continue

            return classNode

        }
        return null
    }

    override fun getFields() {

    }

}