package io.autorune.osrs.api.widget;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.Node;

public interface ScriptEvent extends Node {
	Client getClientInstance();
}
