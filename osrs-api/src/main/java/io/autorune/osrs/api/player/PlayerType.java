package io.autorune.osrs.api.player;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.RSEnum;

public interface PlayerType extends RSEnum {
	Client getClientInstance();
}
