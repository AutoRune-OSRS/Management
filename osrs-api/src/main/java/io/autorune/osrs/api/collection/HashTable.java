package io.autorune.osrs.api.collection;

import io.autorune.osrs.api.Client;

public interface HashTable {
	Client getClientInstance();

	Node fetch(long param0);

	void insert(Node param0, long param1);

	Node next();

	Node first();
}
