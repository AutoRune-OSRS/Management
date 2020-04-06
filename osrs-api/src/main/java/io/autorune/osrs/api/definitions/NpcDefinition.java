package io.autorune.osrs.api.definitions;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.DoublyNode;
import io.autorune.osrs.api.collection.IterableHashTable;
import io.autorune.osrs.api.io.RSByteBuffer;
import io.autorune.osrs.api.model.Model;
import java.lang.String;

public interface NpcDefinition extends DoublyNode {
	int[] getModels();

	void setModels(int[] value);

	String getName();

	void setName(String value);

	int getSize();

	void setSize(int value);

	int getReadySequence();

	void setReadySequence(int value);

	int getWalkSequence();

	void setWalkSequence(int value);

	int getTurnLeftSequence();

	void setTurnLeftSequence(int value);

	int getTurnRightSequence();

	void setTurnRightSequence(int value);

	int getWalkBackSequence();

	void setWalkBackSequence(int value);

	int getWalkLeftSequence();

	void setWalkLeftSequence(int value);

	int getWalkRightSequence();

	void setWalkRightSequence(int value);

	short[] getRecolorFrom();

	void setRecolorFrom(short[] value);

	short[] getRecolorTo();

	void setRecolorTo(short[] value);

	short[] getReTextureFrom();

	void setReTextureFrom(short[] value);

	short[] getReTextureTo();

	void setReTextureTo(short[] value);

	int[] getHeadModels();

	void setHeadModels(int[] value);

	boolean getDrawMapDot();

	void setDrawMapDot(boolean value);

	int getCombatLevel();

	void setCombatLevel(int value);

	int getWidthScale();

	void setWidthScale(int value);

	int getHeightScale();

	void setHeightScale(int value);

	boolean getIsVisible();

	void setIsVisible(boolean value);

	int getAmbient();

	void setAmbient(int value);

	int getContrast();

	void setContrast(int value);

	int getHeadIconPrayer();

	void setHeadIconPrayer(int value);

	int getRotation();

	void setRotation(int value);

	boolean getCanInteract();

	void setCanInteract(boolean value);

	boolean getIsClickable();

	void setIsClickable(boolean value);

	boolean getIsFollower();

	void setIsFollower(boolean value);

	IterableHashTable getParams();

	void setParams(IterableHashTable value);

	int getTransformVarBit();

	void setTransformVarBit(int value);

	int getTransformVarp();

	void setTransformVarp(int value);

	int[] getTransforms();

	void setTransforms(int[] value);

	String[] getActions();

	void setActions(String[] value);

	int getId();

	void setId(int value);

	Client getClientInstance();

	void decode(RSByteBuffer param0);

	void decodeOpcode(RSByteBuffer param0, int param1);

	Model model(SequenceDefinition param0, int param1, SequenceDefinition param2, int param3);

	NpcDefinition transform();
}
