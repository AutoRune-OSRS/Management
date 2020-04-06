package io.autorune.osrs.api.model;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.entity.Entity;

public interface ModelHeader extends Entity {
	Client getClientInstance();
}
