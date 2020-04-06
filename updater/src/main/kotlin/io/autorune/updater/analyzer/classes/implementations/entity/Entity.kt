package io.autorune.updater.analyzer.classes.implementations.entity

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.collection.DoublyNode
import io.autorune.updater.analyzer.classes.implementations.model.Model
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(1)
@CorrectMethodCount(2)
@DependsOn(DoublyNode::class, Model::class)
class Entity : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            if (!Modifier.isAbstract(classNode.access))
                continue

            if (classNode.superName != getClassAnalyser(DoublyNode::class)?.classNode?.name)
                continue

            val match = classNode.methods.none { Modifier.isAbstract(it.access) }

            if (match)
                return classNode

        }

        return null
    }

    fun findModel()
    {

        val match = classNode.methods.first { it.desc == String.format("()L%s;", getClassAnalyserName(Model::class)) && !isMemberStatic(it.access) }

        addMethod("fetchModel", match)

    }

    fun findEntityHeight()
    {

        val match = classNode.methods.first { it.desc == "(IIIIIIIIJ)V" && !isMemberStatic(it.access) }

        addMethod("drawEntityModel", match)

        val drawEntityMn = methods.first { it.newMethodName == "drawEntityModel" }.methodNode

        val field = AnalyzerSearching.searchMethodForFields(drawEntityMn, listOf(GETFIELD))

        addField("height", field.second.first())

    }

    override fun getFields() {
        findModel()
        findEntityHeight()
    }


}