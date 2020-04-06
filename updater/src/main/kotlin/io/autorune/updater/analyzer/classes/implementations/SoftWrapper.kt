package io.autorune.updater.analyzer.classes.implementations

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(1)
@CorrectMethodCount(2)
@DependsOn(Wrapper::class, DirectWrapper::class)
class SoftWrapper : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyserName(Wrapper::class) || classNode.name == getClassAnalyserName(DirectWrapper::class))
                continue

            return classNode

        }
        return null
    }

    private fun localInstanceField()
    {

        val decodeMn = methods.first { it.newMethodName == "instance" }.methodNode

        val matches = AnalyzerSearching.searchMethodForFields(decodeMn, listOf(GETFIELD)).second

        addField("reference", matches[0])

    }

    private fun findObject()
    {

        val matches = AnalyzerSearching.searchClassForMethod(classNode, "()Ljava/lang/Object;")

        addMethod("instance", matches[0])

    }

    private fun findIsSoft()
    {

        val matches = AnalyzerSearching.searchClassForMethod(classNode, "()Z")

        addMethod("isSoft", matches[0])

    }

    override fun getFields() {

        findObject()
        findIsSoft()
        localInstanceField()

    }

}