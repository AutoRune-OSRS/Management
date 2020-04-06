package io.autorune.updater.analyzer.classes.implementations.region

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(1)
@CorrectMethodCount(0)
class TileComposition : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.interfaces.isNotEmpty() || classNode.superName != "java/lang/Object")
                continue

            val match = classNode.fields.count { it.desc == descInt && !Modifier.isStatic(it.access) } == 6
                    && classNode.fields.count { it.desc == descBool && !Modifier.isStatic(it.access) } == 1
                    && classNode.fields.count { !Modifier.isStatic(it.access) } == 7

            if (match)
                return classNode

        }

        return null
    }

    private fun findTileHeights()
    {

        val methodN = AnalyzerSearching.searchAllClassesForMethod(listOf(DUP, ICONST_4, BIPUSH, BIPUSH), "()V").filter { isMemberStatic(it.first.access) }

        val matches = AnalyzerSearching.searchMethodForFields(methodN.first().first, listOf(GETSTATIC)).second

        addFieldToClient("tileHeights", matches[1])

    }

    override fun getFields() {
        findTileHeights()
    }


}