package io.autorune.injector.transform

import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.*

object CallbackTransform
{

	fun injectTransform(clientClassNode: ClassNode)
	{

		clientClassNode.fields.add(FieldNode(9, "clientCallbacks", "Lio/autorune/client/instance/ClientInstanceCallbacks;", null, null))

		val initCallbackMn = clientClassNode.methods.first { it.name == "initCallback" }

		val icbInsnList = InsnList()

		icbInsnList.add(TypeInsnNode(Opcodes.NEW, "io/autorune/client/instance/ClientInstanceCallbacks"))
		icbInsnList.add(InsnNode(Opcodes.DUP))
		icbInsnList.add(MethodInsnNode(Opcodes.INVOKESPECIAL, "io/autorune/client/instance/ClientInstanceCallbacks", "<init>", "()V"))
		icbInsnList.add(FieldInsnNode(Opcodes.PUTSTATIC, "client", "clientCallbacks", "Lio/autorune/client/instance/ClientInstanceCallbacks;"))

		icbInsnList.add(FieldInsnNode(Opcodes.GETSTATIC, "client", "clientCallbacks", "Lio/autorune/client/instance/ClientInstanceCallbacks;"))
		icbInsnList.add(VarInsnNode(Opcodes.ALOAD, 0))
		icbInsnList.add(MethodInsnNode(Opcodes.INVOKESTATIC,
				"io/autorune/client/instance/ClientInstanceRepo", "getInstanceFromClient", "(Lio/autorune/osrs/api/Client;)Lio/autorune/client/instance/ClientInstance;"))
		icbInsnList.add(FieldInsnNode(Opcodes.PUTFIELD, "io/autorune/client/instance/ClientInstanceCallbacks", "clientInstance", "Lio/autorune/client/instance/ClientInstance;"))
		icbInsnList.add(InsnNode(Opcodes.RETURN))

		initCallbackMn.instructions = icbInsnList
		initCallbackMn.visitMaxs(icbInsnList.size(), icbInsnList.size())
		initCallbackMn.visitEnd()

		clientClassNode.visitEnd()

	}

}