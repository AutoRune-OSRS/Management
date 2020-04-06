package io.autorune.osrs.api.collection;

import io.autorune.osrs.api.Client;
import java.util.Iterator;

public interface NodeListIterator extends Iterator {
	Client getClientInstance();
}
