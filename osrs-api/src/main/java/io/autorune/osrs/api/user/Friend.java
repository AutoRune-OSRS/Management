package io.autorune.osrs.api.user;

import io.autorune.osrs.api.Client;

public interface Friend extends RelatedUser {
	Client getClientInstance();
}
