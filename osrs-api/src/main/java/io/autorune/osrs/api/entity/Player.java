package io.autorune.osrs.api.entity;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.io.RSByteBuffer;
import io.autorune.osrs.api.user.Username;

public interface Player extends Actor {
	int getPlane();

	void setPlane(int value);

	int getTileX();

	void setTileX(int value);

	int getTileY();

	void setTileY(int value);

	Username getUsername();

	void setUsername(Username value);

	int getCombatLevel();

	void setCombatLevel(int value);

	Client getClientInstance();

	void readPlayerAppearanceUpdate(RSByteBuffer param0);
}
