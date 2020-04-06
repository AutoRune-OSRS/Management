package io.autorune.osrs.api.region;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.Node;
import io.autorune.osrs.api.entity.InteractiveObject;

public interface Tile extends Node {
	int getPlane();

	void setPlane(int value);

	int getGameObjectCount();

	void setGameObjectCount(int value);

	InteractiveObject[] getGameObjects();

	void setGameObjects(InteractiveObject[] value);

	long getTag();

	void setTag(long value);

	int getStartX();

	void setStartX(int value);

	int getStartY();

	void setStartY(int value);

	Client getClientInstance();
}
