package io.autorune.osrs.api.collection;

import io.autorune.osrs.api.Client;

public interface NodeCache {
	DoublyNode getDoublyNode();

	void setDoublyNode(DoublyNode value);

	int getSize();

	void setSize(int value);

	int getRemaining();

	void setRemaining(int value);

	IterableHashTable getIterableHashTable();

	void setIterableHashTable(IterableHashTable value);

	IterableDoublyQueue getDoublyNodeList();

	void setDoublyNodeList(IterableDoublyQueue value);

	Client getClientInstance();
}
