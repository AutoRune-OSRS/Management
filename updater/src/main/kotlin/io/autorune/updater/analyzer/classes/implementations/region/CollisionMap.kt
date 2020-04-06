package io.autorune.updater.analyzer.classes.implementations.region

import io.autorune.updater.analyzer.classes.ClassAnalyzer
import io.autorune.updater.analyzer.classes.annotations.CorrectFieldCount
import io.autorune.updater.analyzer.classes.annotations.CorrectMethodCount
import io.autorune.updater.analyzer.util.AnalyzerSearching
import org.objectweb.asm.tree.ClassNode
import java.lang.Exception

@CorrectFieldCount(1)
@CorrectMethodCount(5)
class CollisionMap : ClassAnalyzer() {//fv

    override fun findClassNode(): ClassNode? {

        for (classNode in classPool) {

            if (classNode.superName != "java/lang/Object" || classNode.interfaces.isNotEmpty())
                continue

            val match = classNode.fields.count { it.desc == descInt && !isMemberStatic(it.access) } == 4
                    && classNode.fields.count { it.desc == descIntArrArr && !isMemberStatic(it.access) } == 1

            if (!match)
                continue

            return classNode

        }
        return null
    }

    private fun setFlagOff()
    {

        val matches = AnalyzerSearching.searchClassForMethod(classNode, listOf(IAND), "(III)V")

        if(!isMemberStatic(matches[0].first.access))
            addMethod("setFlagOff", matches[0].first)
        else
            throw Exception()

    }

    private fun setFlagOffNonSquare()
    {

        val match = classNode.methods.first { it.desc == "(IIIIIZ)V" }

        if(!isMemberStatic(match.access))
            addMethod("setFlagOffNonSquare", match)
        else
            throw Exception()

    }

    /*private fun setBlockedByFloor()
    {

        val matches = classNode.methods.filter { it.desc == "(II)V" }

        for (match in matches) {
            val result = AnalyzerSearching.searchMethodForConstants(match, listOf(LDC))
            if (!result.first)
                continue
            val ldc = result.second[0]
            if (ldc.cst is Int && ldc.cst == 2097152) {
                addMethod("setBlockedByFloor", match)
                return
            }
        }

        throw Exception()

    }

    private fun setBlockedByFloorDecoration()
    {

        val matches = classNode.methods.filter { it.desc == "(II)V" }

        for (match in matches) {
            val result = AnalyzerSearching.searchMethodForConstants(match, listOf(LDC))
            if (!result.first)
                continue
            val ldc = result.second[0]
            if (ldc.cst is Int && ldc.cst == 262144) {
                addMethod("setBlockedByFloorDecoration", match)
                return
            }
        }

        throw Exception()

    }

    private fun setNotBlockedByFloorDecoration()
    {

        val matches = classNode.methods.filter { it.desc == "(II)V" }

        for (match in matches) {
            val result = AnalyzerSearching.searchMethodForConstants(match, listOf(LDC))
            if (!result.first)
                continue
            val ldc = result.second[0]
            if (ldc.cst is Int && ldc.cst == -262145) {
                addMethod("setNotBlockedByFloorDecoration", match)
                return
            }
        }

        throw Exception()

    }

    private fun addGameObject()
    {

        val matches = classNode.methods.filter { it.desc == "(IIIIZ)V" }

        for (match in matches) {
            val result = AnalyzerSearching.searchMethodForConstants(match, listOf(LDC))
            if (!result.first)
                continue
            val ldc = result.second[0]
            if (ldc.cst is Int && ldc.cst == 131072) {
                addMethod("addGameObject", match)
                return
            }
        }

        throw Exception()

    }

    private fun getClear()
    {

        val matches = classNode.methods.filter { it.desc == "()V" }

        for (match in matches) {
            val result = AnalyzerSearching.searchMethodForConstants(match, listOf(LDC))
            if (!result.first)
                continue
            val ldc = result.second[0]
            if (ldc.cst is Int && ldc.cst == 16777216) {
                addMethod("clear", match)
                return
            }
        }

        throw Exception()

    }*/

    private fun setFlag()
    {

        val matches = AnalyzerSearching.searchClassForMethod(classNode, listOf(IOR), "(III)V")

        if(!isMemberStatic(matches[0].first.access))
            addMethod("setFlag", matches[0].first)
        else
            throw Exception()

    }

    private fun addWall()
    {

        val matches = classNode.methods.filter { it.desc == "(IIIIZ)V" }

        for (match in matches) {
            val result = AnalyzerSearching.searchMethodForMethodCall(match, listOf(INVOKEVIRTUAL))
            if (!result.first)
                continue
            val mInsn = result.second[0]
            if (mInsn.name == methods.first { it.newMethodName == "setFlag" }.methodNode.name) {
                addMethod("addWall", match)
                return
            }
        }

        throw Exception()

    }

    private fun removeWall()
    {

        val matches = classNode.methods.filter { it.desc == "(IIIIZ)V" }

        for (match in matches) {
            val result = AnalyzerSearching.searchMethodForMethodCall(match, listOf(INVOKEVIRTUAL))
            if (!result.first)
                continue
            val mInsn = result.second[0]
            if (mInsn.name == methods.first { it.newMethodName == "setFlagOff" }.methodNode.name) {
                addMethod("removeWall", match)
                return
            }
        }

        throw Exception()

    }

    /*private fun hookFields()
    {

        var mn = methods.first { it.newMethodName == "addGameObject" }.methodNode

        var matches = AnalyzerSearching.searchMethodForFields(mn, listOf(GETFIELD)).second

        addField("xInset", matches[0])
        addField("yInset", matches[1])
        addField("xSize", matches[2])
        addField("ySize", matches[3])

        mn = methods.first { it.newMethodName == "setFlag" }.methodNode

        matches = AnalyzerSearching.searchMethodForFields(mn, listOf(GETFIELD)).second

        addField("flags", matches[0])

    }*/

    private fun getCollisionMapArray() {
        for (cN in classPool) {
            val field = cN.fields.find { it.desc == String.format("[L%s;", classNode.name) && isMemberStatic(it.access) }
            if (field != null) {
                addFieldToClient("collisionMaps", cN.name, field)
                return
            }
        }
        throw Exception()
    }

    override fun getFields() {

        //TODO: Fix these.
        /*setBlockedByFloor()
        setBlockedByFloorDecoration()
        setNotBlockedByFloorDecoration()
        addGameObject()
        getClear()
        hookFields()*/
        setFlag()
        setFlagOff()
        setFlagOffNonSquare()
        addWall()
        removeWall()
        getCollisionMapArray()

    }

}