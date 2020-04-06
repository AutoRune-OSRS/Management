package io.autorune.updater.analyzer.util

import io.autorune.updater.analyzer.classes.repo.ClassRepository
import org.objectweb.asm.tree.*

object AsmUtils {

    fun insnToField(insn: FieldInsnNode): FieldNode? {
        val classNode = ClassRepository.classPool.first { it.name == insn.owner }
        return classNode.fields.first { it.name == insn.name && it.desc == insn.desc}
    }

    fun insnToMethod(insn: MethodInsnNode): MethodNode? {
        val classNode = ClassRepository.classPool.first { it.name == insn.owner }
        return classNode.methods.first { it.name == insn.name && it.desc == insn.desc}
    }

}