package io.autorune.osrs.mixins.model

import io.autorune.osrs.api.model.Model
import io.autorune.utilities.scene.Perspective
import io.autorune.utilities.scene.model.Jarvis
import java.awt.Shape

abstract class ModelMixin: Model {

    fun fetchConvexHullMixin(localX: Int, localY: Int, orientation: Int, tileHeight: Int): Shape? {
        val x2d = IntArray(verticesCount)
        val y2d = IntArray(verticesCount)
        val c = clientInstance
        Perspective.modelToCanvas(c.cameraPitch, c.cameraYaw, c.cameraX, c.cameraY, c.cameraZ,
                c.viewportWidth, c.viewportHeight, c.viewportOffsetX, c.viewportOffsetY, c.viewportZoom,
                verticesCount, localX, localY, tileHeight, orientation, verticesX, verticesZ, verticesY, x2d, y2d)
        return Jarvis.convexHull(x2d, y2d)
    }

}