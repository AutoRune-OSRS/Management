package io.autorune.osrs.api.combat;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.IterableNodeList;
import io.autorune.osrs.api.collection.Node;
import io.autorune.osrs.api.definitions.HealthBarDefinition;

public interface HealthBar extends Node {
	IterableNodeList getUpdates();

	void setUpdates(IterableNodeList value);

	HealthBarDefinition getDefinition();

	void setDefinition(HealthBarDefinition value);

	Client getClientInstance();

	void applyHitUpdate(int param0, int param1, int param2, int param3);

	HealthBarUpdate fetchHealthBarUpdate(int param0);

	boolean hasNoUpdates();
}
