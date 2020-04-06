package io.autorune.osrs.api.devices;

import io.autorune.osrs.api.Client;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;

public interface KeyboardListener extends KeyListener, FocusListener {
	Client getClientInstance();

	boolean getIsInputBlocked();

	void setIsInputBlocked(boolean value);
}
