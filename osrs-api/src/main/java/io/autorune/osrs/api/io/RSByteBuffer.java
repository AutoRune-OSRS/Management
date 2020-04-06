package io.autorune.osrs.api.io;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.Node;

public interface RSByteBuffer extends Node {
	byte[] getBuffer();

	void setBuffer(byte[] value);

	int getOffset();

	void setOffset(int value);

	int[] getCrcTable32();

	void setCrcTable32(int[] value);

	long[] getCrcTable64();

	void setCrcTable64(long[] value);

	Client getClientInstance();
}
