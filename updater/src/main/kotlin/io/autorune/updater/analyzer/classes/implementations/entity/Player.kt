package io.autorune.updater.analyzer.classes.implementations.entity

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.classes.annotations.DependsOn
import io.autorune.updater.analyzer.classes.implementations.io.PacketBuffer
import io.autorune.updater.analyzer.classes.implementations.io.RSByteBuffer
import io.autorune.updater.analyzer.classes.implementations.user.Username
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(8)
@CorrectMethodCount(2)
@DependsOn(Actor::class, Username::class, PacketBuffer::class, RSByteBuffer::class)
class Player : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.superName != getClassAnalyserName(Actor::class))
                continue

            val match = classNode.fields.any { it.desc == String.format("L%s;", getClassAnalyserName(Username::class)) }

            if (match)
                return classNode

        }
        return null
    }

    private fun getPlayerArray() {
        for (cN in classPool) {
            val field = cN.fields.find { it.desc == String.format("[L%s;", classNode.name) && isMemberStatic(it.access) }
            if (field != null) {
                addFieldToClient("players", cN.name, field)
                break
            }
        }
    }

    private fun getLocalPlayer() {
        for (cN in classPool) {
            val field = cN.fields.find { it.desc == String.format("L%s;", classNode.name) && isMemberStatic(it.access) }
            if (field != null) {
                addFieldToClient("localPlayer", cN.name, field)
                break
            }
        }
    }

    private fun findLocalPlayerIndexField()
    {

        val clazz = getClassAnalyser(Actor::class) ?: return

        val methodN = clazz.methods.first { it.newMethodName == "readPlayerUpdate" }.methodNode

        val matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(PUTFIELD, GETSTATIC, ILOAD)).second

        addFieldToClient("localPlayerIndex", matches[1])

    }

    private fun findUpdatePlayers()
    {

        val matches = AnalyzerSearching.searchAllClassesForMethod(
                listOf(BALOAD), String.format("(L%s;I)V", getClassAnalyserName(PacketBuffer::class), classNode.name)).filter { isMemberStatic(it.first.access) }

        addMethodToClient("updatePlayers", matches[0].second.name, matches[0].first)

    }

    private fun findPlaneField()
    {

        val methodN = getClassAnalyser(Actor::class)?.methods?.first { it.newMethodName == "readPlayerUpdate" }?.methodNode ?: return

        val matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(GETFIELD)).second.filter { it.desc == "I" }

        addField("plane", matches[0])

    }

    fun findTileCoordinateFields()
    {

        val methodN = getClassAnalyser(Actor::class)?.methods?.first { it.newMethodName == "readPlayerUpdate" }?.methodNode ?: return

        val matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(ILOAD, PUTFIELD)).second

        addField("tileX", matches[6])
        addField("tileY", matches[7])

    }

    private fun findReadPlayerAppearanceUpdate() {

        val match = classNode.methods.first { !isMemberStatic(it.access) && it.desc == String.format("(L%s;)V", getClassAnalyserName(RSByteBuffer::class)) }

        addMethod("readPlayerAppearanceUpdate", match)

    }

    private fun findUsernameAndCombat() {

        val methodToSearch = methods.first { it.newMethodName == "readPlayerAppearanceUpdate" }.methodNode

        val matches = AnalyzerSearching.searchMethodForFields(methodToSearch, listOf(PUTFIELD)).second

        addField("username", matches[20])

        addField("combatLevel", matches[21])

    }

    override fun getFields() {

        getPlayerArray()
        getLocalPlayer()

        findLocalPlayerIndexField()

        findUpdatePlayers()

        findPlaneField()
        findTileCoordinateFields()

        findReadPlayerAppearanceUpdate()
        findUsernameAndCombat()

    }

}