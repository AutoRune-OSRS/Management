package io.autorune.osrs.api.sprite;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.graphics.Rasterizer2D;

public interface Sprite extends Rasterizer2D {
	int[] getSpritePixels();

	void setSpritePixels(int[] value);

	int getSpriteMaxWidth();

	void setSpriteMaxWidth(int value);

	int getSpriteWidth();

	void setSpriteWidth(int value);

	int getSpriteMaxHeight();

	void setSpriteMaxHeight(int value);

	int getSpriteHeight();

	void setSpriteHeight(int value);

	int getSpriteOffsetY();

	void setSpriteOffsetY(int value);

	int getSpriteOffsetX();

	void setSpriteOffsetX(int value);

	Client getClientInstance();
}
