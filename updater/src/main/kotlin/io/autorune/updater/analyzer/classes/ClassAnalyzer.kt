package io.autorune.updater.analyzer.classes

import io.autorune.updater.analyzer.AsmConstants
import io.autorune.updater.analyzer.classes.members.Field
import io.autorune.updater.analyzer.classes.members.Method
import io.autorune.updater.analyzer.classes.repo.ClassRepository
import io.autorune.updater.analyzer.classes.util.AnalyzerUtils
import io.autorune.updater.analyzer.util.AsmUtils
import org.objectweb.asm.tree.*
import java.lang.reflect.Modifier
import java.util.ArrayList
import kotlin.reflect.KClass

abstract class ClassAnalyzer : AsmConstants() {

    lateinit var classNode: ClassNode

    var fields = ArrayList<Field>()
    var methods = ArrayList<Method>()

    val analyzerName: String = javaClass.simpleName

    var readyForDependencies: Boolean = false

    val classPool = ClassRepository.classPool


    protected abstract fun findClassNode(): ClassNode?

    protected abstract fun getFields()

    fun runClassStage() {
        classNode = findClassNode()!!
        println(javaName())
        readyForDependencies = true
    }

    fun runFieldStage() {
        getFields()
    }

    fun getFoundFields(): Int {
        return fields.size
    }

    fun getFoundMethods(): Int {
        return methods.size
    }

    fun getClassAnalyser(classAnalyzerClass: KClass<out ClassAnalyzer>): ClassAnalyzer? {
        return AnalyzerUtils.getClassAnalyser(classAnalyzerClass.simpleName ?: "poop")
    }

    fun getClassAnalyserName(classAnalyzerClass: KClass<out ClassAnalyzer>): String? {
        return AnalyzerUtils.getClassAnalyser(classAnalyzerClass.simpleName ?: "poop")?.classNode?.name
    }

    fun isMemberStatic(mod: Int): Boolean {
        return Modifier.isStatic(mod)
    }

    fun getField(fieldName: String): FieldNode {
        return fields.first { it.fieldName == fieldName }.fieldNode
    }

    fun addField(fieldName: String, fieldNode: FieldInsnNode, documentation: String = "Auto Generated Field") {
        AsmUtils.insnToField(fieldNode)?.let { Field(it, fieldNode.owner, fieldName, false) }?.let { fields.add(it) }
    }

    fun addField(fieldName: String, owner: String, fieldNode: FieldNode, documentation: String = "Auto Generated Field") {
        fields.add(Field(fieldNode, owner, fieldName, false))
    }

    fun addField(fieldName: String, fieldNode: FieldNode, documentation: String = "Auto Generated Field") {
        fields.add(Field(fieldNode, classNode.name, fieldName, false))
    }

    fun addFieldToClient(fieldName: String, fieldNode: FieldInsnNode, documentation: String = "Auto Generated Field") {
        AsmUtils.insnToField(fieldNode)?.let { Field(it, fieldNode.owner, fieldName, true) }?.let { fields.add(it) }
    }

    fun addFieldToClient(fieldName: String, owner: String, fieldNode: FieldNode, documentation: String = "Auto Generated Field") {
        fields.add(Field(fieldNode, owner, fieldName, true))
    }

    fun addMethod(methodName: String, methodNode: MethodNode, documentation: String = "Auto Generated Method") {
        methods.add(Method(methodNode, classNode.name, methodName, false))
    }

    fun addMethod(methodName: String, methodNode: MethodInsnNode, documentation: String = "Auto Generated Method") {
        AsmUtils.insnToMethod(methodNode)?.let { Method(it, methodNode.owner, methodName, false) }?.let { methods.add(it) }
    }

    fun addMethod(methodName: String, owner: String, methodNode: MethodNode, documentation: String = "Auto Generated Method") {
        methods.add(Method(methodNode, owner, methodName, false))
    }

    fun addMethodToClient(methodName: String, owner: String, methodNode: MethodNode, documentation: String = "Auto Generated Method") {
        methods.add(Method(methodNode, owner, methodName, true))
    }

    fun addMethodToClient(methodName: String, methodNode: MethodInsnNode, documentation: String = "Auto Generated Method") {
        AsmUtils.insnToMethod(methodNode)?.let { Method(it, methodNode.owner, methodName, true) }?.let { methods.add(it) }
    }

    fun print(totalFields: Int, totalMethods: Int) {
        println(toString() + String.format(" (%s/%s) (%s/%s)", getFoundFields().toString(), totalFields.toString(), getFoundMethods().toString(), totalMethods.toString()))
        for (f in fields) {
            f.print()
        }
        for (m in methods) {
            m.print()
        }
    }

    fun javaName() : String
    {
        return this.javaClass.simpleName
    }

    override fun toString(): String {
        val s = ""
        return "\n[- " + this.javaClass.simpleName + " identified as: " + classNode.name + " extends " + AnalyzerUtils.clean(classNode.superName ?: "") + " -]" + s
    }

}