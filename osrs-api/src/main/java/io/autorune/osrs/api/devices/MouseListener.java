package io.autorune.osrs.api.devices;

import io.autorune.osrs.api.Client;
import java.awt.event.FocusListener;
import java.awt.event.MouseMotionListener;

public interface MouseListener extends java.awt.event.MouseListener, MouseMotionListener, FocusListener {
	int getMouseX();

	void setMouseX(int value);

	int getMouseY();

	void setMouseY(int value);

	Client getClientInstance();

	boolean getIsInputBlocked();

	void setIsInputBlocked(boolean value);
}
