package io.autorune.osrs.api.user;

import io.autorune.osrs.api.Client;

public interface RelatedUser extends BasicUser {
	int getWorld();

	void setWorld(int value);

	int getPosition();

	void setPosition(int value);

	int getRank();

	void setRank(int value);

	Client getClientInstance();
}
