package io.autorune.osrs.api.collection;

import io.autorune.osrs.api.Client;
import java.lang.Iterable;

public interface IterableDoublyQueue extends Iterable {
	Client getClientInstance();
}
