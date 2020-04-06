package io.autorune.updater.analyzer.classes.implementations.sprite

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.graphics.Rasterizer2D
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(Rasterizer2D::class)
class IndexedSprite : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {
            if (classNode.superName != getClassAnalyser(Rasterizer2D::class)?.classNode?.name)
                continue
            val intArrayNodeCount = classNode.fields.count { it.desc == descIntArr }
            val byteArrayNodeCount = classNode.fields.count { it.desc == descByteArr }
            if (intArrayNodeCount != 1 || byteArrayNodeCount != 1)
                continue
            return classNode
        }
        return null

    }

    override fun getFields() {

    }

}