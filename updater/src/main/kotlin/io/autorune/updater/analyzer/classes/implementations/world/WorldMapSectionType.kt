package io.autorune.updater.analyzer.classes.implementations.world

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.RSEnum
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(RSEnum::class)
class WorldMapSectionType : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (!classNode.interfaces.contains(getClassAnalyserName(RSEnum::class)))
                continue

            val match = classNode.fields.count { it.desc == String.format("L%s;", classNode.name) } == 4
                    && classNode.fields.count { it.desc == descInt && !isMemberStatic(it.access) } == 1
                    && classNode.fields.count { it.desc == descByte && !isMemberStatic(it.access) } == 1

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {
    }


}