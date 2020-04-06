package io.autorune.injector.transform

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.*
import java.lang.reflect.Modifier

object GameShellTransform
{
    fun injectTransform(cn : ClassNode, classHook: JsonObject)
    {
        cn.interfaces.removeIf { it.contains("Listener") }

        val methodHooks = classHook.get("method_hooks").asJsonArray

        val clientTickMethodName = methodHooks.first { it.asJsonObject.get("ref_name").asString == "clientTick" }.asJsonObject.get("obf_name").asString

        val runMethodNode = cn.methods.first { it.name == "run" && !Modifier.isStatic(it.access) }

        val instructions = runMethodNode.instructions

        val clientTickCallNext = instructions.toArray().toList().filterIsInstance<MethodInsnNode>().first { it.name == clientTickMethodName }.next
        val ourClientTickCall  = MethodInsnNode(Opcodes.INVOKEVIRTUAL, "io/autorune/client/instance/ClientInstanceCallbacks", "clientTick", "()V")

        instructions.insertBefore(clientTickCallNext, FieldInsnNode(Opcodes.GETSTATIC, "client", "clientCallbacks", "Lio/autorune/client/instance/ClientInstanceCallbacks;"))
        instructions.insertBefore(clientTickCallNext, ourClientTickCall)

        runMethodNode.instructions = instructions

        runMethodNode.visitMaxs(instructions.size(), instructions.size())
        runMethodNode.visitEnd()

        cn.visitEnd()
    }

}