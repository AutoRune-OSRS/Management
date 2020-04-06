package io.autorune.osrs.api.cache;

import io.autorune.osrs.api.Client;

public interface Archive extends AbstractArchive {
	Client getClientInstance();
}
