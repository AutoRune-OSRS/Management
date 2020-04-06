package io.autorune.osrs.api.collection;

import io.autorune.osrs.api.Client;

public interface Link {
	Link getNext();

	void setNext(Link value);

	Link getPrevious();

	void setPrevious(Link value);

	Client getClientInstance();
}
