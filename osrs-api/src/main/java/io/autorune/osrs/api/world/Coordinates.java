package io.autorune.osrs.api.world;

import io.autorune.osrs.api.Client;

public interface Coordinates {
	int getPlane();

	void setPlane(int value);

	int getX();

	void setX(int value);

	int getY();

	void setY(int value);

	Client getClientInstance();

	int pack();
}
