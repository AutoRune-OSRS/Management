package io.autorune.osrs.api;

import io.autorune.osrs.api.collection.HashTable;
import io.autorune.osrs.api.definitions.EnumDefinition;
import io.autorune.osrs.api.definitions.HealthBarDefinition;
import io.autorune.osrs.api.definitions.IdentityKitDefinition;
import io.autorune.osrs.api.definitions.InventoryDefinition;
import io.autorune.osrs.api.definitions.ItemDefinition;
import io.autorune.osrs.api.definitions.NpcDefinition;
import io.autorune.osrs.api.definitions.ObjectDefinition;
import io.autorune.osrs.api.definitions.OverlayDefinition;
import io.autorune.osrs.api.definitions.ParameterDefinition;
import io.autorune.osrs.api.definitions.SequenceDefinition;
import io.autorune.osrs.api.definitions.SpotAnimationDefinition;
import io.autorune.osrs.api.definitions.StructDefinition;
import io.autorune.osrs.api.definitions.UnderlayDefinition;
import io.autorune.osrs.api.definitions.VarpDefinition;
import io.autorune.osrs.api.definitions.WorldMapElementDefinition;
import io.autorune.osrs.api.devices.KeyboardListener;
import io.autorune.osrs.api.devices.MouseListener;
import io.autorune.osrs.api.devices.MouseRecorder;
import io.autorune.osrs.api.entity.Actor;
import io.autorune.osrs.api.entity.Npc;
import io.autorune.osrs.api.entity.Player;
import io.autorune.osrs.api.grandexchange.GrandExchangeOffer;
import io.autorune.osrs.api.io.PacketBuffer;
import io.autorune.osrs.api.region.CollisionMap;
import io.autorune.osrs.api.region.Scene;
import io.autorune.osrs.api.sprite.Sprite;
import io.autorune.osrs.api.url.UrlRequest;
import io.autorune.osrs.api.user.User;
import io.autorune.osrs.api.widget.Widget;
import io.autorune.osrs.api.world.World;
import java.lang.Class;
import java.lang.String;

public interface Client extends GameShell, User {
	Client getClientInstance();

	void setClientInstance(Client value);

	int getCycleTick();

	void setCycleTick(int value);

	int getGameState();

	void setGameState(int value);

	int getLoginState();

	void setLoginState(int value);

	int getNpcCount();

	void setNpcCount(int value);

	Npc[] getNpcs();

	void setNpcs(Npc[] value);

	int[] getNpcIndices();

	void setNpcIndices(int[] value);

	int getPlane();

	void setPlane(int value);

	int getCameraX();

	void setCameraX(int value);

	int getCameraZ();

	void setCameraZ(int value);

	int getCameraY();

	void setCameraY(int value);

	int getCameraPitch();

	void setCameraPitch(int value);

	int getCameraYaw();

	void setCameraYaw(int value);

	int getViewportZoom();

	void setViewportZoom(int value);

	int getViewportWidth();

	void setViewportWidth(int value);

	int getViewportHeight();

	void setViewportHeight(int value);

	int getTempViewportX();

	void setTempViewportX(int value);

	int getTempViewportY();

	void setTempViewportY(int value);

	int getViewportOffsetX();

	void setViewportOffsetX(int value);

	int getViewportOffsetY();

	void setViewportOffsetY(int value);

	boolean getIsInInstance();

	void setIsInInstance(boolean value);

	int[][][] getInstanceChunkTemplates();

	void setInstanceChunkTemplates(int[][][] value);

	int getLoginIndex();

	void setLoginIndex(int value);

	String getPassword();

	void setPassword(String value);

	String getUsername();

	void setUsername(String value);

	int getRootWidget();

	void setRootWidget(int value);

	int getGameDrawingMode();

	void setGameDrawingMode(int value);

	int[] getCurrentLevels();

	void setCurrentLevels(int[] value);

	int[] getLevels();

	void setLevels(int[] value);

	int[] getExperience();

	void setExperience(int[] value);

	int[] getExperienceTable();

	void setExperienceTable(int[] value);

	boolean[] getEnabledSkills();

	int getRunEnergy();

	void setRunEnergy(int value);

	int getWeight();

	void setWeight(int value);

	ClientPreferences getClientPreferences();

	void setClientPreferences(ClientPreferences value);

	HashTable getItemContainers();

	void setItemContainers(HashTable value);

	KeyboardListener getKeyboardListener();

	void setKeyboardListener(KeyboardListener value);

	MouseListener getMouseListener();

	void setMouseListener(MouseListener value);

	MouseRecorder getMouseRecorder();

	void setMouseRecorder(MouseRecorder value);

	int getSelectedSpellFlags();

	void setSelectedSpellFlags(int value);

	int getBaseY();

	void setBaseY(int value);

	int getBaseX();

	void setBaseX(int value);

	Player[] getPlayers();

	void setPlayers(Player[] value);

	Player getLocalPlayer();

	void setLocalPlayer(Player value);

	int getLocalPlayerIndex();

	void setLocalPlayerIndex(int value);

	GameShell getGameShell();

	void setGameShell(GameShell value);

	GrandExchangeOffer[] getGrandExchangeOffers();

	void setGrandExchangeOffers(GrandExchangeOffer[] value);

	boolean getContextMenuOpen();

	void setContextMenuOpen(boolean value);

	int getMenuOptionsCount();

	void setMenuOptionsCount(int value);

	String[] getMenuActions();

	void setMenuActions(String[] value);

	int getMenuX();

	void setMenuX(int value);

	int getMenuY();

	void setMenuY(int value);

	int getMenuWidth();

	void setMenuWidth(int value);

	int getMenuHeight();

	void setMenuHeight(int value);

	String[] getMenuTargets();

	void setMenuTargets(String[] value);

	int[] getMenuOpcodes();

	void setMenuOpcodes(int[] value);

	int[] getMenuIdentifiers();

	void setMenuIdentifiers(int[] value);

	int[] getMenuArguments1();

	void setMenuArguments1(int[] value);

	int[] getMenuArguments2();

	void setMenuArguments2(int[] value);

	CollisionMap[] getCollisionMaps();

	void setCollisionMaps(CollisionMap[] value);

	Scene getCurrentScene();

	void setCurrentScene(Scene value);

	byte[][][] getTileSettings();

	void setTileSettings(byte[][][] value);

	int[][][] getTileHeights();

	void setTileHeights(int[][][] value);

	Widget[][] getWidgets();

	void setWidgets(Widget[][] value);

	Widget[] getWidgetCache();

	void setWidgetCache(Widget[] value);

	HashTable getWidgetNodeCache();

	void setWidgetNodeCache(HashTable value);

	World[] getWorlds();

	void setWorlds(World[] value);

	UrlRequest getWorldRequest();

	void setWorldRequest(UrlRequest value);

	void load();

	void addPlayerToScene(Player param0, boolean param1);

	void addSceneMenuOptions(int param0, int param1, int param2, int param3);

	void doCycle();

	void doCycleLoggedOut();

	void doCycleLoggedIn();

	void updateGameState(int param0);

	void addNpcToScene(boolean param0);

	void worldToScreen(int param0, int param1, int param2);

	void viewportShape(int param0, int param1, int param2, int param3, boolean param4);

	void loadRegions(boolean param0, PacketBuffer param1);

	void addChatBoxMessage(int param0, String param1, String param2, String param3);

	void addGameMessage(int param0, String param1, String param2);

	void promptLoginCredentials(boolean param0);

	void updateLoginPreferenceType(boolean param0);

	void logout();

	void drawLoggedIn();

	Class loadClassFromDescriptor(String param0);

	void draw(boolean param0);

	int parseCS2Instruction(Widget param0, int param1);

	String colorTagFromDecimal(int param0);

	EnumDefinition fetchEnumDefinition(int param0);

	HealthBarDefinition fetchHealthBarDefinition(int param0);

	IdentityKitDefinition fetchIdentityKitDefinition(int param0);

	InventoryDefinition fetchInventoryDefinition(int param0);

	ItemDefinition fetchItemDefinition(int param0);

	NpcDefinition fetchNpcDefinition(int param0);

	ObjectDefinition fetchObjectDefinition(int param0);

	OverlayDefinition fetchOverlayDefinition(int param0);

	ParameterDefinition fetchParameterDefinition(int param0);

	SequenceDefinition fetchSequenceDefinition(int param0);

	SpotAnimationDefinition fetchSpotAnimDefinition(int param0);

	StructDefinition fetchStructDefinition(int param0);

	UnderlayDefinition fetchUnderlayDefinition(int param0);

	int fetchVarBitValue(int param0);

	void putVarBitValue(int param0, int param1);

	VarpDefinition fetchVarpDefinition(int param0);

	WorldMapElementDefinition fetchWorldMapDefinition(int param0);

	void readPlayerUpdate(PacketBuffer param0, int param1);

	void selectSpell(int param0, int param1, int param2, int param3);

	void syncActorOrientationAndAngle(Actor param0);

	void updatePlayers(PacketBuffer param0, int param1);

	void sendMenuAction(int param0, int param1, int param2, int param3, String param4, String param5,
			int param6, int param7);

	void insertMenuAction(String param0, String param1, int param2, int param3, int param4, int param5,
			boolean param6);

	void constructContextMenuBounds();

	void addPendingSpawnToScene(int param0, int param1, int param2, int param3, int param4, int param5,
			int param6);

	void runComponentCloseListeners(Widget[] param0, int param1);

	Sprite createItemSprite(int param0, int param1, int param2, int param3, int param4,
			boolean param5);

	void alignWidgetSize(Widget param0, int param1, int param2, boolean param3);

	void drawInterface(Widget[] param0, int param1, int param2, int param3, int param4, int param5,
			int param6, int param7, int param8);

	Widget child(int param0);

	void revalidateWidgetScroll(Widget[] param0, Widget param1, boolean param2);

	void resizeWidget(Widget[] param0, int param1, int param2, int param3, boolean param4);

	void changeWorld(World param0);

	boolean fetchWorldList();

	void openWorldSelect();

	boolean getIsDebugMenuActionsEnabled();

	void setIsDebugMenuActionsEnabled(boolean value);

	Widget getWidget(int param0, int param1);

	String[] realMenuActions();

	void initClassLoader(String param0);

	void initCallback();
}
