package io.autorune.updater.analyzer.classes.implementations.widget

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.collection.Node
import io.autorune.updater.analyzer.classes.implementations.font.Font
import io.autorune.updater.analyzer.classes.implementations.io.RSByteBuffer
import io.autorune.updater.analyzer.classes.implementations.model.Model
import io.autorune.updater.analyzer.classes.implementations.sprite.Sprite
import io.autorune.updater.analyzer.classes.implementations.sprite.SpriteMask
import io.autorune.updater.analyzer.util.AnalyzerSearching
import io.autorune.updater.analyzer.classes.util.AnalyzerUtils
import org.objectweb.asm.tree.ClassNode
import java.lang.Exception
import java.lang.reflect.Modifier


@CorrectFieldCount(106)
@CorrectMethodCount(18)
@DependsOn(Node::class, RSByteBuffer::class, Sprite::class, Font::class, Model::class, SpriteMask::class)
class Widget : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyser(Node::class)?.classNode?.name)
                continue

            val match = classNode.fields.count { it.desc == "[Ljava/lang/Object;" && !Modifier.isStatic(it.access) } > 10

            if (match)
                return classNode

        }

        return null
    }

    private fun decode() {

        val matches = AnalyzerSearching.searchClassForMethod(classNode,
                listOf(ALOAD, ALOAD, ALOAD, INVOKEVIRTUAL),
                String.format("(L%s;)V", getClassAnalyserName(RSByteBuffer::class)))

        addMethod("decode", matches[0].first)

    }

    private fun decodeLegacy() {

        val matches = AnalyzerSearching.searchClassForMethod(classNode,
                String.format("(L%s;)V", getClassAnalyserName(RSByteBuffer::class)))

        val match = matches.first { it1 -> it1 != methods.first { it.newMethodName == "decode" }.methodNode }

        addMethod("decodeLegacy", match)

    }

    private fun readListener() {

        val matches = AnalyzerSearching.searchClassForMethod(classNode,
                String.format("(L%s;)[%s", getClassAnalyserName(RSByteBuffer::class), descObject))

        addMethod("readListener", matches[0])

    }

    private fun fetchSprite() {

        val matches = AnalyzerSearching.searchClassForMethod(classNode,
                String.format("(Z)L%s;", getClassAnalyserName(Sprite::class)))

        addMethod("fetchSprite", matches[0])

    }

    private fun getDecodedFields() {

        val decodeMn = methods.first { it.newMethodName == "decode" }.methodNode

        val matches = AnalyzerSearching.searchMethodForFieldsInCodeOrder(decodeMn, PUTFIELD)

        val filteredMatches = AnalyzerUtils.filterFields(matches, fields)

        addField("isCs2Widget", filteredMatches[0])
        addField("type", filteredMatches[1])
        addField("contentType", filteredMatches[2])
        addField("rawX", filteredMatches[3])
        addField("rawY", filteredMatches[4])
        addField("rawWidth", filteredMatches[5])
        addField("rawHeight", filteredMatches[6])
        addField("widthAlignment", filteredMatches[7])
        addField("heightAlignment", filteredMatches[8])
        addField("xAlignment", filteredMatches[9])
        addField("yAlignment", filteredMatches[10])
        addField("parentHash", filteredMatches[11])
        addField("isHidden", filteredMatches[12])
        addField("scrollWidth", filteredMatches[13])
        addField("scrollHeight", filteredMatches[14])
        addField("noClickThrough", filteredMatches[15])
        addField("spriteId", filteredMatches[16])
        addField("spriteAngle", filteredMatches[17])
        addField("spriteTiling", filteredMatches[18])
        addField("transparencyTop", filteredMatches[19])
        addField("outline", filteredMatches[20])
        addField("spriteShadow", filteredMatches[21])
        addField("spriteFlipV", filteredMatches[22])
        addField("spriteFlipH", filteredMatches[23])
        addField("modelType", filteredMatches[24])
        addField("modelId", filteredMatches[25])
        addField("modelOffsetX", filteredMatches[26])
        addField("modelOffsetY", filteredMatches[27])
        addField("modelAngleX", filteredMatches[28])
        addField("modelAngleY", filteredMatches[29])
        addField("modelAngleZ", filteredMatches[30])
        addField("modelZoom", filteredMatches[31])
        addField("sequenceId", filteredMatches[32])
        addField("modelOrthogonal", filteredMatches[33])
        addField("field6969", filteredMatches[34])
        addField("fontId", filteredMatches[35])
        addField("text", filteredMatches[36])
        addField("textLineHeight", filteredMatches[37])
        addField("textXAlignment", filteredMatches[38])
        addField("textYAlignment", filteredMatches[39])
        addField("textShadowed", filteredMatches[40])
        addField("color", filteredMatches[41])
        addField("fill", filteredMatches[42])
        addField("lineWidth", filteredMatches[43])
        addField("field696969", filteredMatches[44])
        addField("clickMask", filteredMatches[45])
        addField("dataText", filteredMatches[46])
        addField("actions", filteredMatches[47])
        addField("dragZoneSize", filteredMatches[48])
        addField("dragThreshold", filteredMatches[49])
        addField("isScrollBar", filteredMatches[50])
        addField("spellActionName", filteredMatches[51])
        addField("onLoad", filteredMatches[52])
        addField("onMouseOver", filteredMatches[53])
        addField("onMouseLeave", filteredMatches[54])
        addField("onTargetLeave", filteredMatches[55])
        addField("onTargetEnter", filteredMatches[56])
        addField("onVarTransmit", filteredMatches[57])
        addField("onInvTransmit", filteredMatches[58])
        addField("onStatTransmit", filteredMatches[59])
        addField("onTimer", filteredMatches[60])
        addField("onOp", filteredMatches[61])
        addField("onMouseRepeat", filteredMatches[62])
        addField("onClick", filteredMatches[63])
        addField("onClickRepeat", filteredMatches[64])
        addField("onRelease", filteredMatches[65])
        addField("onHold", filteredMatches[66])
        addField("onDrag", filteredMatches[67])
        addField("onDragComplete", filteredMatches[68])
        addField("onScroll", filteredMatches[69])
        addField("varTransmitTriggers", filteredMatches[70])
        addField("invTransmitTriggers", filteredMatches[71])
        addField("statTransmitTriggers", filteredMatches[72])

    }

    private fun getDecodedLegacyFields() {

        val decodeMn = methods.first { it.newMethodName == "decodeLegacy" }.methodNode

        val matches = AnalyzerSearching.searchMethodForFieldsInCodeOrder(decodeMn, PUTFIELD)

        val filteredMatches = AnalyzerUtils.filterFields(matches, fields)

        addField("buttonType", filteredMatches[0])
        addField("mouseOverRedirect", filteredMatches[1])
        addField("cs1Comparisons", filteredMatches[2])
        addField("cs1ComparisonValues", filteredMatches[3])
        addField("cs1Instructions", filteredMatches[4])
        addField("itemIds", filteredMatches[5])
        addField("itemQuantities", filteredMatches[6])
        addField("paddingX", filteredMatches[7])
        addField("paddingY", filteredMatches[8])
        addField("inventoryXOffsets", filteredMatches[9])
        addField("inventoryYOffsets", filteredMatches[10])
        addField("inventorySprites", filteredMatches[11])
        addField("text2", filteredMatches[12])
        addField("color2", filteredMatches[13])
        addField("mouseOverColor", filteredMatches[14])
        addField("mouseOverColor2", filteredMatches[15])
        addField("spriteId2", filteredMatches[16])
        addField("modelType2", filteredMatches[17])
        addField("modelId2", filteredMatches[18])
        addField("sequenceId2", filteredMatches[19])
        addField("itemActions", filteredMatches[20])
        addField("spellName", filteredMatches[21])
        addField("buttonText", filteredMatches[22])

    }

    private fun getHasListener() {

        val decodeMn = methods.first { it.newMethodName == "readListener" }.methodNode

        val matches = AnalyzerSearching.searchMethodForFields(decodeMn, listOf(PUTFIELD)).second

        addField("hasListener", matches[0])

    }

    private fun readListenerTriggers()
    {

        val matches = AnalyzerSearching.searchClassForMethod(classNode,
                String.format("(L%s;)[%s", getClassAnalyserName(RSByteBuffer::class), descInt))

        addMethod("readListenerTriggers", matches[0])

    }

    private fun swapItems()
    {
        val matches = AnalyzerSearching.searchClassForMethod(classNode, "(II)V")

        val match = matches.first { !isMemberStatic(it.access) }

        addMethod("swapItems", match)
    }

    private fun fetchFont()
    {

        val matches = AnalyzerSearching.searchClassForMethod(classNode,
                String.format("()L%s;", getClassAnalyserName(Font::class)))

        addMethod("fetchFont", matches[0])

    }

    private fun fetchInventorySprite()
    {

        val matches = AnalyzerSearching.searchClassForMethod(classNode,
                String.format("(I)L%s;", getClassAnalyserName(Sprite::class)))

        addMethod("fetchInventorySprite", matches[0])

    }

    private fun fetchModel()
    {

        val match = classNode.methods.first { it.desc.contains(String.format(")L%s;", getClassAnalyserName(Model::class))) }

        addMethod("fetchModel", match)

    }

    private fun fetchSpriteMask()
    {

        val match = classNode.methods.first { it.desc.contains(String.format(")L%s;", getClassAnalyserName(SpriteMask::class))) }

        addMethod("fetchSpriteMask", match)

    }

    private fun setAction()
    {
        val matches = AnalyzerSearching.searchClassForMethod(classNode, "(ILjava/lang/String;)V")

        val match = matches.first { !isMemberStatic(it.access) }

        addMethod("setAction", match)
    }

    private fun getWidgets() {
        for (cN in classPool) {
            val field = cN.fields.find { it.desc == String.format("[[L%s;", classNode.name) && isMemberStatic(it.access) }
            if (field != null) {
                addFieldToClient("widgets", cN.name, field)
                return
            }
        }
        throw Exception()
    }

    private fun getWidgetCache() {
        for (cN in classPool) {
            val field = cN.fields.find { it.desc == String.format("[L%s;", classNode.name) && isMemberStatic(it.access) }
            if (field != null) {
                addFieldToClient("widgetCache", cN.name, field)
                return
            }
        }
        throw Exception()
    }

    fun findCreateItemSprite()
    {

        val match = AnalyzerSearching.searchAllClassesForMethod(listOf(), String.format("(IIIIIZ)L%s;", getClassAnalyserName(Sprite::class)))

        addMethodToClient("createItemSprite", match.first().second.name, match.first().first)

    }

    private fun findAlignWidgetSize()
    {

        val match = AnalyzerSearching.searchAllClassesForMethod(listOf(), String.format("(L%s;IIZ)V", getClassAnalyserName(Widget::class))).filter { isMemberStatic(it.first.access) }

        addMethodToClient("alignWidgetSize", match.first().second.name, match.first().first)

    }

    private fun findWidgetDimensions()
    {

        val methodN = methods.first { it.newMethodName == "alignWidgetSize" }.methodNode

        val matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(GETFIELD)).second

        addField("width", matches[0])
        addField("height", matches[1])

    }

    private fun findDrawInterface()
    {

        val match = AnalyzerSearching.searchAllClassesForMethod(listOf(), String.format("([L%s;IIIIIIII)V", getClassAnalyserName(Widget::class))).filter { isMemberStatic(it.first.access) }

        addMethodToClient("drawInterface", match.first().second.name, match.first().first)

    }

    private fun findXandY()
    {

        val methodN = methods.first { it.newMethodName == "drawInterface" }.methodNode

        val matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(GETFIELD)).second

        val filteredMatches = AnalyzerUtils.filterFields(matches, fields)

        addField("x", filteredMatches[0])
        addField("y", filteredMatches[1])

    }

    private fun findWidgetChildrenField()
    {

        val methodN = methods.first { it.newMethodName == "drawInterface" }.methodNode

        val matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(GETFIELD)).second

        val filteredMatches = AnalyzerUtils.filterFields(matches, fields)

        addField("widgetHash", filteredMatches[2])

        addField("children", filteredMatches[4])

    }

    private fun findGetChild()
    {

        val match = AnalyzerSearching.searchAllClassesForMethod(listOf(), String.format("(I)L%s;", getClassAnalyserName(Widget::class))).filter { isMemberStatic(it.first.access) }

        addMethodToClient("child", match.first().second.name, match.first().first)

    }

    private fun findRevalidateWidgetScroll()
    {

        val match = AnalyzerSearching.searchAllClassesForMethod(listOf(), String.format("([L%s;L%s;Z)V", getClassAnalyserName(Widget::class), getClassAnalyserName(Widget::class))).filter { isMemberStatic(it.first.access) }

        addMethodToClient("revalidateWidgetScroll", match.first().second.name, match.first().first)

    }

    private fun findWidgetParentsCache()
    {

        val methodN = methods.first { it.newMethodName == "revalidateWidgetScroll" }.methodNode

        var matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(GETSTATIC)).second

        addFieldToClient("widgetNodeCache", matches.first())

    }

    private fun findResizeWidget()
    {

        val methodN = methods.first { it.newMethodName == "revalidateWidgetScroll" }.methodNode

        var matches = AnalyzerSearching.searchMethodForMethodCall(methodN, listOf(INVOKESTATIC)).second

        addMethodToClient("resizeWidget", matches.first())

    }

    private fun findRunComponentCloseListeners() {

        val matches = AnalyzerSearching.searchAllClassesForMethod(listOf(), String.format("([L%s;I)V", classNode.name))

        val match = matches.first { it.first.instructions.size() < 150 }

        addMethodToClient("runComponentCloseListeners", match.second.name, match.first)

    }

    override fun getFields() {

        getWidgets()
        getWidgetCache()

        findRunComponentCloseListeners()

        decode()
        decodeLegacy()
        readListener()
        fetchSprite()
        readListenerTriggers()
        swapItems()
        fetchFont()
        fetchInventorySprite()
        fetchModel()
        fetchSpriteMask()
        setAction()
        getDecodedFields()
        getDecodedLegacyFields()
        getHasListener()
        findCreateItemSprite()

        findAlignWidgetSize()
        findWidgetDimensions()

        findDrawInterface()
        findXandY()
        findWidgetChildrenField()
        findGetChild()

        findRevalidateWidgetScroll()
        findWidgetParentsCache()
        findResizeWidget()

    }


}