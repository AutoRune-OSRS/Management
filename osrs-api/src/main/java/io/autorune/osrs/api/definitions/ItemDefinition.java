package io.autorune.osrs.api.definitions;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.DoublyNode;
import io.autorune.osrs.api.collection.IterableHashTable;
import io.autorune.osrs.api.io.RSByteBuffer;
import java.lang.String;

public interface ItemDefinition extends DoublyNode {
	int getModel();

	void setModel(int value);

	String getName();

	void setName(String value);

	int getZoom2d();

	void setZoom2d(int value);

	int getXan2d();

	void setXan2d(int value);

	int getYan2d();

	void setYan2d(int value);

	int getOffsetX2d();

	void setOffsetX2d(int value);

	int getOffsetY2d();

	void setOffsetY2d(int value);

	int getStackable();

	void setStackable(int value);

	int getPrice();

	void setPrice(int value);

	boolean getMembers();

	void setMembers(boolean value);

	int getMaleModel();

	void setMaleModel(int value);

	int getMaleOffset();

	void setMaleOffset(int value);

	int getMaleModel1();

	void setMaleModel1(int value);

	int getFemaleModel();

	void setFemaleModel(int value);

	int getFemaleOffset();

	void setFemaleOffset(int value);

	int getFemaleModel1();

	void setFemaleModel1(int value);

	short[] getRecolorFrom();

	void setRecolorFrom(short[] value);

	short[] getRecolorTo();

	void setRecolorTo(short[] value);

	short[] getReTextureFrom();

	void setReTextureFrom(short[] value);

	short[] getReTextureTo();

	void setReTextureTo(short[] value);

	int getShiftClickIndex();

	void setShiftClickIndex(int value);

	boolean getIsTradable();

	void setIsTradable(boolean value);

	int getMaleModel2();

	void setMaleModel2(int value);

	int getFemaleModel2();

	void setFemaleModel2(int value);

	int getMaleHeadModel();

	void setMaleHeadModel(int value);

	int getFemaleHeadModel();

	void setFemaleHeadModel(int value);

	int getMaleHeadModel2();

	void setMaleHeadModel2(int value);

	int getFemaleHeadModel2();

	void setFemaleHeadModel2(int value);

	int getZan2d();

	void setZan2d(int value);

	int getNote();

	void setNote(int value);

	int getNoteTemplate();

	void setNoteTemplate(int value);

	int[] getStackIds();

	void setStackIds(int[] value);

	int[] getStackAmounts();

	void setStackAmounts(int[] value);

	int getResizeX();

	void setResizeX(int value);

	int getResizeY();

	void setResizeY(int value);

	int getResizeZ();

	void setResizeZ(int value);

	int getAmbient();

	void setAmbient(int value);

	int getContrast();

	void setContrast(int value);

	int getTeam();

	void setTeam(int value);

	int getUnNotedId();

	void setUnNotedId(int value);

	int getNotedId();

	void setNotedId(int value);

	int getPlaceholder();

	void setPlaceholder(int value);

	int getPlaceHolderTemplate();

	void setPlaceHolderTemplate(int value);

	IterableHashTable getParams();

	void setParams(IterableHashTable value);

	String[] getGroundActions();

	void setGroundActions(String[] value);

	String[] getInventoryActions();

	void setInventoryActions(String[] value);

	Client getClientInstance();

	void decode(RSByteBuffer param0);

	void decodeOpcode(RSByteBuffer param0, int param1);
}
