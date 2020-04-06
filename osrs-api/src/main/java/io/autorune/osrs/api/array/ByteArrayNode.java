package io.autorune.osrs.api.array;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.Node;

public interface ByteArrayNode extends Node {
	byte[] getByteArray();

	void setByteArray(byte[] value);

	Client getClientInstance();
}
