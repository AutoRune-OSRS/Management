package io.autorune.osrs.api.collection;

import io.autorune.osrs.api.Client;
import java.lang.Iterable;

public interface IterableHashTable extends Iterable {
	Client getClientInstance();
}
