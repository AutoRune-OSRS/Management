package io.autorune.osrs.api.io;

import io.autorune.osrs.api.Client;

public interface BufferedNetSocket extends AbstractSocket {
	Client getClientInstance();
}
