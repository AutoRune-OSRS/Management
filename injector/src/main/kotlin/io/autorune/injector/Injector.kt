package io.autorune.injector

import org.objectweb.asm.Opcodes

abstract class Injector
{

    abstract fun runInjection()

    fun getReturnOpcode(retDesc: String): Int {

        when (retDesc) {
            "B", "C", "I", "S", "Z" -> return Opcodes.IRETURN
            "J" -> return Opcodes.LRETURN
            "F" -> return Opcodes.FRETURN
            "D" -> return Opcodes.DRETURN
            "V" -> return Opcodes.RETURN
        }

        return Opcodes.ARETURN

    }

    fun getVarInstructionOpcode(desc: String): Int {

        when (desc) {
            "B", "C", "I", "S", "Z" -> return Opcodes.ILOAD
            "J" -> return Opcodes.LLOAD
            "F" -> return Opcodes.FLOAD
            "D" -> return Opcodes.DLOAD
        }

        return Opcodes.ALOAD

    }

}