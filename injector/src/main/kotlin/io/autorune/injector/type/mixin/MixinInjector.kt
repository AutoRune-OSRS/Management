package io.autorune.injector.type.mixin

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.autorune.injector.Injector
import org.objectweb.asm.*
import org.objectweb.asm.tree.*


class MixinInjector(private val classJson: JsonObject,
					private val mixinClassNode: ClassNode,
					private val classNode: ClassNode,
					private val classHooks: JsonArray,
					val classPool: List<ClassNode>
) : Injector()
{

	override fun runInjection() {

		for (fieldNode in mixinClassNode.fields)
		{
			MixinFieldInjector(fieldNode, classNode).runInjection()
		}

		for (methodNode in mixinClassNode.methods)
		{
			MixinMethodInjector(methodNode, classJson, classNode, classHooks, classPool).runInjection()
		}

	}

}