package io.autorune.osrs.api.collection;

import io.autorune.osrs.api.Client;
import java.util.Iterator;

public interface DoublyQueueIterator extends Iterator {
	Client getClientInstance();
}
