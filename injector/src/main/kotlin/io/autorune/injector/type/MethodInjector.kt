package io.autorune.injector.type

import com.google.gson.JsonObject
import io.autorune.injector.Injector
import io.autorune.injector.PackageConstants
import io.autorune.injector.util.DescriptorUtils
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.*
import java.lang.reflect.Modifier

class MethodInjector(private val methodJson: JsonObject, private val owningClassNode: ClassNode,
                     private val retClassJson: JsonObject?, private val paramClassJsons: List<JsonObject>) : Injector()
{

    override fun runInjection() {

        val refName = methodJson.get("ref_name").asString
        val descriptor = methodJson.get("descriptor").asString

        val invokeMethod = createInvokeMethod(refName, descriptor)

        addInvokeInstructions(invokeMethod, methodJson)

        owningClassNode.methods.add(invokeMethod)

    }


    private fun createInvokeMethod(methodName: String, descriptor: String) : MethodNode {

        var retTypeDesc = descriptor.split(")")[1]
        var paramsDesc = descriptor.split("(")[1].split(")")[0]

        val apiPkg = PackageConstants.osrsApiPkg

        if (retClassJson != null) {
            val pkg = retClassJson.get("package").asString.replace(".", "/")
            val obfName = retClassJson.get("obf_name").asString
            val refName = retClassJson.get("ref_name").asString
            retTypeDesc = retTypeDesc.replace(obfName, "$apiPkg$pkg/$refName")
        }

        for (paramClassJson in paramClassJsons) {

            val pkg = paramClassJson.get("package").asString.replace(".", "/")
            val obfName = paramClassJson.get("obf_name").asString
            val refName = paramClassJson.get("ref_name").asString
            paramsDesc = paramsDesc.replace("L$obfName;", "L$apiPkg$pkg/$refName;")

        }

        return MethodNode(Opcodes.ACC_PUBLIC, methodName, "($paramsDesc)$retTypeDesc", null, null)
    }

    private fun addInvokeInstructions(invokeMethod: MethodNode, methodJson: JsonObject) {

        val obfName = methodJson.get("obf_name").asString

        val descriptor = methodJson.get("descriptor").asString
        val paramsDesc = descriptor.split("(")[1].split(")")[0]
        val retTypeDesc = descriptor.split(")")[1]

        val owner = methodJson.get("owner").asString
        val access = methodJson.get("access").asInt

        val paramStrings = DescriptorUtils.getParamStrings(paramsDesc)

        if (!Modifier.isStatic(access))
            invokeMethod.instructions.add(VarInsnNode(Opcodes.ALOAD, 0))

        var offset = 1

        paramStrings.forEachIndexed { index, paramDesc ->

            val instrOpcode = getVarInstructionOpcode(paramDesc)
            invokeMethod.instructions.add(VarInsnNode(instrOpcode, index + offset))
            if (instrOpcode == Opcodes.LLOAD || instrOpcode == Opcodes.DLOAD) // Longs and doubles take two stack slots
                offset++

        }

        if (Modifier.isStatic(access))
            invokeMethod.instructions.add(MethodInsnNode(Opcodes.INVOKESTATIC, owner, obfName, descriptor))
        else
            invokeMethod.instructions.add(MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner, obfName, descriptor))

        invokeMethod.instructions?.add(InsnNode(getReturnOpcode(retTypeDesc)))

        val size: Int = invokeMethod.instructions.size()

        invokeMethod.visitMaxs(size, size)
        invokeMethod.visitEnd()

    }

}