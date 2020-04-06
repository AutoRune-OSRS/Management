package io.autorune.osrs.api.definitions;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.DoublyNode;
import io.autorune.osrs.api.collection.IterableHashTable;
import io.autorune.osrs.api.io.RSByteBuffer;
import java.lang.String;

public interface ObjectDefinition extends DoublyNode {
	int[] getModels();

	void setModels(int[] value);

	int[] getModelIds();

	void setModelIds(int[] value);

	String getName();

	void setName(String value);

	int getSizeX();

	void setSizeX(int value);

	int getSizeY();

	void setSizeY(int value);

	int getInteractionClipping();

	void setInteractionClipping(int value);

	boolean getProjectileClipped();

	void setProjectileClipped(boolean value);

	int getSolid();

	void setSolid(int value);

	int getTerrainClipping();

	void setTerrainClipping(int value);

	boolean getNonFlatShading();

	void setNonFlatShading(boolean value);

	boolean getModelClipped();

	void setModelClipped(boolean value);

	int getAnimationId();

	void setAnimationId(int value);

	int getOffsetMultiplier();

	void setOffsetMultiplier(int value);

	int getAmbient();

	void setAmbient(int value);

	int getContrast();

	void setContrast(int value);

	short[] getRecolorFrom();

	void setRecolorFrom(short[] value);

	short[] getRecolorTo();

	void setRecolorTo(short[] value);

	short[] getReTextureFrom();

	void setReTextureFrom(short[] value);

	short[] getReTextureTo();

	void setReTextureTo(short[] value);

	boolean getRotated();

	void setRotated(boolean value);

	boolean getClipped();

	void setClipped(boolean value);

	int getModelSizeX();

	void setModelSizeX(int value);

	int getModelHeight();

	void setModelHeight(int value);

	int getModelSizeY();

	void setModelSizeY(int value);

	int getMapSceneId();

	void setMapSceneId(int value);

	int getOffsetX();

	void setOffsetX(int value);

	int getOffsetHeight();

	void setOffsetHeight(int value);

	int getOffsetY();

	void setOffsetY(int value);

	boolean getIsInteractive();

	void setIsInteractive(boolean value);

	boolean getIsSolid();

	void setIsSolid(boolean value);

	int getUnknownField1();

	void setUnknownField1(int value);

	int getAmbientSoundId();

	void setAmbientSoundId(int value);

	int getUnknownSoundField1();

	void setUnknownSoundField1(int value);

	int getUnknownSoundField2();

	void setUnknownSoundField2(int value);

	int getUnknownSoundField3();

	void setUnknownSoundField3(int value);

	int[] getSoundEffectIds();

	void setSoundEffectIds(int[] value);

	int getMapIconId();

	void setMapIconId(int value);

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

	Client getClientInstance();

	void decode(RSByteBuffer param0);

	void decodeOpcode(RSByteBuffer param0, int param1);
}
