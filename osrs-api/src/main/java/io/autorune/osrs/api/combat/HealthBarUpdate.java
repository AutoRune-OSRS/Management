package io.autorune.osrs.api.combat;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.Node;

public interface HealthBarUpdate extends Node {
	int getCycle();

	void setCycle(int value);

	int getHealth();

	void setHealth(int value);

	int getHealth2();

	void setHealth2(int value);

	int getCycleOffset();

	void setCycleOffset(int value);

	Client getClientInstance();

	void update(int param0, int param1, int param2, int param3);
}
