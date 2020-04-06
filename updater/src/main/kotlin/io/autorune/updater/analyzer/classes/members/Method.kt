package io.autorune.updater.analyzer.classes.members

import io.autorune.updater.analyzer.classes.util.AnalyzerUtils
import org.objectweb.asm.tree.MethodNode

class Method(var methodNode: MethodNode, var owner: String, var newMethodName: String, var isClientMember: Boolean) {

    fun print() {
        val out = String.format("\t[> Method '%s' identified as '", newMethodName) + String.format(owner + ".%s' -] (%s)", methodNode.name, AnalyzerUtils.clean(methodNode.desc))
        println(out)
    }

}