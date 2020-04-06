package io.autorune.injector.type

import com.google.gson.JsonObject
import io.autorune.injector.Injector
import io.autorune.injector.PackageConstants
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.*
import java.lang.reflect.Modifier

class FieldInjector(private val fieldJson: JsonObject, private val owningClassNode: ClassNode, private val retClassJson: JsonObject?, private val noSetter: Boolean = false) : Injector()
{

    override fun runInjection() {

        val refName = fieldJson.get("ref_name").asString
        val retDesc = fieldJson.get("ret_type").asString
        val access = fieldJson.get("access").asInt

        val getterName = "get" + refName.substring(0, 1).toUpperCase() + refName.substring(1);

        val getterMethod = createGetterMethod(getterName, retDesc)
        addGetterInstructions(getterMethod, fieldJson)
        owningClassNode.methods.add(getterMethod)

        if (!Modifier.isFinal(access) && !noSetter)
        {
            val setterName = "set" + refName.substring(0, 1).toUpperCase() + refName.substring(1);
            val setterMethod = createSetterMethod(setterName, retDesc)
            addSetterInstructions(setterMethod, fieldJson)
            owningClassNode.methods.add(setterMethod)
        }

    }


    private fun createGetterMethod(getterName: String, retDesc: String) : MethodNode {

        val apiPkg = PackageConstants.osrsApiPkg

        if (retClassJson != null) {
            val pkg = retClassJson.get("package").asString.replace(".", "/")
            val obfName = retClassJson.get("obf_name").asString
            val refName = retClassJson.get("ref_name").asString
            val retType = retDesc.replace(obfName, "$apiPkg$pkg/$refName")
            return MethodNode(Opcodes.ACC_PUBLIC, getterName, "()$retType", null, null)
        }

        return MethodNode(Opcodes.ACC_PUBLIC, getterName, "()$retDesc", null, null)
    }

    private fun addGetterInstructions(getterMethod: MethodNode, fieldJson: JsonObject) {

        val obfName = fieldJson.get("obf_name").asString
        val retDesc = fieldJson.get("ret_type").asString
        val owner = fieldJson.get("owner").asString
        val access = fieldJson.get("access").asInt

        if (Modifier.isStatic(access)) {
            getterMethod.instructions.add(FieldInsnNode(Opcodes.GETSTATIC, owner, obfName, retDesc))
        } else {
            getterMethod.instructions.add(VarInsnNode(Opcodes.ALOAD, 0))
            getterMethod.instructions.add(FieldInsnNode(Opcodes.GETFIELD, owner, obfName, retDesc))
        }

        getterMethod.instructions.add(InsnNode(getReturnOpcode(retDesc)))

        val size: Int = getterMethod.instructions.size()

        getterMethod.visitMaxs(size, size)
        getterMethod.visitEnd()

    }

    private fun createSetterMethod(setterName: String, retDesc: String) : MethodNode {

        val apiPkg = PackageConstants.osrsApiPkg

        if (retClassJson != null) {
            val pkg = retClassJson.get("package").asString.replace(".", "/")
            val obfName = retClassJson.get("obf_name").asString
            val refName = retClassJson.get("ref_name").asString
            val paramType = retDesc.replace(obfName, "$apiPkg$pkg/$refName")
            return MethodNode(Opcodes.ACC_PUBLIC, setterName, "($paramType)V", null, null)
        }

        return MethodNode(Opcodes.ACC_PUBLIC, setterName, "($retDesc)V", null, null)
    }

    private fun addSetterInstructions(setterMethod: MethodNode, fieldJson: JsonObject) {

        val obfName = fieldJson.get("obf_name").asString
        val retDesc = fieldJson.get("ret_type").asString
        val owner = fieldJson.get("owner").asString
        val access = fieldJson.get("access").asInt

        val varInstrOpcode = getVarInstructionOpcode(retDesc)

        if (Modifier.isStatic(access)) {
            setterMethod.instructions.add(VarInsnNode(varInstrOpcode, 1))
            setterMethod.instructions.add(FieldInsnNode(Opcodes.PUTSTATIC, owner, obfName, retDesc))
        } else {
            setterMethod.instructions.add(VarInsnNode(Opcodes.ALOAD, 0))
            setterMethod.instructions.add(VarInsnNode(varInstrOpcode, 1))
            setterMethod.instructions.add(FieldInsnNode(Opcodes.PUTFIELD, owner, obfName, retDesc))
        }

        setterMethod.instructions?.add(InsnNode(Opcodes.RETURN))

        val size: Int = setterMethod.instructions.size()

        setterMethod.visitMaxs(size, size)
        setterMethod.visitEnd()

    }

}