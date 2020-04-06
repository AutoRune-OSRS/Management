package io.autorune.updater.analyzer.classes.implementations

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode

@CorrectFieldCount(12)
@CorrectMethodCount(3)
class MenuAction : ClassAnalyzer() {

    override fun findClassNode(): ClassNode? {
        for (classNode in classPool) {

            val match = classNode.fields.count { !isMemberStatic(it.access) } == 5
                    && classNode.fields.count { it.desc == descInt && !isMemberStatic(it.access) } == 4
                    && classNode.fields.count { it.desc == descString && !isMemberStatic(it.access) } == 1

            if (match)
                return classNode

        }

        return null
    }

    private fun findMenuAction()
    {

        val match = AnalyzerSearching.searchAllClassesForMethod(listOf(),
                "(IIIILjava/lang/String;Ljava/lang/String;II)V").filter { isMemberStatic(it.first.access) }

        addMethodToClient("sendMenuAction", match.first().second.name, match.first().first)

    }

    private fun findInsertMenuAction()
    {

        val match = AnalyzerSearching.searchAllClassesForMethod(listOf(),
                "(Ljava/lang/String;Ljava/lang/String;IIIIZ)V").filter { isMemberStatic(it.first.access) }

        addMethodToClient("insertMenuAction", match.first().second.name, match.first().first)

    }

    private fun findMenuActionsFields()
    {

        val methodN = methods.first { it.newMethodName == "insertMenuAction" }.methodNode

        val fieldMatch = AnalyzerSearching.searchMethodForFields(methodN, listOf(GETSTATIC)).second

        addFieldToClient("contextMenuOpen", fieldMatch[0])

        addFieldToClient("menuOptionsCount", fieldMatch[1])

        addFieldToClient("menuActions", fieldMatch[2])

    }

    private fun findContextMenuBounds()
    {

        val methodN = AnalyzerSearching.searchAllClassesForMethod(listOf(IINC, GETSTATIC, ICONST_1, ISUB), "()V").filter { isMemberStatic(it.first.access) }

        addMethodToClient("constructContextMenuBounds", methodN.first().second.name, methodN.first().first)

    }

    private fun findContextMenuFields()
    {

        val methodN = methods.first { it.newMethodName == "constructContextMenuBounds" }.methodNode

        var matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(PUTSTATIC)).second

        addFieldToClient("menuX", matches[1])
        addFieldToClient("menuY", matches[2])
        addFieldToClient("menuWidth", matches[3])
        addFieldToClient("menuHeight", matches[4])

        matches = AnalyzerSearching.searchMethodForFields(methodN, listOf(GETSTATIC)).second

        addFieldToClient("menuTargets", matches[6])
        addFieldToClient("menuOpcodes", matches[8])
        addFieldToClient("menuIdentifiers", matches[10])
        addFieldToClient("menuArguments1", matches[12])
        addFieldToClient("menuArguments2", matches[14])

    }

    override fun getFields()
    {

        findMenuAction()

        findInsertMenuAction()

        findMenuActionsFields()

        findContextMenuBounds()

        findContextMenuFields()

    }


}