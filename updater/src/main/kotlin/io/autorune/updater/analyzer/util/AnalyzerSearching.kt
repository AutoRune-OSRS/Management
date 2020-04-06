package io.autorune.updater.analyzer.util

import io.autorune.updater.analyzer.classes.repo.ClassRepository
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.*


object AnalyzerSearching {

    fun searchAllClassesForConstants(methodDesc: String): MutableList<Triple<ClassNode, MethodNode, MutableList<LdcInsnNode>>> {

        val matches = mutableListOf<Triple<ClassNode, MethodNode, MutableList<LdcInsnNode>>>()

        ClassRepository.classPool.forEach {
            cN -> matches.addAll(searchClassForConstants(cN, listOf(Opcodes.LDC), methodDesc))
        }

        return matches

    }

    fun searchAllClassesForInts(query: List<Int>, methodDesc: String? = null): MutableList<Triple<ClassNode, MethodNode, MutableList<IntInsnNode>>> {

        val matches = mutableListOf<Triple<ClassNode, MethodNode, MutableList<IntInsnNode>>>()

        for (classNode in ClassRepository.classPool) {
            matches.addAll(
                    searchClassForInts(classNode, query, methodDesc).map { match -> Triple(classNode, match.first, match.second) }
            )
        }

        return matches

    }

    fun searchAllClassesForField(query: List<Int>, methodDesc: String? = null): MutableList<FieldInsnNode> {

        val matches = mutableListOf<FieldInsnNode>()

        for (classNode in ClassRepository.classPool) {
            matches.addAll(searchClassForField(classNode, query, methodDesc))
        }

        return matches

    }

    fun searchAllClassesForField2(query: List<Int>, methodDesc: String? = null): MutableList<Triple<ClassNode, MethodNode, MutableList<FieldInsnNode>>> {

        val matches = mutableListOf<Triple<ClassNode, MethodNode, MutableList<FieldInsnNode>>>()

        for (classNode in ClassRepository.classPool) {
            matches.addAll(searchClassForField2(classNode, query, methodDesc).map { Triple(classNode, it.first, it.second) })
        }

        return matches

    }

    fun searchAllClassesForMethodCall(query: List<Int>, methodDesc: String? = null): MutableList<Triple<ClassNode, MethodNode, MutableList<MethodInsnNode>>> {

        val matches = mutableListOf<Triple<ClassNode, MethodNode, MutableList<MethodInsnNode>>>()

        for (classNode in ClassRepository.classPool) {
            matches.addAll(searchClassForMethodCall(classNode, query, methodDesc))
        }

        return matches

    }

    fun searchClassForField(classNode: ClassNode, query: List<Int>, methodDesc: String? = null): MutableList<FieldInsnNode> {

        val matches = mutableListOf<FieldInsnNode>()

        var methodMatch = false

        for (methodNode in classNode.methods) {
            if (methodDesc != null && methodDesc != methodNode.desc)
                continue
            val result = searchMethodForFields(methodNode, query)
            methodMatch = result.first
            matches.addAll(result.second)
        }

        if (methodMatch && matches.size == 0)
            matches.add(FieldInsnNode(0, "", "", ""))

        return matches

    }

    fun searchClassForField2(classNode: ClassNode, query: List<Int>, methodDesc: String? = null): MutableList<Pair<MethodNode, MutableList<FieldInsnNode>>> {

        val matches = mutableListOf<Pair<MethodNode, MutableList<FieldInsnNode>>>()

        for (methodNode in classNode.methods) {
            if (methodDesc != null && methodDesc != methodNode.desc)
                continue
            val result = searchMethodForFields(methodNode, query)
            if (result.first)
                matches.add(Pair(methodNode, result.second))
        }

        return matches

    }

    fun searchClassForMethodCall(classNode: ClassNode, query: List<Int>, methodDesc: String? = null): MutableList<Triple<ClassNode, MethodNode, MutableList<MethodInsnNode>>> {

        val matches = mutableListOf<Triple<ClassNode, MethodNode, MutableList<MethodInsnNode>>>()

        var methodMatch = false

        for (methodNode in classNode.methods) {
            if (methodDesc != null && methodDesc != methodNode.desc)
                continue
            val result = searchMethodForMethodCall(methodNode, query)
            methodMatch = result.first
            matches.add(Triple(classNode, methodNode, result.second))
        }

        return matches

    }

    fun searchClassForConstants(classNode: ClassNode, query: List<Int>, methodDesc: String? = null): MutableList<Triple<ClassNode, MethodNode, MutableList<LdcInsnNode>>> {

        val matches = mutableListOf<Triple<ClassNode, MethodNode, MutableList<LdcInsnNode>>>()

        for (methodNode in classNode.methods) {
            if (methodDesc != null && methodDesc != methodNode.desc)
                continue
            val result = searchMethodForConstants(methodNode, query)
            matches.add(Triple(classNode, methodNode, result.second))
        }

        return matches

    }

    fun searchClassForInts(classNode: ClassNode, query: List<Int>, methodDesc: String? = null): MutableList<Pair<MethodNode, MutableList<IntInsnNode>>> {

        val matches = mutableListOf<Pair<MethodNode, MutableList<IntInsnNode>>>()

        for (methodNode in classNode.methods) {
            if (methodDesc != null && methodDesc != methodNode.desc)
                continue
            val result = searchMethodForInts(methodNode, query)
            matches.add(Pair(methodNode, result.second))
        }

        return matches

    }

    fun searchAllClassesForMethod(query: List<Int>, methodDesc: String? = null): MutableList<Pair<MethodNode, ClassNode>> {

        val matches = mutableListOf<Pair<MethodNode, ClassNode>>()

        for (classNode in ClassRepository.classPool) {
            matches.addAll(searchClassForMethod(classNode, query, methodDesc))
        }

        return matches

    }

    fun searchClassForMethod(classNode: ClassNode, query: List<Int>, methodDesc: String? = null): MutableList<Pair<MethodNode, ClassNode>> {

        val matches = mutableListOf<Pair<MethodNode, ClassNode>>()

        for (methodNode in classNode.methods) {
            if (methodDesc != null && methodDesc != methodNode.desc)
                continue
            val result = searchMethodForFields(methodNode, query)
            if (result.first || query.isEmpty())
                matches.add(Pair(methodNode, classNode))
        }

        return matches

    }

    fun searchClassForMethod(classNode: ClassNode, methodDesc: String): MutableList<MethodNode>
    {

        val matches = mutableListOf<MethodNode>()

        for (methodNode in classNode.methods) {
            if (methodNode.desc.contains(methodDesc)) {
                matches.add(methodNode)
            }
        }

        return matches
    }


    fun searchMethodForFields(methodNode: MethodNode, query: List<Int>): Pair<Boolean, MutableList<FieldInsnNode>> {

        var matchIndex = 0
        var tentativeMatches = 0

        val matches = mutableListOf<FieldInsnNode>()
        var methodMatch = false;

        if (query.isEmpty())
            return Pair(methodMatch, matches)

        var nextNodeNeeded = false

        val instructions = methodNode.instructions;

        var instrNode = instructions.first

        while (instrNode != null) {

            if (matchIndex == query.size){
                matchIndex = 0
                tentativeMatches = 0
                methodMatch = true;
            }

            if (instrNode.opcode == query[matchIndex] || query[matchIndex] == -1) {
                if (instrNode is FieldInsnNode) {
                    matches.add(instrNode)
                    tentativeMatches++
                }
                matchIndex++
                nextNodeNeeded = true
            } else {
                if (matchIndex == 0)
                    nextNodeNeeded = true
                matches.removeIf { matches.indexOf(it) >= (matches.size - tentativeMatches) }
                tentativeMatches = 0
                matchIndex = 0
            }

            if (nextNodeNeeded)
                instrNode = instrNode.next

            nextNodeNeeded = false

        }

        return Pair(methodMatch, matches)

    }

    fun searchMethodForFieldsInCodeOrder(methodNode: MethodNode, opcode: Int): MutableList<FieldInsnNode> {

        assert(opcode == Opcodes.GETFIELD || opcode == Opcodes.PUTFIELD)

        val matches = mutableListOf<FieldInsnNode>()

        val instructions = methodNode.instructions;

        var instrNode = instructions.first

        var pendingJump: JumpInsnNode? = null
        var pendingPendingJump: JumpInsnNode? = null

        var previousLabel: LabelNode? = null
        var currentLabel: LabelNode? = null

        while (instrNode != null) {

            if (instrNode.opcode == Opcodes.RETURN) {

                if (pendingJump == null)
                {
                    if (pendingPendingJump == null)
                        break

                    instrNode = pendingPendingJump.label.next
                    pendingPendingJump = null
                    continue

                }

                instrNode = pendingJump.label.next
                pendingJump = null
                continue

            }


            if (instrNode is LabelNode)
            {
                previousLabel = currentLabel
                currentLabel = instrNode
            }

            if (instrNode.opcode == opcode) {
                if (instrNode is FieldInsnNode) {
                    matches.add(instrNode)
                }
            }

            if (instrNode is JumpInsnNode) {
                if (instrNode.opcode == Opcodes.GOTO
                    || (instrNode.next is LabelNode && (instrNode.previous.opcode == Opcodes.ILOAD || instrNode.previous.previous.opcode == Opcodes.ILOAD))) {

                    if ((instrNode.label.next.opcode == Opcodes.RETURN || instrNode.label == currentLabel || instrNode.label == previousLabel) && pendingJump != null)
                    {
                        instrNode = pendingJump.label.next
                        pendingJump = null
                    } else
                    {
                        if (instrNode.next is LabelNode && instrNode.opcode != Opcodes.GOTO) {
                            pendingPendingJump = pendingJump
                            pendingJump = null
                        }
                        instrNode = instrNode.label.next
                    }

                } else {
                    if (pendingJump == null)
                        pendingJump = instrNode
                    instrNode = instrNode.next
                }
            } else
                instrNode = instrNode.next

        }

        return matches

    }

    fun searchMethodForConstants(methodNode: MethodNode, query: List<Int>): Pair<Boolean, MutableList<LdcInsnNode>> {

        var matchIndex = 0
        var tentativeMatches = 0

        val matches = mutableListOf<LdcInsnNode>()
        var methodMatch = false;

        var nextNodeNeeded = false

        val instructions = methodNode.instructions;

        var instrNode = instructions.first

        while (instrNode != null) {

            if (matchIndex == query.size){
                matchIndex = 0
                tentativeMatches = 0
                methodMatch = true;
            }

            if (instrNode.opcode == query[matchIndex] || query[matchIndex] == -1) {
                if (instrNode is LdcInsnNode) {
                    matches.add(instrNode)
                    tentativeMatches++
                }
                matchIndex++
                nextNodeNeeded = true
            } else {
                if (matchIndex == 0)
                    nextNodeNeeded = true
                matches.removeIf { matches.indexOf(it) >= (matches.size - tentativeMatches) }
                tentativeMatches = 0
                matchIndex = 0
            }

            if (nextNodeNeeded)
                instrNode = instrNode.next

            nextNodeNeeded = false

        }

        return Pair(methodMatch, matches)

    }

    fun searchMethodForInts(methodNode: MethodNode, query: List<Int>): Pair<Boolean, MutableList<IntInsnNode>> {

        var matchIndex = 0
        var tentativeMatches = 0

        val matches = mutableListOf<IntInsnNode>()
        var methodMatch = false;

        var nextNodeNeeded = false

        val instructions = methodNode.instructions;

        var instrNode = instructions.first

        while (instrNode != null) {

            if (matchIndex == query.size){
                matchIndex = 0
                tentativeMatches = 0
                methodMatch = true;
            }

            if (instrNode.opcode == query[matchIndex] || query[matchIndex] == -1) {
                if (instrNode is IntInsnNode) {
                    matches.add(instrNode)
                    tentativeMatches++
                }
                matchIndex++
                nextNodeNeeded = true
            } else {
                if (matchIndex == 0)
                    nextNodeNeeded = true
                matches.removeIf { matches.indexOf(it) >= (matches.size - tentativeMatches) }
                tentativeMatches = 0
                matchIndex = 0
            }

            if (nextNodeNeeded)
                instrNode = instrNode.next

            nextNodeNeeded = false

        }

        return Pair(methodMatch, matches)

    }


    fun searchMethodForMethodCall(methodNode: MethodNode, query: List<Int>): Pair<Boolean, MutableList<MethodInsnNode>> {

        var matchIndex = 0
        var tentativeMatches = 0

        val matches = mutableListOf<MethodInsnNode>()
        var methodMatch = false;

        var nextNodeNeeded = false

        val instructions = methodNode.instructions;

        var instrNode = instructions.first

        while (instrNode != null) {

            if (matchIndex == query.size){
                matchIndex = 0
                tentativeMatches = 0
                methodMatch = true;
            }

            if (instrNode.opcode == query[matchIndex] || query[matchIndex] == -1) {
                if (instrNode is MethodInsnNode) {
                    matches.add(instrNode)
                    tentativeMatches++
                }
                matchIndex++
                nextNodeNeeded = true
            } else {
                if (matchIndex == 0)
                    nextNodeNeeded = true
                matches.removeIf { matches.indexOf(it) >= (matches.size - tentativeMatches) }
                tentativeMatches = 0
                matchIndex = 0
            }

            if (nextNodeNeeded)
                instrNode = instrNode.next

            nextNodeNeeded = false

        }

        return Pair(methodMatch, matches)

    }


    //Will return NEW, ANEWARRAY, CHECKCAST, INSTANCEOF
    fun searchMethodForCast(methodNode: MethodNode, query: List<Int>): Pair<Boolean, MutableList<TypeInsnNode>> {

        var matchIndex = 0
        var tentativeMatches = 0

        val matches = mutableListOf<TypeInsnNode>()
        var methodMatch = false;

        var nextNodeNeeded = false

        val instructions = methodNode.instructions;

        var instrNode = instructions.first

        while (instrNode != null) {

            if (matchIndex == query.size){
                matchIndex = 0
                tentativeMatches = 0
                methodMatch = true;
            }

            if (instrNode.opcode == query[matchIndex] || query[matchIndex] == -1) {
                if (instrNode is TypeInsnNode) {
                    matches.add(instrNode)
                    tentativeMatches++
                }
                matchIndex++
                nextNodeNeeded = true
            } else {
                if (matchIndex == 0)
                    nextNodeNeeded = true
                matches.removeIf { matches.indexOf(it) >= (matches.size - tentativeMatches) }
                tentativeMatches = 0
                matchIndex = 0
            }

            if (nextNodeNeeded)
                instrNode = instrNode.next

            nextNodeNeeded = false

        }

        return Pair(methodMatch, matches)

    }




}