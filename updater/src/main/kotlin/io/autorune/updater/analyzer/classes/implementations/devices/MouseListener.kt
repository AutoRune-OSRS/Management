package io.autorune.updater.analyzer.classes.implementations.devices

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(3)
@CorrectMethodCount(0)
class MouseListener : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        return classPool.first { it.methods.any { mn -> !isMemberStatic(mn.access) && mn.name.contains("mousePressed") } }
    }

    private fun getMouseListener() {
        for (cN in classPool) {
            val field = cN.fields.find { it.desc == String.format("L%s;", classNode.name) && Modifier.isStatic(it.access) }
            if (field != null) {
                addFieldToClient("mouseListener", cN.name, field)
                break
            }
        }
    }

    private fun getCoordinates() {

        val mN = classNode.methods.first { it.name == "<clinit>" }

        //TODO: This is trash

        val matches = AnalyzerSearching.searchMethodForFields(mN, listOf(LDC, PUTSTATIC, ICONST_0, PUTSTATIC, ICONST_0, PUTSTATIC, ICONST_0, PUTSTATIC))
        addField("mouseX", matches.second[2])
        addField("mouseY", matches.second[3])

    }

    override fun getFields() {
        getMouseListener()
        getCoordinates()
    }

}