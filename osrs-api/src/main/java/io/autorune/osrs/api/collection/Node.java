package io.autorune.osrs.api.collection;

import io.autorune.osrs.api.Client;

public interface Node {
	Node getNext();

	void setNext(Node value);

	Node getPrevious();

	void setPrevious(Node value);

	long getHash();

	void setHash(long value);

	Client getClientInstance();
}
