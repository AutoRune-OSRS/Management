package io.autorune.injector.type.mixin

import io.autorune.injector.Injector
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.*
import java.lang.reflect.Modifier

class MixinFieldInjector(private val fieldNode: FieldNode, private val classNode: ClassNode) : Injector()
{


    override fun runInjection() {

        val fieldName = fieldNode.name.replace("Mixin", "")
        val fieldDesc = fieldNode.desc

        val injectedFieldNode = FieldNode(if (Modifier.isStatic(fieldNode.access)) 9 else 1, fieldName, fieldDesc, null, fieldNode.value)

        injectedFieldNode.visitEnd()

        classNode.fields.add(injectedFieldNode)

        addGetter(injectedFieldNode)
        addSetter(injectedFieldNode)

    }

    private fun addGetter(fieldNode: FieldNode) {

        val name = fieldNode.name

        val getterName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);

        val injectMethodNode = MethodNode(Opcodes.ACC_PUBLIC, getterName, "()${fieldNode.desc}", null, null)

        val isStatic = Modifier.isStatic(fieldNode.access)

        if (isStatic)
            injectMethodNode.instructions.add(FieldInsnNode(Opcodes.GETSTATIC, classNode.name, name, fieldNode.desc))
        else
        {
            injectMethodNode.instructions.add(VarInsnNode(Opcodes.ALOAD, 0))
            injectMethodNode.instructions.add(FieldInsnNode(Opcodes.GETFIELD, classNode.name, name, fieldNode.desc))
        }

        injectMethodNode.instructions.add(InsnNode(getReturnOpcode(fieldNode.desc)))

        val size: Int = injectMethodNode.instructions.size()

        injectMethodNode.visitMaxs(size, size)
        injectMethodNode.visitEnd()

        classNode.methods.add(injectMethodNode)

    }

    private fun addSetter(fieldNode: FieldNode) {

        val name = fieldNode.name

        val setterName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);

        val injectMethodNode = MethodNode(Opcodes.ACC_PUBLIC, setterName, "(${fieldNode.desc})V", null, null)

        val varInstrOpcode = getVarInstructionOpcode(fieldNode.desc)

        val isStatic = Modifier.isStatic(fieldNode.access)

        if (isStatic) {
            injectMethodNode.instructions.add(VarInsnNode(varInstrOpcode, 1))
            injectMethodNode.instructions.add(FieldInsnNode(Opcodes.PUTSTATIC, classNode.name, name, fieldNode.desc))
        } else {
            injectMethodNode.instructions.add(VarInsnNode(Opcodes.ALOAD, 0))
            injectMethodNode.instructions.add(VarInsnNode(varInstrOpcode, 1))
            injectMethodNode.instructions.add(FieldInsnNode(Opcodes.PUTFIELD, classNode.name, name, fieldNode.desc))
        }

        injectMethodNode.instructions?.add(InsnNode(Opcodes.RETURN))

        val size: Int = injectMethodNode.instructions.size()

        injectMethodNode.visitMaxs(size, size)
        injectMethodNode.visitEnd()

        classNode.methods.add(injectMethodNode)

    }

}