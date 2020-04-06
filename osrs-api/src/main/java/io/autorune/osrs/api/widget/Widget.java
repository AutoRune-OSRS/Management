package io.autorune.osrs.api.widget;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.Node;
import io.autorune.osrs.api.definitions.SequenceDefinition;
import io.autorune.osrs.api.font.Font;
import io.autorune.osrs.api.io.RSByteBuffer;
import io.autorune.osrs.api.model.Model;
import io.autorune.osrs.api.player.PlayerAppearance;
import io.autorune.osrs.api.sprite.Sprite;
import io.autorune.osrs.api.sprite.SpriteMask;
import java.awt.Rectangle;
import java.lang.Object;
import java.lang.String;

public interface Widget extends Node {
	boolean getIsCs2Widget();

	void setIsCs2Widget(boolean value);

	int getType();

	void setType(int value);

	int getContentType();

	void setContentType(int value);

	int getRawX();

	void setRawX(int value);

	int getRawY();

	void setRawY(int value);

	int getRawWidth();

	void setRawWidth(int value);

	int getRawHeight();

	void setRawHeight(int value);

	int getWidthAlignment();

	void setWidthAlignment(int value);

	int getHeightAlignment();

	void setHeightAlignment(int value);

	int getXAlignment();

	void setXAlignment(int value);

	int getYAlignment();

	void setYAlignment(int value);

	int getParentHash();

	void setParentHash(int value);

	boolean getIsHidden();

	void setIsHidden(boolean value);

	int getScrollWidth();

	void setScrollWidth(int value);

	int getScrollHeight();

	void setScrollHeight(int value);

	boolean getNoClickThrough();

	void setNoClickThrough(boolean value);

	int getSpriteId();

	void setSpriteId(int value);

	int getSpriteAngle();

	void setSpriteAngle(int value);

	boolean getSpriteTiling();

	void setSpriteTiling(boolean value);

	int getTransparencyTop();

	void setTransparencyTop(int value);

	int getOutline();

	void setOutline(int value);

	int getSpriteShadow();

	void setSpriteShadow(int value);

	boolean getSpriteFlipV();

	void setSpriteFlipV(boolean value);

	boolean getSpriteFlipH();

	void setSpriteFlipH(boolean value);

	int getModelType();

	void setModelType(int value);

	int getModelId();

	void setModelId(int value);

	int getModelOffsetX();

	void setModelOffsetX(int value);

	int getModelOffsetY();

	void setModelOffsetY(int value);

	int getModelAngleX();

	void setModelAngleX(int value);

	int getModelAngleY();

	void setModelAngleY(int value);

	int getModelAngleZ();

	void setModelAngleZ(int value);

	int getModelZoom();

	void setModelZoom(int value);

	int getSequenceId();

	void setSequenceId(int value);

	boolean getModelOrthogonal();

	void setModelOrthogonal(boolean value);

	int getField6969();

	void setField6969(int value);

	int getFontId();

	void setFontId(int value);

	String getText();

	void setText(String value);

	int getTextLineHeight();

	void setTextLineHeight(int value);

	int getTextXAlignment();

	void setTextXAlignment(int value);

	int getTextYAlignment();

	void setTextYAlignment(int value);

	boolean getTextShadowed();

	void setTextShadowed(boolean value);

	int getColor();

	void setColor(int value);

	boolean getFill();

	void setFill(boolean value);

	int getLineWidth();

	void setLineWidth(int value);

	boolean getField696969();

	void setField696969(boolean value);

	int getClickMask();

	void setClickMask(int value);

	String getDataText();

	void setDataText(String value);

	String[] getActions();

	void setActions(String[] value);

	int getDragZoneSize();

	void setDragZoneSize(int value);

	int getDragThreshold();

	void setDragThreshold(int value);

	boolean getIsScrollBar();

	void setIsScrollBar(boolean value);

	String getSpellActionName();

	void setSpellActionName(String value);

	Object[] getOnLoad();

	void setOnLoad(Object[] value);

	Object[] getOnMouseOver();

	void setOnMouseOver(Object[] value);

	Object[] getOnMouseLeave();

	void setOnMouseLeave(Object[] value);

	Object[] getOnTargetLeave();

	void setOnTargetLeave(Object[] value);

	Object[] getOnTargetEnter();

	void setOnTargetEnter(Object[] value);

	Object[] getOnVarTransmit();

	void setOnVarTransmit(Object[] value);

	Object[] getOnInvTransmit();

	void setOnInvTransmit(Object[] value);

	Object[] getOnStatTransmit();

	void setOnStatTransmit(Object[] value);

	Object[] getOnTimer();

	void setOnTimer(Object[] value);

	Object[] getOnOp();

	void setOnOp(Object[] value);

	Object[] getOnMouseRepeat();

	void setOnMouseRepeat(Object[] value);

	Object[] getOnClick();

	void setOnClick(Object[] value);

	Object[] getOnClickRepeat();

	void setOnClickRepeat(Object[] value);

	Object[] getOnRelease();

	void setOnRelease(Object[] value);

	Object[] getOnHold();

	void setOnHold(Object[] value);

	Object[] getOnDrag();

	void setOnDrag(Object[] value);

	Object[] getOnDragComplete();

	void setOnDragComplete(Object[] value);

	Object[] getOnScroll();

	void setOnScroll(Object[] value);

	int[] getVarTransmitTriggers();

	void setVarTransmitTriggers(int[] value);

	int[] getInvTransmitTriggers();

	void setInvTransmitTriggers(int[] value);

	int[] getStatTransmitTriggers();

	void setStatTransmitTriggers(int[] value);

	int getButtonType();

	void setButtonType(int value);

	int getMouseOverRedirect();

	void setMouseOverRedirect(int value);

	int[] getCs1Comparisons();

	void setCs1Comparisons(int[] value);

	int[] getCs1ComparisonValues();

	void setCs1ComparisonValues(int[] value);

	int[][] getCs1Instructions();

	void setCs1Instructions(int[][] value);

	int[] getItemIds();

	void setItemIds(int[] value);

	int[] getItemQuantities();

	void setItemQuantities(int[] value);

	int getPaddingX();

	void setPaddingX(int value);

	int getPaddingY();

	void setPaddingY(int value);

	int[] getInventoryXOffsets();

	void setInventoryXOffsets(int[] value);

	int[] getInventoryYOffsets();

	void setInventoryYOffsets(int[] value);

	int[] getInventorySprites();

	void setInventorySprites(int[] value);

	String getText2();

	void setText2(String value);

	int getColor2();

	void setColor2(int value);

	int getMouseOverColor();

	void setMouseOverColor(int value);

	int getMouseOverColor2();

	void setMouseOverColor2(int value);

	int getSpriteId2();

	void setSpriteId2(int value);

	int getModelType2();

	void setModelType2(int value);

	int getModelId2();

	void setModelId2(int value);

	int getSequenceId2();

	void setSequenceId2(int value);

	String[] getItemActions();

	void setItemActions(String[] value);

	String getSpellName();

	void setSpellName(String value);

	String getButtonText();

	void setButtonText(String value);

	boolean getHasListener();

	void setHasListener(boolean value);

	int getWidth();

	void setWidth(int value);

	int getHeight();

	void setHeight(int value);

	int getX();

	void setX(int value);

	int getY();

	void setY(int value);

	int getWidgetHash();

	void setWidgetHash(int value);

	Widget[] getChildren();

	void setChildren(Widget[] value);

	Client getClientInstance();

	void decode(RSByteBuffer param0);

	void decodeLegacy(RSByteBuffer param0);

	Object[] readListener(RSByteBuffer param0);

	Sprite fetchSprite(boolean param0);

	int[] readListenerTriggers(RSByteBuffer param0);

	void swapItems(int param0, int param1);

	Font fetchFont();

	Sprite fetchInventorySprite(int param0);

	Model fetchModel(SequenceDefinition param0, int param1, boolean param2, PlayerAppearance param3);

	SpriteMask fetchSpriteMask(boolean param0);

	void setAction(int param0, String param1);

	int getRenderParentId();

	void setRenderParentId(int value);

	int getRenderX();

	void setRenderX(int value);

	int getRenderY();

	void setRenderY(int value);

	Rectangle bounds();

	Widget getChild(int param0);

	Widget getParent();

	boolean isVisible();

	int parentId();

	int widgetId();

	Widget[] getDynamicChildren();

	Widget[] getStaticChildren();

	Widget[] getNestedChildren();
}
