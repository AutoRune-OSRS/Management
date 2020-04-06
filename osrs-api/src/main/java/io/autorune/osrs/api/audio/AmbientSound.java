package io.autorune.osrs.api.audio;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.Node;

public interface AmbientSound extends Node {
	Client getClientInstance();
}
