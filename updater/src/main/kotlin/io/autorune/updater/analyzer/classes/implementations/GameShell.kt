package io.autorune.updater.analyzer.classes.implementations

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.*
import io.autorune.updater.analyzer.classes.implementations.devices.MouseWheel
import io.autorune.updater.analyzer.classes.implementations.devices.MouseWheelListener
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodInsnNode
import java.lang.Exception

@CorrectFieldCount(9)
@CorrectMethodCount(10)
@DependsOn(MouseWheel::class, MouseWheelListener::class)
class GameShell : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        return classPool.first { it.superName == "java/applet/Applet" }
    }

    private fun getCanvas() {
        val fn = classNode.fields.first { it.desc.toLowerCase().contains("canvas") }
        addField("canvas", fn)
    }

    private fun getFrame() {
        val fn = classNode.fields.first { it.desc.toLowerCase().contains("frame") }
        addField("frame", fn)
    }

    private fun getClipboard() {
        val fn = classNode.fields.first { it.desc.toLowerCase().contains("clipboard") }
        addField("clipboard", fn)
    }

    private fun getEventQueue() {
        val fn = classNode.fields.first { it.desc.toLowerCase().contains("queue") }
        addField("eventQueue", fn)
    }

    private fun getAwtFocus() {

        val matches = AnalyzerSearching.searchClassForField(classNode, listOf(ALOAD, ICONST_1, PUTFIELD, INVOKESTATIC))

        val fieldNode = matches[0]

        if (fieldNode.desc == "Z")
            addField("awtFocus", fieldNode)
        else
            throw Exception()

    }

    private fun getGameShell() {
        val fn = classNode.fields.first { it.desc == String.format("L%s;", classNode.name) }
        addFieldToClient("gameShell", classNode.name, fn)
    }

    private fun getCanvasDimensions() {
        for (mn in classNode.methods) {

            if (mn.desc != "()V")
                continue

            val matches = AnalyzerSearching.searchMethodForFields(mn, listOf(GETFIELD, INVOKESTATIC, PUTSTATIC, ALOAD))

            if (matches.first) {
                val widthField = matches.second[1]
                val heightField = matches.second[3]
                addField("canvasWidth", widthField)
                addField("canvasHeight", heightField)
                return
            }
        }
        throw Exception()
    }

    private fun getMouseWheelListener() {
        addField("mouseWheelListener",
                classNode.fields.first { String.format("L%s;", getClassAnalyserName(MouseWheelListener::class)) == it.desc && !isMemberStatic(it.access) })
    }

    private fun findSetMaxCanvasSize()
    {

        val match = classNode.methods.first { it.desc == "(II)V" }

        if(!isMemberStatic(match.access))
            addMethod("maxCanvasSize", match)
        else
            throw Exception()

    }

    private fun findPost()
    {

        val match = classNode.methods.first { it.desc == "(Ljava/lang/Object;)V" }

        if(!isMemberStatic(match.access))
            addMethod("post", match)
        else
            throw Exception()

    }

    private fun findMouseWheel()
    {

        val matches = AnalyzerSearching.searchClassForMethod(classNode, String.format("()L%s;", getClassAnalyserName(MouseWheel::class)))

        addMethod("mouseWheel", matches[0])

    }

    private fun findSetupClipboard()
    {

        val clipboardField = fields.first { it.fieldName == "clipboard" }.fieldNode

        val matches = AnalyzerSearching.searchClassForMethod(classNode, listOf(PUTFIELD), "()V")

        val match = matches.first { AnalyzerSearching.searchMethodForFields(it.first, listOf(PUTFIELD)).second[0].name == clipboardField.name }

        addMethod("setupClipboard", match.first)

    }

    private fun findSetupClipboardSettings()
    {

        val match = classNode.methods.first { it.desc == "(Ljava/lang/String;)V" }

        addMethod("setupClipboardSettings", match)

    }

    private fun findSetupKeyboard()
    {

        val matches = AnalyzerSearching.searchClassForMethod(classNode, listOf(INVOKESTATIC, ALOAD, GETFIELD), "()V")
                .filter { it.first.instructions.first is MethodInsnNode }

        addMethod("setupKeyboard", matches[0].first)

    }

    private fun findSetupMouse()
    {

        val matches = AnalyzerSearching.searchClassForMethod(classNode, listOf(ALOAD, GETFIELD, INVOKESTATIC), "()V")

        val match = matches.first { ma -> ma.first.name != methods.first { m -> m.newMethodName == "setupKeyboard" }.methodNode.name }

        addMethod("setupMouse", match.first)

    }

    private fun findResizeCanvas()
    {

        val matches = AnalyzerSearching.searchClassForMethod(classNode, listOf(IDIV, PUTFIELD, ALOAD, ICONST_0, PUTFIELD),"()V")

        addMethod("resizeCanvas", matches[0].first)

    }

    private fun findClientTick()
    {

        val match = AnalyzerSearching.searchClassForMethod(classNode, listOf(ALOAD, DUP, ASTORE, MONITORENTER), "()V")

        addMethod("clientTick", match[0].first)

    }

    private fun findGraphicsTick()
    {

        val matches = AnalyzerSearching.searchClassForInts(classNode, listOf(SIPUSH), "()V")

        val match = matches.first { it.second.any { it.operand == 32000 } }

        addMethod("graphicsTick", match.first)

    }

    override fun getFields() {
        getCanvas()
        getFrame()
        getClipboard()
        getEventQueue()
        getAwtFocus()
        getGameShell()
        getCanvasDimensions()
        getMouseWheelListener()
        findSetMaxCanvasSize()
        findPost()
        findMouseWheel()
        findSetupClipboard()
        findSetupClipboardSettings()
        findSetupKeyboard()
        findSetupMouse()
        findResizeCanvas()
        findClientTick()
        findGraphicsTick()
    }
}