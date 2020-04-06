package io.autorune.osrs.api;

import io.autorune.osrs.api.io.RSByteBuffer;
import java.lang.String;
import java.util.LinkedHashMap;

public interface ClientPreferences {
	boolean getRoofsHidden();

	void setRoofsHidden(boolean value);

	boolean getTitleMusicDisabled();

	void setTitleMusicDisabled(boolean value);

	int getWindowMode();

	void setWindowMode(int value);

	LinkedHashMap getParameters();

	void setParameters(LinkedHashMap value);

	String getRememberedUsername();

	void setRememberedUsername(String value);

	boolean getHideUsername();

	void setHideUsername(boolean value);

	Client getClientInstance();

	RSByteBuffer toBuffer();
}
