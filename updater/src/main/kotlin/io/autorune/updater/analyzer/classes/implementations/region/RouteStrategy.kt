package io.autorune.updater.analyzer.classes.implementations.region

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.audio.pcm.PcmStream
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(PcmStream::class)
class RouteStrategy : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            if (!Modifier.isAbstract(classNode.access))
                continue

            val match = classNode.fields.count { it.desc == descInt && !isMemberStatic(it.access) } == 4
                    && classNode.fields.count { !isMemberStatic(it.access) } == 4

            if (match)
                return classNode

        }

        return null
    }

    override fun getFields() {

    }


}