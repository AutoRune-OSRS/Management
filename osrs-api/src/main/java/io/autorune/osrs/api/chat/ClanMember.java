package io.autorune.osrs.api.chat;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.user.RelatedUser;

public interface ClanMember extends RelatedUser {
	Client getClientInstance();
}
