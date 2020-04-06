package io.autorune.osrs.api.world;

import io.autorune.osrs.api.Client;

public interface World {
	int getId();

	void setId(int value);

	Client getClientInstance();
}
