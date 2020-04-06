package io.autorune.osrs.api;

import io.autorune.osrs.api.devices.MouseWheel;
import io.autorune.osrs.api.devices.MouseWheelListener;
import java.awt.Canvas;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.datatransfer.Clipboard;
import java.awt.event.FocusListener;
import java.awt.event.WindowListener;
import java.lang.Object;
import java.lang.Runnable;
import java.lang.String;

public interface GameShell extends Runnable, FocusListener, WindowListener {
	Canvas getCanvas();

	void setCanvas(Canvas value);

	Frame getFrame();

	void setFrame(Frame value);

	Clipboard getClipboard();

	void setClipboard(Clipboard value);

	EventQueue getEventQueue();

	boolean getAwtFocus();

	void setAwtFocus(boolean value);

	int getCanvasWidth();

	void setCanvasWidth(int value);

	int getCanvasHeight();

	void setCanvasHeight(int value);

	MouseWheelListener getMouseWheelListener();

	void setMouseWheelListener(MouseWheelListener value);

	Client getClientInstance();

	void maxCanvasSize(int param0, int param1);

	void post(Object param0);

	MouseWheel mouseWheel();

	void setupClipboard();

	void setupClipboardSettings(String param0);

	void setupKeyboard();

	void setupMouse();

	void resizeCanvas();

	void clientTick();

	void graphicsTick();
}
