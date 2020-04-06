package io.autorune.osrs.api.collection;

import io.autorune.osrs.api.Client;
import java.lang.Iterable;
import java.util.Collection;

public interface IterableNodeList extends Iterable, Collection {
	Node getSentinel();

	void setSentinel(Node value);

	Node getCurrent();

	void setCurrent(Node value);

	Client getClientInstance();

	void clear();

	void addFirst(Node param0);

	void addLast(Node param0);
}
