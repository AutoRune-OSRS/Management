package io.autorune.osrs.api.entity;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.IterableNodeList;

public interface Actor extends Entity {
	int[] getHitSplatTypes();

	void setHitSplatTypes(int[] value);

	int[] getHitSplatValues();

	void setHitSplatValues(int[] value);

	int[] getHitSplatTypes2();

	void setHitSplatTypes2(int[] value);

	int[] getHitSplatValues2();

	void setHitSplatValues2(int[] value);

	int[] getHitSplatCycles();

	void setHitSplatCycles(int[] value);

	byte getHitSplatCount();

	void setHitSplatCount(byte value);

	IterableNodeList getHealthBars();

	void setHealthBars(IterableNodeList value);

	byte[] getPathTraversed();

	void setPathTraversed(byte[] value);

	boolean getIsWalking();

	void setIsWalking(boolean value);

	int getX();

	void setX(int value);

	int getY();

	void setY(int value);

	int[] getPathY();

	void setPathY(int[] value);

	int[] getPathX();

	void setPathX(int[] value);

	int getOrientation();

	void setOrientation(int value);

	int getTargetIndex();

	void setTargetIndex(int value);

	Client getClientInstance();

	boolean isVisible();

	void addHitSplat(int param0, int param1, int param2, int param3, int param4, int param5);

	void addHealthBar(int param0, int param1, int param2, int param3, int param4, int param5);

	void removeHealthBar(int param0);

	int getWorldX();

	int getWorldY();

	int getHealthRatio();

	int getHealth();
}
