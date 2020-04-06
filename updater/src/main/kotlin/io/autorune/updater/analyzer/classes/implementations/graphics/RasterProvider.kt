package io.autorune.updater.analyzer.classes.implementations.graphics

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(1)
@CorrectMethodCount(1)
@DependsOn(AbstractRasterProvider::class)
class RasterProvider : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyserName(AbstractRasterProvider::class))
                continue

            return classNode

        }
        return null
    }

    private fun getImageField() {

        val match = classNode.fields.first { it.desc == "Ljava/awt/Image;" }

        addField("image", match)

    }

    private fun getDrawFullMethod() {

        val match = classNode.methods.first { it.desc == "(Ljava/awt/Graphics;II)V" }

        addMethod("drawFull", match)

    }

    override fun getFields() {
        getImageField()
        getDrawFullMethod()
    }

}