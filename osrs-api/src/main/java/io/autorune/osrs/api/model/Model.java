package io.autorune.osrs.api.model;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.entity.Entity;
import java.awt.Shape;

public interface Model extends Entity {
	int getXzRadius();

	void setXzRadius(int value);

	int getVerticesCount();

	void setVerticesCount(int value);

	int getIndicesCount();

	void setIndicesCount(int value);

	int[] getVerticesX();

	void setVerticesX(int[] value);

	int[] getVerticesZ();

	void setVerticesZ(int[] value);

	int[] getIndices1();

	void setIndices1(int[] value);

	int[] getVerticesY();

	void setVerticesY(int[] value);

	Client getClientInstance();

	Model contourGround(int[][] param0, int param1, int param2, int param3, boolean param4,
			int param5);

	Shape fetchConvexHull(int param0, int param1, int param2, int param3);
}
