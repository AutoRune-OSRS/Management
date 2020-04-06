package io.autorune.updater.analyzer.classes.util

import io.autorune.updater.analyzer.classes.ClassAnalyzer

object AnalyzerStaging {

    fun runStaging(classAnalyzer: ClassAnalyzer): Triple<Int, Int, Int> {
        var classCount = 0
        var fieldCount = 0
        var methodCount = 0
        val dependencies = AnalyzerUtils.getAnalyzerDependencies(classAnalyzer)
        if (dependencies != null) {
            for (dep in dependencies) {
                if (dep != null) {
                    val depAnalyzer = AnalyzerUtils.getClassAnalyser(dep)
                    if (depAnalyzer?.readyForDependencies != true) {
                        val counts: Triple<Int, Int, Int> = depAnalyzer?.let { runStaging(it) } ?: Triple(0, 0, 0)
                        classCount += counts.first
                        fieldCount += counts.second
                        methodCount += counts.third
                    }
                }
            }
        }
        classAnalyzer.runClassStage()
        classCount += 1
        classAnalyzer.runFieldStage()
        fieldCount += classAnalyzer.getFoundFields()
        methodCount += classAnalyzer.getFoundMethods()
        return Triple(classCount, fieldCount, methodCount)
    }

}