package io.autorune.injector.transform

import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.*
import java.lang.reflect.Modifier

object ReflectionTransform {

    fun injectTransform(classNode: ClassNode, clientClassNode: ClassNode)
    {

        val newClassLoaderName = "reflectionClassLoader"

        val methodNode = classNode.methods.first { it.desc == "(Ljava/lang/String;)Ljava/lang/Class;" }

        val insnList = methodNode.instructions

        val insn = insnList.toArray().toList().first { it.opcode == Opcodes.INVOKESTATIC }

        insnList.insertBefore(insn, InsnNode(Opcodes.ICONST_1))
        insnList.insertBefore(insn, FieldInsnNode(Opcodes.GETSTATIC, "client", newClassLoaderName, "Ljava/net/URLClassLoader;"))
        insnList.insertBefore(insn, TypeInsnNode(Opcodes.CHECKCAST, "java/lang/ClassLoader"))

        insnList.set(insn, MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Class", "forName", "(Ljava/lang/String;ZLjava/lang/ClassLoader;)Ljava/lang/Class;"))

        methodNode.instructions = insnList
        methodNode.visitMaxs(insnList.size(), insnList.size())
        methodNode.visitEnd()

        classNode.visitEnd()

        clientClassNode.fields.add(FieldNode(9, newClassLoaderName, "Ljava/net/URLClassLoader;", null, null))

        val initClassLoader = clientClassNode.methods.first { it.name == "initClassLoader" }

        val iclInsnList = InsnList()

        iclInsnList.add(TypeInsnNode(Opcodes.NEW, "java/net/URLClassLoader"))
        iclInsnList.add(InsnNode(Opcodes.DUP))
        iclInsnList.add(InsnNode(Opcodes.ICONST_1))
        iclInsnList.add(TypeInsnNode(Opcodes.ANEWARRAY, "java/net/URL"))
        iclInsnList.add(InsnNode(Opcodes.DUP))
        iclInsnList.add(InsnNode(Opcodes.ICONST_0))
        iclInsnList.add(TypeInsnNode(Opcodes.NEW, "java/io/File"))
        iclInsnList.add(InsnNode(Opcodes.DUP))
        iclInsnList.add(VarInsnNode(Opcodes.ALOAD, 1))
        iclInsnList.add(MethodInsnNode(Opcodes.INVOKESPECIAL, "java/io/File", "<init>", "(Ljava/lang/String;)V"))
        iclInsnList.add(MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/io/File", "toURI", "()Ljava/net/URI;"))
        iclInsnList.add(MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/net/URI", "toURL", "()Ljava/net/URL;"))
        iclInsnList.add(InsnNode(Opcodes.AASTORE))
        iclInsnList.add(MethodInsnNode(Opcodes.INVOKESPECIAL, "java/net/URLClassLoader", "<init>", "([Ljava/net/URL;)V"))
        iclInsnList.add(FieldInsnNode(Opcodes.PUTSTATIC, "client", newClassLoaderName, "Ljava/net/URLClassLoader;"))
        iclInsnList.add(InsnNode(Opcodes.RETURN))

        initClassLoader.instructions = iclInsnList
        initClassLoader.visitMaxs(iclInsnList.size(), iclInsnList.size())
        initClassLoader.visitEnd()

        clientClassNode.visitEnd()
    }

}