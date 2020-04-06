package io.autorune.injector.transform


import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.*
import java.lang.reflect.Modifier

object DeviceTransform {

    fun injectTransform(cn: ClassNode, prefix: String)
    {

        val methodsToInject = cn.methods.filter { (it.name.startsWith(prefix) || it.name.startsWith("focus")) && !Modifier.isStatic(it.access) }

        methodsToInject.forEach {

            methodNode ->

            val returnLabel = getReturnLabel(methodNode) ?: return@forEach

            val insnStart = methodNode.instructions.first

            val injectedList = InsnList()

            injectedList.add(VarInsnNode(Opcodes.ALOAD,  0))
            injectedList.add(FieldInsnNode(Opcodes.GETFIELD, cn.name, "isInputBlocked", "Z"))
            injectedList.add(JumpInsnNode(Opcodes.IFNE, returnLabel))

            methodNode.instructions.insertBefore(insnStart, injectedList)

        }

        cn.visitEnd()
    }

    private fun getLabelCount(methodNode: MethodNode): Int {

        var count = 0

        for (insn in methodNode.instructions) {
            if (insn is LabelNode)
                count++
        }

        return count

    }

    private fun getReturnLabel(methodNode: MethodNode): LabelNode? {

        val labelCount = getLabelCount(methodNode)

        if (labelCount == 0) {
            if (methodNode.instructions.size() == 1)
                return null
            return createReturnLabel(methodNode)
        }

        var currentLabel: LabelNode? = null

        for (insn in methodNode.instructions) {

            if (insn is LabelNode)
                currentLabel = insn

            if (insn.opcode == Opcodes.RETURN)
                break

        }

        return currentLabel

    }

    private fun createReturnLabel(methodNode: MethodNode): LabelNode? {

        val returnInsn = methodNode.instructions.toArray().toList().first { it.opcode == Opcodes.RETURN }

        val newLabel = LabelNode()

        methodNode.instructions.insertBefore(returnInsn, newLabel)

        return newLabel

    }

}