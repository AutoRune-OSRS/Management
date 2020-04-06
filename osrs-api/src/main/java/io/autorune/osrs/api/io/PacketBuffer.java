package io.autorune.osrs.api.io;

import io.autorune.osrs.api.Client;

public interface PacketBuffer extends RSByteBuffer {
	Client getClientInstance();
}
