package io.autorune.osrs.api.collection;

import io.autorune.osrs.api.Client;

public interface IntegerNode extends Node {
	int getInteger();

	void setInteger(int value);

	Client getClientInstance();
}
