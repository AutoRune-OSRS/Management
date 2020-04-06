package io.autorune.osrs.api.widget;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.DoublyNode;

public interface Script extends DoublyNode {
	Client getClientInstance();
}
