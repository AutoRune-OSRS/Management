package io.autorune.osrs.mixin

import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode
import java.io.File
import java.util.ArrayList

object MixinFetcher {

    fun getAllMixinClassNodes() : List<ClassNode> {

        val mixinClassNodes = ArrayList<ClassNode>()

        val mixinClassFileBytes = getAllMixinByteArrays()

        for (mixinBytes in mixinClassFileBytes) {

            val mixinClassNode = ClassNode()

            ClassReader(mixinBytes).accept(mixinClassNode, ClassReader.SKIP_DEBUG or ClassReader.SKIP_FRAMES)

            mixinClassNodes.add(mixinClassNode)

        }

        mixinClassNodes.forEach {
            classNode ->
            classNode.fields = classNode.fields.filter { it.name != "Companion" }
            classNode.methods = classNode.methods.filter { it.name != "<init>" && it.name != "<clinit>" }
        }

        return mixinClassNodes

    }

    private fun getAllMixinByteArrays(): List<ByteArray>
    {

        val packageName = "io.autorune.osrs.mixins"

        val path = packageName.replace(".", File.separator)

        val byteArrays = ArrayList<ByteArray>()

        val classPathEntry = System.getProperty("java.class.path").split(System.getProperty("path.separator").toRegex()).dropLastWhile { it.isEmpty() }
                .first().split("client-management")[0]+"client-management"+ File.separator+"mixins"+ File.separator+"target"+ File.separator+"classes"

        try {
            val base = File(classPathEntry + File.separator + path)
            byteArrays.addAll(searchDirectory(packageName, base))
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return byteArrays
    }

    @Throws(Exception::class)
    private fun searchDirectory(packageName: String, base: File): List<ByteArray> {
        val byteArrays = ArrayList<ByteArray>()
        var name: String
        val baseFiles = base.listFiles() ?: return byteArrays
        for (file in baseFiles) {
            if (file.isDirectory)
                byteArrays.addAll(searchDirectory(packageName + "." + file.name, file))
            else {
                name = file.name
                if (name.endsWith(".class")) {
                    name = name.substring(0, name.length - 6)
                    if (name.endsWith("Mixin"))
                        byteArrays.add(file.readBytes())
                }
            }
        }
        return byteArrays
    }

}