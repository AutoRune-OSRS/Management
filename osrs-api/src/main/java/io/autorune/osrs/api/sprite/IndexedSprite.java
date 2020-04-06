package io.autorune.osrs.api.sprite;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.graphics.Rasterizer2D;

public interface IndexedSprite extends Rasterizer2D {
	Client getClientInstance();
}
