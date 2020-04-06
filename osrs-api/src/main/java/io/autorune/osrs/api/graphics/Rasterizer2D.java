package io.autorune.osrs.api.graphics;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.DoublyNode;

public interface Rasterizer2D extends DoublyNode {
	int[] getDrawingAreaPixels();

	void setDrawingAreaPixels(int[] value);

	int getDrawingAreaWidth();

	void setDrawingAreaWidth(int value);

	int getDrawingAreaHeight();

	void setDrawingAreaHeight(int value);

	int getDrawingAreaLeft();

	void setDrawingAreaLeft(int value);

	int getDrawingAreaTop();

	void setDrawingAreaTop(int value);

	int getDrawingAreaBottom();

	void setDrawingAreaBottom(int value);

	int getDrawingAreaRight();

	void setDrawingAreaRight(int value);

	Client getClientInstance();
}
