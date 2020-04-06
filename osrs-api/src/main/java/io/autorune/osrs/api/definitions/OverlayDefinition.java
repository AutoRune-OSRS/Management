package io.autorune.osrs.api.definitions;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.DoublyNode;
import io.autorune.osrs.api.io.RSByteBuffer;

public interface OverlayDefinition extends DoublyNode {
	Client getClientInstance();

	void decode(RSByteBuffer param0, int param1);

	void decodeOpcode(RSByteBuffer param0, int param1, int param2);
}
