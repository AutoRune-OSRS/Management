package io.autorune.osrs.api.reflection;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.Node;

public interface ClassInfo extends Node {
	Client getClientInstance();
}
