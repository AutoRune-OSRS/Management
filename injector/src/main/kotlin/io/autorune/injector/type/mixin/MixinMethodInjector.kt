package io.autorune.injector.type.mixin

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.autorune.injector.Injector
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.*
import java.lang.reflect.Modifier

class MixinMethodInjector(private val methodNode: MethodNode,
                          private val classJson: JsonObject,
                          private val classNode: ClassNode,
                          private val classHooks: JsonArray,
                          private val classPool: List<ClassNode>) : Injector()
{

    override fun runInjection() {

        val refName = methodNode.name.replace("Mixin", "")

        lateinit var methodJson: JsonObject
        lateinit var owningClassNode: ClassNode
        var isInsertionStatic = false

        val isInsertionMethod = methodNode.visibleAnnotations != null && methodNode.visibleAnnotations.any { it.desc.contains("Insertion") }

        if (isInsertionMethod) {

            methodJson = classJson.get("method_hooks").asJsonArray.first { it.asJsonObject.get("ref_name").asString == refName }.asJsonObject

            val obfName = methodJson.get("obf_name").asString
            val owner = methodJson.get("owner").asString
            val desc = methodJson.get("descriptor").asString
            val access = methodJson.get("access").asInt

            owningClassNode = classPool.first { it.name == owner }

            isInsertionStatic = isInsertionMethod && Modifier.isStatic(owningClassNode.methods.first { it.name == obfName && it.desc == desc && it.access == access }.access)

        }

        val insnList = InsnList()

        for (insn in methodNode.instructions) {

            if (insn is VarInsnNode && insn.opcode == Opcodes.ALOAD && insn.`var` == 0 && isInsertionStatic)
                    continue

            if (isInsertionStatic && insn is VarInsnNode)
                insn.`var`--

            if (isInsertionStatic && insn is IincInsnNode)
                insn.`var`--

            insnList.add(fixInsn(insn, isInsertionStatic))

        }

        val injectedMethodNode: MethodNode

        if (isInsertionMethod) {

            val obfName = methodJson.get("obf_name").asString
            val desc = methodJson.get("descriptor").asString
            val access = methodJson.get("access").asInt

            injectedMethodNode = owningClassNode.methods.first { it.name == obfName && it.desc == desc && it.access == access }

            val existingNewStartLabel = LabelNode()

            injectedMethodNode.instructions.insertBefore(injectedMethodNode.instructions.first, existingNewStartLabel)

            val newReturnInsn = insnList.toArray().toList().first { isReturn(it.opcode) }

            insnList.insertBefore(newReturnInsn, JumpInsnNode(Opcodes.GOTO, existingNewStartLabel))
            insnList.remove(newReturnInsn)

            injectedMethodNode.instructions.insertBefore(injectedMethodNode.instructions.first, insnList)

            owningClassNode.visitEnd()

        } else {

            injectedMethodNode = MethodNode(Opcodes.ACC_PUBLIC, methodNode.name.replace("Mixin", ""), methodNode.desc, null, null)

            injectedMethodNode.instructions = insnList

            val size: Int = injectedMethodNode.instructions.size()

            injectedMethodNode.visitMaxs(size, size)
            injectedMethodNode.visitEnd()

            classNode.methods.add(injectedMethodNode)

        }

    }

    private fun isReturn(opcode: Int): Boolean {
        return when (opcode) {
            Opcodes.RETURN,
            Opcodes.ARETURN,
            Opcodes.DRETURN,
            Opcodes.FRETURN,
            Opcodes.IRETURN,
            Opcodes.LRETURN -> true
            else            -> false
        }
    }

    private fun fixInsn(insn: AbstractInsnNode, isInsertionStatic: Boolean): AbstractInsnNode {

        var fixedInsn = insn

        if (fixedInsn is MethodInsnNode) {

            if (isInsertionStatic && fixedInsn.name == "getClientInstance") {

                val clientInstanceFieldHook = classHooks.flatMap { it.asJsonObject.getAsJsonArray("field_hooks") }.first { it.asJsonObject.get("ref_name").asString == "clientInstance" }.asJsonObject

                val obfName = clientInstanceFieldHook.get("obf_name").asString
                val retDesc = clientInstanceFieldHook.get("ret_type").asString
                val access = clientInstanceFieldHook.get("access").asInt
                val owner = clientInstanceFieldHook.get("owner").asString

                fixedInsn = FieldInsnNode(if(Modifier.isStatic(access)) Opcodes.GETSTATIC else Opcodes.GETFIELD, owner, obfName, retDesc)

            } else {

                fixedInsn.name = fixedInsn.name.replace("Mixin", "")
                if (fixedInsn.owner.contains("Mixin"))
                    fixedInsn.owner = classNode.name

            }

        } else if (fixedInsn is FieldInsnNode) {
            fixedInsn.name = fixedInsn.name.replace("Mixin", "")
            if (fixedInsn.owner.contains("Mixin"))
                fixedInsn.owner = classNode.name
        }

        return fixedInsn

    }


}