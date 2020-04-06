package io.autorune.osrs.api.entity;

import io.autorune.osrs.api.Client;

public interface SpawnedObject extends Entity {
	Client getClientInstance();
}
