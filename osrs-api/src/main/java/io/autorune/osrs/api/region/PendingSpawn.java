package io.autorune.osrs.api.region;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.Node;

public interface PendingSpawn extends Node {
	Client getClientInstance();
}
