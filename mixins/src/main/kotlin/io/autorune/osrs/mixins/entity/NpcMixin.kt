package io.autorune.osrs.mixins.entity

import io.autorune.osrs.api.entity.Npc
import io.autorune.utilities.scene.Perspective
import io.autorune.utilities.scene.coords.LocalPoint
import java.awt.Shape

abstract class NpcMixin: Npc {

    fun fetchNpcConvexHullMixin(): Shape? {

        val model = model() ?: return null

        val size: Int = definition.size

        val tileHeightPoint = LocalPoint(
                size * Perspective.LOCAL_HALF_TILE_SIZE - Perspective.LOCAL_HALF_TILE_SIZE + x,
                size * Perspective.LOCAL_HALF_TILE_SIZE - Perspective.LOCAL_HALF_TILE_SIZE + y)

        val tileHeight: Int = Perspective.fetchTileHeightFromLocal(clientInstance.tileHeights, clientInstance.tileSettings, tileHeightPoint, clientInstance.plane)

        return model.fetchConvexHull(x, y, orientation, tileHeight)

    }

}