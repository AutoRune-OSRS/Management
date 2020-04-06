package io.autorune.osrs.api.container;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.Node;

public interface ItemContainer extends Node {
	int[] getItemIds();

	void setItemIds(int[] value);

	int[] getItemQuantities();

	void setItemQuantities(int[] value);

	Client getClientInstance();
}
