package io.autorune.osrs.mixins.entity

import io.autorune.osrs.api.entity.InteractiveObject
import io.autorune.osrs.api.model.Model
import io.autorune.utilities.scene.Perspective
import io.autorune.utilities.scene.coords.LocalPoint
import java.awt.Shape

abstract class InteractiveObjectMixin : InteractiveObject
{

	fun getIdMixin() : Int
	{
		return (hash ushr 17 and 4294967295L).toInt()
	}

	fun fetchModelMixin() : Model?
	{

		val renderable = entity ?: return null

		return if(renderable is Model)
		{
			renderable
		}
		else
		{
			renderable.fetchModel()
		}
	}

	fun fetchConvexHullMixin() : Shape?
	{

		val model = fetchModel() ?: return null

		val tileHeightPoint = LocalPoint(centerX, centerY)

		val tileHeight: Int = Perspective.fetchTileHeightFromLocal(clientInstance.tileHeights, clientInstance.tileSettings, tileHeightPoint, plane)

		return model.fetchConvexHull(centerX, centerY, orientation, tileHeight)
	}


}