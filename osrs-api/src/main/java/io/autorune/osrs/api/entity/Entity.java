package io.autorune.osrs.api.entity;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.DoublyNode;
import io.autorune.osrs.api.model.Model;

public interface Entity extends DoublyNode {
	int getHeight();

	void setHeight(int value);

	Client getClientInstance();

	Model fetchModel();

	void drawEntityModel(int param0, int param1, int param2, int param3, int param4, int param5,
			int param6, int param7, long param8);
}
