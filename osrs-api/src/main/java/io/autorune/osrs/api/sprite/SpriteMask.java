package io.autorune.osrs.api.sprite;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.DoublyNode;

public interface SpriteMask extends DoublyNode {
	Client getClientInstance();
}
