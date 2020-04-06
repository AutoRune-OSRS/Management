package io.autorune.updater.analyzer.classes.implementations.user

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(BasicUser::class)
class UserList : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (!Modifier.isAbstract(classNode.access))
                continue

            val match = classNode.fields.any { it.desc == String.format("[L%s;", getClassAnalyserName(BasicUser::class)) }

            if (!match)
                continue

            return classNode

        }
        return null
    }

    override fun getFields() {

    }

}