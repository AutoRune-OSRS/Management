package io.autorune.osrs.api.audio;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.Node;

public interface AbstractSound extends Node {
	int getPosition();

	void setPosition(int value);

	Client getClientInstance();
}
