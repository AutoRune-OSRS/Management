package io.autorune.osrs.mixins.entity

import io.autorune.osrs.api.combat.HealthBar
import io.autorune.osrs.api.combat.HealthBarUpdate
import io.autorune.osrs.api.entity.Actor

abstract class ActorMixin: Actor
{

	fun getWorldXMixin(): Int {

		val localX = x

		val regionBaseX = clientInstance.baseX

		return regionBaseX + (localX shr 7)

	}

	fun getWorldYMixin(): Int {

		val localY = y

		val regionBaseY = clientInstance.baseY

		return regionBaseY + (localY shr 7)

	}

	fun getHealthRatioMixin() : Int
	{
		val healthBars = healthBars
		if(healthBars != null)
		{
			val current = healthBars.sentinel
			val next = current.next
			if(next is HealthBar)
			{
				val updates = next.updates
				val currentUpdate = updates.sentinel
				val nextUpdate = currentUpdate.next
				if(nextUpdate is HealthBarUpdate)
				{
					return nextUpdate.health
				}
			}
		}
		return -1
	}

	fun getHealthMixin() : Int
	{
		val healthBars = healthBars
		if(healthBars != null)
		{
			val current = healthBars.sentinel
			val next = current.next
			if(next is HealthBar)
			{
				val definition = next.definition
				return definition.width
			}
		}
		return -1
	}

}