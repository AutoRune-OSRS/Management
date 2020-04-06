package io.autorune.osrs.api.font;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.graphics.Rasterizer2D;

public interface AbstractFont extends Rasterizer2D {
	Client getClientInstance();
}
