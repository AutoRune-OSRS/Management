package io.autorune.osrs.api.io;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.DoublyNode;

public interface NetFileRequest extends DoublyNode {
	Client getClientInstance();
}
