package io.autorune.osrs.api.user;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.Link;

public interface UserLoginUpdate extends Link {
	Client getClientInstance();
}
