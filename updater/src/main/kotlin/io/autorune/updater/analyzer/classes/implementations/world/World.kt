package io.autorune.updater.analyzer.classes.implementations.world

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode
import java.lang.Exception

@CorrectFieldCount(3)
@CorrectMethodCount(3)
class World : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            if (classNode.interfaces.isNotEmpty() || classNode.superName != "java/lang/Object")
                continue

            val match = classNode.fields.count { it.desc == descInt && !isMemberStatic(it.access) } == 5
                    && classNode.fields.count { it.desc == descString && !isMemberStatic(it.access) } == 2

            if (match)
                return classNode

        }

        return null
    }

    private fun getWorlds() {
        for (cN in classPool) {
            val field = cN.fields.find { it.desc == String.format("[L%s;", classNode.name) && isMemberStatic(it.access) }
            if (field != null) {
                addFieldToClient("worlds", cN.name, field)
                return
            }
        }
        throw Exception()
    }

    private fun findChangeWorld()
    {

        val match = AnalyzerSearching.searchAllClassesForMethod(
                listOf(ALOAD, INVOKEVIRTUAL, PUTSTATIC, ALOAD, INVOKEVIRTUAL, INVOKESTATIC), String.format("(L%s;)V", getClassAnalyserName(World::class))).filter { isMemberStatic(it.first.access) }

        addMethodToClient("changeWorld", match.first().second.name, match.first().first)

    }

    private fun findId()
    {

        val methodN = methods.first { it.newMethodName == "changeWorld" }.methodNode

        val matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(GETFIELD)).second

        addField("id", matches[1])

    }

    private fun findFetchWorldList()
    {

        val match = AnalyzerSearching.searchAllClassesForMethod(
                listOf(ICONST_0, GETSTATIC, ARRAYLENGTH, ICONST_1, ISUB), "()Z").filter { isMemberStatic(it.first.access) }

        addMethodToClient("fetchWorldList", match.first().second.name, match.first().first)

    }

    private fun findWorldRequestField()
    {

        val methodN = methods.first { it.newMethodName == "fetchWorldList" }.methodNode

        val matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(GETSTATIC)).second

        addFieldToClient("worldRequest", matches[0])

    }

    private fun findOpenWorldSelect()
    {

        val fetchWorldListMethod = methods.first { it.newMethodName == "fetchWorldList" }

        val match = AnalyzerSearching.searchAllClassesForMethodCall(listOf(INVOKESTATIC), "()V")
                .first {
                    match -> match.third.any { it.owner == fetchWorldListMethod.owner
                                             && it.name == fetchWorldListMethod.methodNode.name
                                             && it.desc == fetchWorldListMethod.methodNode.desc }
                            && isMemberStatic(match.second.access)
                }

        addMethodToClient("openWorldSelect", match.first.name, match.second)

    }

    override fun getFields() {

        getWorlds()

        findChangeWorld()

        findId()

        findFetchWorldList()

        findWorldRequestField()

        findOpenWorldSelect()

    }


}