package io.autorune.updater.analyzer.classes.implementations.user

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.collection.Link
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(Link::class, Username::class)
class UserLoginUpdate : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyserName(Link::class))
                continue

            val match = classNode.fields.any { it.desc == String.format("L%s;", getClassAnalyserName(Username::class)) }

            if (!match)
                continue

            return classNode

        }
        return null
    }

    override fun getFields() {

    }

}