package io.autorune.osrs.api.collection;

import io.autorune.osrs.api.Client;

public interface DoublyNode extends Node {
	DoublyNode getNext();

	void setNext(DoublyNode value);

	DoublyNode getPrevious();

	void setPrevious(DoublyNode value);

	long getHash();

	void setHash(long value);

	Client getClientInstance();
}
