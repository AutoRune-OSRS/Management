package io.autorune.osrs.api;

import io.autorune.osrs.api.collection.DoublyNode;
import java.lang.Object;

public interface Wrapper extends DoublyNode {
	Client getClientInstance();

	Object instance();

	boolean isSoft();
}
