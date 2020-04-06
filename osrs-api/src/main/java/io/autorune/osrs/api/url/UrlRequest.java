package io.autorune.osrs.api.url;

import io.autorune.osrs.api.Client;

public interface UrlRequest {
	Client getClientInstance();

	boolean isDone();
}
