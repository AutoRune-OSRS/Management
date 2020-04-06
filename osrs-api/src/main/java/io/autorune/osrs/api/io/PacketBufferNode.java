package io.autorune.osrs.api.io;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.Node;

public interface PacketBufferNode extends Node {
	Client getClientInstance();
}
