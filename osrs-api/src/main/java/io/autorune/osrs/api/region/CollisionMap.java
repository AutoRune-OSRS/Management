package io.autorune.osrs.api.region;

import io.autorune.osrs.api.Client;

public interface CollisionMap {
	Client getClientInstance();

	void setFlag(int param0, int param1, int param2);

	void setFlagOff(int param0, int param1, int param2);

	void setFlagOffNonSquare(int param0, int param1, int param2, int param3, int param4,
			boolean param5);

	void addWall(int param0, int param1, int param2, int param3, boolean param4);

	void removeWall(int param0, int param1, int param2, int param3, boolean param4);
}
