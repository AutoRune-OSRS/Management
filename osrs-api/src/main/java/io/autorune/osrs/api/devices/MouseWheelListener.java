package io.autorune.osrs.api.devices;

import io.autorune.osrs.api.Client;

public interface MouseWheelListener extends MouseWheel, java.awt.event.MouseWheelListener {
	int getMouseWheelRotation();

	void setMouseWheelRotation(int value);

	Client getClientInstance();

	boolean getIsInputBlocked();

	void setIsInputBlocked(boolean value);
}
