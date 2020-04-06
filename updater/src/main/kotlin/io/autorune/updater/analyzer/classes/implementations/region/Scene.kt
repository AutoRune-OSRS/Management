package io.autorune.updater.analyzer.classes.implementations.region

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.*
import io.autorune.updater.analyzer.classes.implementations.entity.Entity
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode
import java.lang.Exception

@CorrectFieldCount(6)
@CorrectMethodCount(5)
@DependsOn(Entity::class)
class Scene : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            val match = classNode.fields.count { it.desc == descBool4D && isMemberStatic(it.access) } == 1

            if (match)
                return classNode

        }
        return null
    }

    private fun getCurrentRegion() {
        for (cN in classPool) {
            val field = cN.fields.find { it.desc == String.format("L%s;", classNode.name) && isMemberStatic(it.access) }
            if (field != null) {
                addFieldToClient("currentScene", cN.name, field)
                return
            }
        }
        throw Exception()
    }

    private fun findTileOccluded()
    {

        val match = AnalyzerSearching.searchClassForMethod(classNode, listOf(INEG, IASTORE), "(III)Z").first().first

        addMethod("isTileOccluded", match)

    }

    private fun findTileFields()
    {

        val methodN = methods.first { it.newMethodName == "isTileOccluded" }.methodNode

        var matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(GETFIELD)).second

        addField("tileDrawCounts", matches[0])

        addField("tileHeights", matches[1])

        matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(GETSTATIC)).second

        addField("sceneDrawCount", matches[0])

    }

    private fun findAddPendingSpawnToScene()
    {

        val methodN = AnalyzerSearching.searchAllClassesForMethod(listOf(LCONST_0, LSTORE, ICONST_M1, ISTORE), "(IIIIIII)V")

        addMethodToClient("addPendingSpawnToScene", classNode.name, methodN.first().first)

    }

    private fun findTileSettings()
    {

        val methodN = methods.first { it.newMethodName == "addPendingSpawnToScene" }.methodNode

        val matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(GETSTATIC)).second

        addFieldToClient("tileSettings", matches[14])

    }

    private fun findLinkBelow()
    {

        val methodN = classNode.methods.first { it.desc == "(II)V" && !isMemberStatic(it.access) }

        addMethod("setLinkBelow", methodN)

    }

    private fun findTilesField()
    {

        val methodN = methods.first { it.newMethodName == "setLinkBelow" }.methodNode

        val match = AnalyzerSearching.searchMethodForFields(methodN, listOf(GETFIELD)).second

        addField("tiles", match[0])

    }

    private fun findNewGroundItemPile()
    {

        val methodN = classNode.methods.first { it.desc == String.format("(IIIIL%s;JL%s;L%s;)V",
                getClassAnalyserName(Entity::class), getClassAnalyserName(Entity::class), getClassAnalyserName(Entity::class)) }

        addMethod("newGroundItemPile", methodN)

    }

    private fun findAddInteractiveObject()
    {

        val methodN = classNode.methods.first { it.desc == String.format("(IIIIIIIIL%s;IZJI)Z", getClassAnalyserName(Entity::class)) }

        addMethod("addInteractiveObject", methodN)

    }

    override fun getFields() {

        getCurrentRegion()

        findTileOccluded()

        findTileFields()

        findAddPendingSpawnToScene()
        findTileSettings()

        findLinkBelow()

        findTilesField()

        findNewGroundItemPile()

        findAddInteractiveObject()

    }

}