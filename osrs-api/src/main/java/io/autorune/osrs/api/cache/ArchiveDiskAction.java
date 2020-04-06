package io.autorune.osrs.api.cache;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.Node;

public interface ArchiveDiskAction extends Node {
	Client getClientInstance();
}
