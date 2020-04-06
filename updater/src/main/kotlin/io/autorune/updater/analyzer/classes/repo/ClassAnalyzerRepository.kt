package io.autorune.updater.analyzer.classes.repo


import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.implementations.ClassDummy
import java.io.File
import java.util.*

object ClassAnalyzerRepository {

    lateinit var classAnalyzers: List<ClassAnalyzer>

    fun initialize() {
        classAnalyzers = getAllAnalyzers()
    }

    private fun getAllAnalyzers(): List<ClassAnalyzer> {

        val packageName = ClassDummy::class.java.packageName

        val path = packageName.replace(".", File.separator)

        val classes = ArrayList<ClassAnalyzer>()

        val classPathEntries = System.getProperty("java.class.path").split(System.getProperty("path.separator").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        for (classpathEntry in classPathEntries) {
            if (!classpathEntry.contains("updater"))
                continue
            try {
                val base = File(classpathEntry + File.separatorChar + path)
                classes.addAll(searchDirectory(packageName, base))
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        }

        return classes
    }

    @Throws(Exception::class)
    private fun searchDirectory(packageName: String, base: File): List<ClassAnalyzer> {
        val classes = ArrayList<ClassAnalyzer>()
        if (base.path.contains(".jar"))
            return classes
        var name: String
        for (file in base.listFiles()) {
            if (file.isDirectory)
                classes.addAll(searchDirectory(packageName + "." + file.name, file))
            else {
                name = file.name
                if (name.endsWith(".class")) {
                    name = name.substring(0, name.length - 6)
                    val clazz = Class.forName("$packageName.$name").getDeclaredConstructor().newInstance()
                    if (clazz is ClassAnalyzer)
                    classes.add(clazz)
                }
            }
        }
        return classes
    }

}