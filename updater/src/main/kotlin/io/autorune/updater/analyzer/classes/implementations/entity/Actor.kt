package io.autorune.updater.analyzer.classes.implementations.entity

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.Client
import io.autorune.updater.analyzer.classes.implementations.io.PacketBuffer
import io.autorune.updater.analyzer.util.AnalyzerSearching
import io.autorune.updater.analyzer.classes.util.AnalyzerUtils
import org.objectweb.asm.tree.ClassNode
import java.lang.reflect.Modifier

@CorrectFieldCount(18)
@CorrectMethodCount(7)
@DependsOn(Entity::class, Client::class, PacketBuffer::class)
class Actor : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            if (!Modifier.isAbstract(classNode.access))
                continue

            if (classNode.superName != getClassAnalyser(Entity::class)?.classNode?.name)
                continue

            return classNode

        }

        return null
    }

	private fun findIsVisible()
    {

        val match = classNode.methods.first { it.desc == "()Z" && !isMemberStatic(it.access) }

        addMethod("isVisible", match)

    }

	private fun findAddHitSplat()
    {

		val matches = AnalyzerSearching.searchClassForMethod(classNode, listOf(IREM), "(IIIIII)V")

	    addMethod("addHitSplat", matches[0].first)

    }

	private fun findAddHealthBar()
	{

		val hitSplatMn = methods.first { it.newMethodName == "addHitSplat" }.methodNode

		val match = classNode.methods.first { it.desc == "(IIIIII)V" && !isMemberStatic(it.access) && it.name != hitSplatMn.name }

		addMethod("addHealthBar", match)

	}

	private fun findHealthFields()
	{

		val hitSplatMn = methods.first { it.newMethodName == "addHitSplat"}.methodNode

		val matches = AnalyzerSearching.searchMethodForFieldsInCodeOrder(hitSplatMn, GETFIELD)


		addField("hitSplatTypes", matches[9])
		addField("hitSplatValues", matches[10])
		addField("hitSplatTypes2", matches[11])
		addField("hitSplatValues2", matches[12])
		addField("hitSplatCycles", matches[13])

		addField("hitSplatCount", matches[7])

	}

	private fun findRemoveHealthBar()
	{

		val match = classNode.methods.first { !isMemberStatic(it.access) && it.desc == "(I)V" }

		addMethod("removeHealthBar", match)

	}

	private fun findHealthBarsField()
	{

		val removeMn = methods.first { it.newMethodName == "removeHealthBar" }.methodNode

		val match = AnalyzerSearching.searchMethodForFields(removeMn, listOf(GETFIELD)).second

		addField("healthBars", match[0])

	}

	private fun findPathTraversedField()
	{

		val field = classNode.fields.first { it.desc == descByteArr && !isMemberStatic(it.access) }

		addField("pathTraversed", field)

	}

	private fun findIsWalking()
	{

		val methodNode = getClassAnalyser(Client::class)?.methods?.first { it.newMethodName == "addPlayerToScene" }?.methodNode ?: return

		val matches = AnalyzerSearching.searchMethodForFields(methodNode, listOf(LLOAD, ALOAD, GETFIELD)).second

		addField("isWalking", matches[1])

	}

	private fun findSelectSpell()
	{

		val matches = AnalyzerSearching.searchAllClassesForMethod(
				listOf(NEW, DUP, INVOKESPECIAL, ASTORE, ALOAD, ALOAD, PUTFIELD, ALOAD, ALOAD, GETFIELD, PUTFIELD, ALOAD, INVOKESTATIC), "(IIII)V")

		addMethodToClient("selectSpell", matches.first().second.name, matches.first().first)

	}

	private fun selectedSpellFlags()//TODO: Fix
	{

		val methodN = methods.first { it.newMethodName == "selectSpell" }.methodNode

		val matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(ILOAD, PUTSTATIC, ALOAD)).second

		addFieldToClient("selectedSpellFlags", matches.first())

	}

	private fun findTargetIndexField()
	{

		val methodN = methods.first { it.newMethodName == "readPlayerUpdate" }.methodNode

		val matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(GETFIELD)).second

		val filtered = AnalyzerUtils.filterFields(matches, fields)

		addField("targetIndex", filtered[2])

	}

	private fun findPositionFields()
	{

		val matches = AnalyzerSearching.searchAllClassesForField(listOf(GETSTATIC, GETFIELD, PUTSTATIC, GETSTATIC,
				GETFIELD, GETSTATIC, GETFIELD, GETSTATIC, INVOKESTATIC, GETSTATIC, ISUB, ISTORE), "()V")

		addField("x", matches[4])
		addField("y", matches[6])

	}

	private fun findBaseFields()
	{

		val methodN = methods.first { it.newMethodName == "readPlayerUpdate"}.methodNode

		var matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(GETSTATIC)).second

		addFieldToClient("baseY", matches[5])
		addFieldToClient("baseX", matches[6])

		matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(GETFIELD)).second

	}

	private fun findPathXYFields()
	{

		val methodN = methods.first { it.newMethodName == "readPlayerUpdate"}.methodNode

		val matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(ALOAD, GETFIELD, ICONST_0)).second

		addField("pathY", matches[3])
		addField("pathX", matches[4])

	}

	private fun findReadPlayerUpdate()
	{

		val match = AnalyzerSearching.searchAllClassesForMethod(listOf(ALOAD, ICONST_1), String.format("(L%s;I)V", getClassAnalyserName(PacketBuffer::class))).filter { isMemberStatic(it.first.access) }

		addMethodToClient("readPlayerUpdate", match.first().second.name, match.first().first)

	}

	private fun findSyncActorOrientationAndAngle()
	{

		val matches = AnalyzerSearching.searchAllClassesForMethod(listOf(), String.format("(L%s;)V", classNode.name))

		matches.sortBy { it.first.instructions.size() }

		addMethodToClient("syncActorOrientationAndAngle", matches[1].second.name, matches[1].first)

	}

	private fun findOrientation()
	{

		val mN = methods.first { it.newMethodName == "syncActorOrientationAndAngle" }.methodNode

		val matches1 = AnalyzerSearching.searchMethodForFields(mN, listOf(GETFIELD)).second
		val matches2 = AnalyzerSearching.searchMethodForFields(mN, listOf(PUTFIELD)).second

		val match = matches1.first { matches2.any { pot -> pot.name == it.name && pot.owner == it.owner && pot.desc == it.desc } }

		addField("orientation", match)

	}

    override fun getFields()
    {
		findReadPlayerUpdate()
        findIsVisible()
	    findAddHitSplat()
		findAddHealthBar()
	    findRemoveHealthBar()
		findHealthFields()
		findHealthBarsField()
		findPathTraversedField()
	    findIsWalking()
	    findSelectSpell()
	    selectedSpellFlags()
		findPositionFields()
	    findPathXYFields()
	    findBaseFields()
	    findSyncActorOrientationAndAngle()
	    findOrientation()
	    findTargetIndexField()
    }


}