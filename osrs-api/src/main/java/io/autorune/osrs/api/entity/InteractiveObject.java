package io.autorune.osrs.api.entity;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.model.Model;
import java.awt.Shape;

public interface InteractiveObject {
	long getHash();

	void setHash(long value);

	int getFlags();

	void setFlags(int value);

	int getPlane();

	void setPlane(int value);

	int getCenterX();

	void setCenterX(int value);

	int getCenterY();

	void setCenterY(int value);

	int getHeight();

	void setHeight(int value);

	Entity getEntity();

	void setEntity(Entity value);

	int getOrientation();

	void setOrientation(int value);

	int getStartX();

	void setStartX(int value);

	int getStartY();

	void setStartY(int value);

	int getEndX();

	void setEndX(int value);

	int getEndY();

	void setEndY(int value);

	Client getClientInstance();

	int getId();

	Model fetchModel();

	Shape fetchConvexHull();
}
