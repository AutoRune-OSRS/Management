package io.autorune.updater.analyzer.classes.implementations.combat

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.RSEnum
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(RSEnum::class)
class AttackOption : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (!classNode.interfaces.contains(getClassAnalyser(RSEnum::class)?.classNode?.name))
                continue

            val match = classNode.fields.count { it.desc == String.format("L%s;", classNode.name) } == 4
                    && classNode.fields.count { it.desc == descInt && !Modifier.isStatic(it.access) } == 1
                    && classNode.fields.none { it.desc == descByte && !Modifier.isStatic(it.access) }

            if (match)
                return classNode

        }

        return null
    }


    override fun getFields() {

    }


}