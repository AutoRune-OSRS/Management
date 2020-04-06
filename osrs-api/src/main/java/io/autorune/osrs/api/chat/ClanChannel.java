package io.autorune.osrs.api.chat;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.user.UserList;

public interface ClanChannel extends UserList {
	Client getClientInstance();
}
