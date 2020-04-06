package io.autorune.osrs.api.definitions;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.DoublyNode;
import io.autorune.osrs.api.io.RSByteBuffer;

public interface HealthBarDefinition extends DoublyNode {
	int getUnknown0();

	void setUnknown0(int value);

	int getUnknown1();

	void setUnknown1(int value);

	int getUnknown2();

	void setUnknown2(int value);

	int getHeight();

	void setHeight(int value);

	int getOverlaySpriteId();

	void setOverlaySpriteId(int value);

	int getUnderlaySpriteId();

	void setUnderlaySpriteId(int value);

	int getUnknown3();

	void setUnknown3(int value);

	int getWidth();

	void setWidth(int value);

	int getWidthPadding();

	void setWidthPadding(int value);

	Client getClientInstance();

	void decode(RSByteBuffer param0);

	void decodeOpcode(RSByteBuffer param0, int param1);
}
