package io.autorune.osrs.api.region;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.entity.Entity;

public interface Scene {
	int[][][] getTileDrawCounts();

	void setTileDrawCounts(int[][][] value);

	int[][][] getTileHeights();

	void setTileHeights(int[][][] value);

	int getSceneDrawCount();

	void setSceneDrawCount(int value);

	Tile[][][] getTiles();

	void setTiles(Tile[][][] value);

	Client getClientInstance();

	boolean isTileOccluded(int param0, int param1, int param2);

	void setLinkBelow(int param0, int param1);

	void newGroundItemPile(int param0, int param1, int param2, int param3, Entity param4, long param5,
			Entity param6, Entity param7);

	boolean addInteractiveObject(int param0, int param1, int param2, int param3, int param4,
			int param5, int param6, int param7, Entity param8, int param9, boolean param10, long param11,
			int param12);
}
