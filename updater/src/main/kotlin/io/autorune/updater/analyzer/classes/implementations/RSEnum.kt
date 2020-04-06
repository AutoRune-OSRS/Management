package io.autorune.updater.analyzer.classes.implementations

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(0)
class RSEnum : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        val possible =  mutableListOf<ClassNode>()

        for (classNode in classPool) {

            if (!Modifier.isInterface(classNode.access))
                continue

            val match = classNode.fields.none { !Modifier.isStatic(it.access) }
                    && classNode.methods.count { Modifier.isAbstract(it.access) && it.desc == "()I" } == 1
                    && classNode.methods.none { !Modifier.isAbstract(it.access) && !Modifier.isStatic(it.access) }

            if (match)
                possible.add(classNode)

        }

        for (classNode in classPool) {

            for (clss in possible)
                if (classNode.fields.any { it.desc == String.format("L%s;", clss.name)})
                    possible.remove(clss)

        }

        return possible[0]

    }


    override fun getFields() {

    }


}