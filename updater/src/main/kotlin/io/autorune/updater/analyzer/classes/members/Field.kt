package io.autorune.updater.analyzer.classes.members

import io.autorune.updater.analyzer.classes.util.AnalyzerUtils
import org.objectweb.asm.tree.FieldNode

class Field(var fieldNode: FieldNode, var owner: String, var fieldName: String, var isClientMember: Boolean, var multiplier: Any? = null) {

    fun print() {
        var out = String.format("\t[> '%s' identified as '", fieldName) + String.format(owner + ".%s' -] (%s)", fieldNode.name, AnalyzerUtils.clean(fieldNode.desc))
        if (fieldNode.desc == "I" && multiplier != null) {
            out += String.format("\t[ * %s ]", multiplier.toString())
        }
        println(out)
    }

}