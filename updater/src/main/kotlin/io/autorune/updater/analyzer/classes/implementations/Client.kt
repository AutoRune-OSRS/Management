package io.autorune.updater.analyzer.classes.implementations

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.ClassDocumentation
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.io.PacketBuffer
import io.autorune.updater.analyzer.classes.implementations.widget.Widget
import io.autorune.updater.analyzer.util.AnalyzerSearching
import io.autorune.updater.analyzer.classes.util.AnalyzerUtils
import io.autorune.updater.analyzer.util.AsmUtils
import org.objectweb.asm.tree.ClassNode
import java.lang.Exception

@CorrectFieldCount(34)
@CorrectMethodCount(21)
@DependsOn(GameShell::class, PacketBuffer::class, ClientPreferences::class, Widget::class)
@ClassDocumentation("OSRS Client Class")
class Client : ClassAnalyzer()
{

	override fun findClassNode() : ClassNode?
	{
		return classPool.first { it.superName == getClassAnalyserName(GameShell::class) }
	}

	private fun getClientInstance()
	{
		for(cN in classPool)
		{
			val field = cN.fields.find { it.desc == String.format("L%s;", classNode.name) && isMemberStatic(it.access) }
			if(field != null)
			{
				addField("clientInstance", cN.name, field, "OSRS Client Instance Field")
				return
			}
		}
		throw Exception()
	}

	private fun findAddPlayerToScene()
	{

		val matches = AnalyzerSearching.searchAllClassesForMethod(
				listOf(ALOAD, GETFIELD, BIPUSH, ISHR, ISTORE, ALOAD, GETFIELD, BIPUSH, ISHR, ISTORE)
		).filter { isMemberStatic(it.first.access) && it.first.desc != "(Z)V" }

		addMethod("addPlayerToScene", matches[0].second.name, matches[0].first)

	}

	private fun findAddMenuOptions()
	{

		val match = AnalyzerSearching.searchAllClassesForInts(listOf(SIPUSH), "(IIII)V")
				.first { isMemberStatic(it.second.access) && it.third.any { intInsn -> intInsn.operand == 1004 } }

		addMethod("addSceneMenuOptions", match.first.name, match.second)

	}

	private fun findDoCycles()
	{

		val clazz = getClassAnalyser(GameShell::class) ?: return

		val methodN = clazz.methods.first { it.newMethodName == "clientTick" }.methodNode

		val matches = AnalyzerSearching.searchMethodForMethodCall(methodN, listOf(INVOKEVIRTUAL)).second

		val match = matches.first()

		val methodName = match.name
		val methodDesc = match.desc

		val doCycleMethod = classNode.methods.first { it.name == methodName && it.desc == methodDesc }

		addMethod("doCycle", doCycleMethod)

		val cycleMethods = AnalyzerSearching.searchMethodForMethodCall(doCycleMethod, listOf(INVOKEVIRTUAL)).second

		addMethod("doCycleLoggedOut", cycleMethods.last())

		addMethod("doCycleLoggedIn", cycleMethods[cycleMethods.size - 2])

	}

	private fun findCycleTickField()
	{

		val method = methods.first { it.newMethodName == "doCycle" }.methodNode

		val matches = AnalyzerSearching.searchMethodForFields(method, listOf(PUTSTATIC)).second

		addField("cycleTick", matches.first())

	}

	private fun findUpdateGameState()
	{

		val matches = AnalyzerSearching.searchAllClassesForInts(listOf(BIPUSH), "(I)V")
				.filter { isMemberStatic(it.second.access)
						&& it.third.any { intInsnNode -> intInsnNode.operand == 20 }
						&& it.third.any { intInsnNode -> intInsnNode.operand == 40 }
						&& it.third.any { intInsnNode -> intInsnNode.operand == 45 }
				}

		addMethod("updateGameState", matches.first().first.name, matches.first().second)

	}

	private fun findGameStateField()
	{

		val methodN = methods.first { it.newMethodName == "updateGameState" }.methodNode

		val matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(ILOAD, GETSTATIC)).second

		addField("gameState", matches.first())

	}

	private fun findLoginStateField()
	{

		val methodN = methods.first { it.newMethodName == "updateGameState" }.methodNode

		val matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(ICONST_0, PUTSTATIC)).second

		addField("loginState", matches.first())

	}

	private fun findAddNpcToScene()
	{

		val classes = AnalyzerSearching.searchAllClassesForMethod(listOf(ICONST_0, ISTORE), "(Z)V").filter { isMemberStatic(it.first.access) }

		addMethod("addNpcToScene", classes.first().second.name, classes.first().first)

	}

	private fun findNpcFields()
	{

		val methodN = methods.first { it.newMethodName == "addNpcToScene" }.methodNode

		val matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(GETSTATIC)).second

		addField("npcCount", matches[0])
		addField("npcs", matches[1])
		addField("npcIndices", matches[2])

		addField("plane", matches[10])

	}

	private fun findWorldToScreen()
	{

		val classes = AnalyzerSearching.searchAllClassesForMethod(listOf(ICONST_M1, PUTSTATIC), "(III)V").filter { isMemberStatic(it.first.access) }

		addMethod("worldToScreen", classes.first().second.name, classes.first().first)

	}

	private fun findViewportFields()
	{

		val methodN = methods.first { it.newMethodName == "worldToScreen" }.methodNode

		var matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(GETSTATIC)).second

		var filtered = AnalyzerUtils.filterFields(matches, fields)

		addField("cameraX", filtered[0])
		addField("cameraZ", filtered[1])
		addField("cameraY", filtered[2])
		addField("cameraPitch", filtered[4])
		addField("cameraYaw", filtered[6])

		addField("viewportZoom", filtered[7])
		addField("viewportWidth", filtered[8])
		addField("viewportHeight", filtered[9])

		matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(PUTSTATIC)).second
		addField("tempViewportX", matches[0])
		addField("tempViewportY", matches[1])
	}

	private fun findSetViewportShape()
	{

		val classes = AnalyzerSearching.searchAllClassesForMethod(listOf(ILOAD, ICONST_1), "(IIIIZ)V").filter { isMemberStatic(it.first.access) }

		addMethod("viewportShape", classes.first().second.name, classes.first().first)

	}

	private fun findViewportOffsetFields()
	{

		val methodN = methods.first { it.newMethodName == "viewportShape" }.methodNode

		var matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(PUTSTATIC)).second

		addField("viewportOffsetX", matches[1])
		addField("viewportOffsetY", matches[2])

	}

	private fun findLoadRegions()
	{

		val classes = AnalyzerSearching.searchAllClassesForMethod(listOf(ILOAD, PUTSTATIC, GETSTATIC, IFNE, ALOAD), String.format("(ZL%s;)V", getClassAnalyserName(PacketBuffer::class))).filter { isMemberStatic(it.first.access) }

		addMethod("loadRegions", classes.first().second.name, classes.first().first)

	}

	private fun findInstanceFields()
	{

		val methodN = methods.first { it.newMethodName == "loadRegions" }.methodNode

		var matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(GETSTATIC)).second

		addField("isInInstance", matches[0])
		addField("instanceChunkTemplates", matches[7])

	}

	private fun findLoad()
	{

		val methodN = AnalyzerSearching.searchAllClassesForMethod(listOf(DUP, ICONST_4, BIPUSH, BIPUSH), "()V").filter { isMemberStatic(it.first.access) }

		addMethod("load", methodN.first().second.name, methodN.first().first)

	}

	private fun findAddChatBoxMessage()
	{

		val match = AnalyzerSearching.searchAllClassesForMethod(listOf(), "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V").filter { isMemberStatic(it.first.access) }

		addMethod("addChatBoxMessage", match.first().second.name, match.first().first)

	}

	private fun findAddGameMessage()
	{

		val match = AnalyzerSearching.searchAllClassesForMethod(listOf(), "(ILjava/lang/String;Ljava/lang/String;)V").filter { isMemberStatic(it.first.access) }

		addMethod("addGameMessage", match.first().second.name, match.first().first)

	}

	private fun findPromptLoginCredentials()
	{

		val match = AnalyzerSearching.searchAllClassesForMethod(listOf(GETSTATIC, PUTSTATIC, GETSTATIC, PUTSTATIC,
				GETSTATIC, PUTSTATIC, ICONST_2), "(Z)V").filter { isMemberStatic(it.first.access) }

		addMethod("promptLoginCredentials", match.first().second.name, match.first().first)

	}

	private fun findLoginCredentialFields()
	{

		val methodN = methods.first { it.newMethodName == "promptLoginCredentials" }.methodNode

		var matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(PUTSTATIC)).second

		addField("loginIndex", matches[3])
		addField("password", matches[4])

		matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(GETSTATIC)).second

		addField("username", matches[3])

	}

	private fun findUpdateLoginPreferenceType()
	{

		val matches = AnalyzerSearching.searchAllClassesForField2(listOf(GETSTATIC), "(Z)V").filter { isMemberStatic(it.second.access) }

		val clientPreferenceField = getClassAnalyser(ClientPreferences::class)?.fields?.first { it.fieldName == "clientPreferences" }?.fieldNode

		val usernameField = fields.first { it.fieldName == "username" }.fieldNode

		val match = matches.first {
			it.third.count { f -> AsmUtils.insnToField(f) == clientPreferenceField } == 1
					&& it.third.count { f -> AsmUtils.insnToField(f) == usernameField } == 1
		}

		addMethod("updateLoginPreferenceType", match.first.name, match.second)

	}

	private fun findLogout()
	{

		val matches = AnalyzerSearching.searchAllClassesForMethod(
				listOf(GETSTATIC, INVOKEVIRTUAL, INVOKESTATIC, GETSTATIC, INVOKEVIRTUAL), "()V").filter { isMemberStatic(it.first.access) }

		addMethod("logout", matches.first().second.name, matches.first().first)

	}

	private fun findDrawLoggedIn()
	{

		val matches = AnalyzerSearching.searchClassForInts(classNode, listOf(BIPUSH), "()V")

		val match = matches.first {
			res -> res.second.count { it.operand == 100 } > 3
				&& res.second.any { it.operand == 64 }
				&& res.second.count { it.operand == 8 } > 1
		}

		addMethod("drawLoggedIn", match.first)

	}

	private fun findRootWidgetField()
	{

		val methodToSearch = methods.first { it.newMethodName == "drawLoggedIn" }.methodNode

		val matches = AnalyzerSearching.searchMethodForFields(methodToSearch, listOf(GETSTATIC)).second

		addField("rootWidget", matches[0])

	}

	private fun findLoadClassFromDescriptor() {

		val match = AnalyzerSearching.searchAllClassesForMethod(listOf(), "(Ljava/lang/String;)Ljava/lang/Class;").first()

		addMethod("loadClassFromDescriptor", match.second.name, match.first)

	}

	private fun findDraw() {

		val matches = AnalyzerSearching.searchClassForInts(classNode, listOf(GETSTATIC, BIPUSH), "(Z)V")

		val match = matches.first { it.second.any { insn -> insn.operand == 45 } }

		addMethod("draw", match.first)

	}

	private fun findGameDrawingMode() {

		val drawMn = methods.first { it.newMethodName == "drawLoggedIn" }.methodNode

		val matches = AnalyzerSearching.searchMethodForFields(drawMn, listOf(ICONST_3, GETSTATIC)).second
		matches.addAll(AnalyzerSearching.searchMethodForFields(drawMn, listOf(GETSTATIC, ICONST_3)).second)

		addField("gameDrawingMode", matches.first())

	}

	private fun findParseCS2Instruction()
	{

		val match = AnalyzerSearching.searchAllClassesForMethod(listOf(), String.format("(L%s;I)I", getClassAnalyserName(Widget::class))).first()

		addMethod("parseCS2Instruction", match.second.name, match.first)

	}

	private fun findSkillFields()
	{

		val methodN = methods.first { it.newMethodName == "parseCS2Instruction" }.methodNode

		val methodFields = AnalyzerSearching.searchMethodForFields(methodN, listOf(GETSTATIC)).second

		addField("currentLevels", methodFields[0])
		addField("levels", methodFields[1])
		addField("experience", methodFields[2])
		addField("experienceTable", methodFields[5])
		addField("enabledSkills", methodFields[9])
		addField("runEnergy", methodFields[12])
		addField("weight", methodFields[13])
	}

	private fun findColorTagFromDecimal()
	{

		val matches = AnalyzerSearching.searchAllClassesForConstants("(I)Ljava/lang/String;")

		val match = matches.first { match -> match.third.any { insn -> insn.cst == "<col=" } }

		addMethodToClient("colorTagFromDecimal", match.first.name, match.second)

	}

	override fun getFields()
	{
		findLoad()
		getClientInstance()
		findAddPlayerToScene()
		findAddMenuOptions()
		findDoCycles()
		findCycleTickField()
		findUpdateGameState()
		findGameStateField()
		findLoginStateField()
		findAddNpcToScene()
		findNpcFields()
		findWorldToScreen()
		findViewportFields()
		findSetViewportShape()
		findViewportOffsetFields()
		findLoadRegions()
		findInstanceFields()
		findAddChatBoxMessage()
		findAddGameMessage()
		findPromptLoginCredentials()
		findLoginCredentialFields()
		findUpdateLoginPreferenceType()
		findLogout()
		findDrawLoggedIn()
		findRootWidgetField()
		findLoadClassFromDescriptor()
		findDraw()
		findGameDrawingMode()
		findParseCS2Instruction()
		findSkillFields()
		findColorTagFromDecimal()
	}

}