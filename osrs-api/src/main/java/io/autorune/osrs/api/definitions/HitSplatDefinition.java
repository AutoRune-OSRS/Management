package io.autorune.osrs.api.definitions;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.DoublyNode;
import io.autorune.osrs.api.io.RSByteBuffer;

public interface HitSplatDefinition extends DoublyNode {
	Client getClientInstance();

	void decode(RSByteBuffer param0);

	void decodeOpcode(RSByteBuffer param0, int param1);

	HitSplatDefinition transformHitSplat();
}
