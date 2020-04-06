package io.autorune.osrs.api.user;

import io.autorune.osrs.api.Client;

public interface IgnoredUser extends BasicUser {
	Client getClientInstance();
}
