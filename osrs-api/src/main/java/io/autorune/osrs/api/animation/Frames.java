package io.autorune.osrs.api.animation;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.DoublyNode;

public interface Frames extends DoublyNode {
	Frame[] getFrames();

	void setFrames(Frame[] value);

	Client getClientInstance();

	boolean hasAlphaTransformation(int param0);
}
