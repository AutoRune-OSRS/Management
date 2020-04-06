package io.autorune.updater.analyzer.classes.implementations.graphics

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.texture.TextureLoader
import org.objectweb.asm.tree.ClassNode


@CorrectFieldCount(0)
@CorrectMethodCount(0)
@DependsOn(Rasterizer2D::class, TextureLoader::class)
class Rasterizer3D : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyserName(Rasterizer2D::class))
                continue

            val match = classNode.fields.any { it.desc == String.format("L%s;", getClassAnalyserName(TextureLoader::class)) }

            if (match)
                return classNode

        }
        return null
    }

    override fun getFields() {

    }

}