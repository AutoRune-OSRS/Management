package io.autorune.updater.analyzer.classes.implementations.model

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(7)
@CorrectMethodCount(1)
class Model : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

//            if (classNode.superName != getClassAnalyserName(Entity::class))
//                continue

            val match = classNode.methods.any { it.desc == String.format("([L%s;I)V", classNode.name) && !isMemberStatic(it.access) }
                        && classNode.fields.count { it.desc == descShortArr && !isMemberStatic(it.access) } == 1

            if (match)
                return classNode

        }

        return null
    }

    fun findContourGround()
    {

        val methodN = classNode.methods.first { it.desc == String.format("([[IIIIZI)L%s;", getClassAnalyserName(this::class))}

        addMethod("contourGround", methodN)

    }

    fun findModelVerticeFields()
    {

        val methodN = methods.first { it.newMethodName == "contourGround" }.methodNode

        var matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(GETFIELD)).second

        addField("xzRadius", matches[0])
        addField("verticesCount", matches[4])
        addField("indicesCount", matches[5])
        addField("verticesX", matches[7])
        addField("verticesZ", matches[8])
        addField("indices1", matches[9])

        matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(PUTFIELD)).second

        addField("verticesY", matches[22])

    }

    override fun getFields() {
        findContourGround()
        findModelVerticeFields()
    }


}