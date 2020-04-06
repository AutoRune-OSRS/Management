package io.autorune.osrs.api.collection;

import io.autorune.osrs.api.Client;
import java.util.Iterator;

public interface HashTableIterator extends Iterator {
	Client getClientInstance();
}
