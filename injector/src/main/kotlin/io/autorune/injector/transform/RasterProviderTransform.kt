package io.autorune.injector.transform

import com.google.gson.JsonObject
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.*
import java.lang.reflect.Modifier

object RasterProviderTransform
{

	fun injectTransform(cn : ClassNode, classHook: JsonObject)
	{

		val methodHooks = classHook.get("method_hooks").asJsonArray

		val drawFullMethodName = methodHooks.first { it.asJsonObject.get("ref_name").asString == "drawFull" }.asJsonObject.get("obf_name").asString

		val drawFullMn = cn.methods.first { it.name == drawFullMethodName && !Modifier.isStatic(it.access) }

		drawFullMn.tryCatchBlocks.clear()

		val instructions = InsnList()

		val ourDrawCall  = MethodInsnNode(Opcodes.INVOKEVIRTUAL,
				"io/autorune/client/instance/ClientInstanceCallbacks",
				"drawRaster",
				"(Lio/autorune/osrs/api/graphics/RasterProvider;Ljava/awt/Graphics;II)V")

		instructions.add(FieldInsnNode(Opcodes.GETSTATIC, "client", "clientCallbacks", "Lio/autorune/client/instance/ClientInstanceCallbacks;"))
		instructions.add(VarInsnNode(Opcodes.ALOAD, 0))
		instructions.add(VarInsnNode(Opcodes.ALOAD, 1))
		instructions.add(VarInsnNode(Opcodes.ILOAD, 2))
		instructions.add(VarInsnNode(Opcodes.ILOAD, 3))
		instructions.add(ourDrawCall)
		instructions.add(InsnNode(Opcodes.RETURN))

		drawFullMn.instructions = instructions

		drawFullMn.visitMaxs(instructions.size(), instructions.size())
		drawFullMn.visitEnd()

		cn.visitEnd()

	}

}