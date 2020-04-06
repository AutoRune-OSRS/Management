package io.autorune.updater.analyzer.classes.util


import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.members.Field
import io.autorune.updater.analyzer.classes.repo.ClassAnalyzerRepository
import io.autorune.updater.analyzer.util.AsmUtils
import org.objectweb.asm.tree.FieldInsnNode
import java.util.*

object AnalyzerUtils {


    fun getTotalCorrectFields(): Int {
        var count = 0
        for (analyzer in ClassAnalyzerRepository.classAnalyzers) {
            count += getCorrectFields(analyzer)
        }
        return count
    }

    fun getCorrectFields(classAnalyzer: ClassAnalyzer): Int {
        val annotations = ArrayList(listOf(*classAnalyzer.javaClass.annotations))
        for (annotation in annotations) {
            if (annotation is CorrectFieldCount)
                return annotation.value
        }
        return 0
    }

    fun getTotalCorrectMethods(): Int {
        var count = 0
        for (analyzer in ClassAnalyzerRepository.classAnalyzers) {
            count += getCorrectMethods(analyzer)
        }
        return count
    }

    fun getCorrectMethods(classAnalyzer: ClassAnalyzer): Int {
        val annotations = ArrayList(listOf(*classAnalyzer.javaClass.annotations))
        for (annotation in annotations) {
            if (annotation is CorrectMethodCount)
                return annotation.value
        }
        return 0
    }


    fun getAnalyzerDependencies(classAnalyzer: ClassAnalyzer): List<String?>? {
        val annotations = ArrayList(listOf(*classAnalyzer.javaClass.annotations))
        for (annotation in annotations) {
            if (annotation is DependsOn)
                return annotation.value.map { it.simpleName }
        }
        return null
    }

    fun getClassAnalyser(name: String): ClassAnalyzer? {
        for (analyzer in ClassAnalyzerRepository.classAnalyzers) {
            if (analyzer.analyzerName == name) {
                return analyzer
            }
        }
        return null
    }

    fun filterFields(fields: MutableList<FieldInsnNode>, foundFields: ArrayList<Field>): List<FieldInsnNode> {
        val foundFieldNodes = foundFields.map { it.fieldNode }
        return fields.distinctBy { it.owner+"."+it.name+"."+it.desc }.filter { !foundFieldNodes.contains(AsmUtils.insnToField(it)) }
    }

    fun clean(s: String): String {
        return s.replace("java/awt/", "").replace("java/lang/", "")
    }

}