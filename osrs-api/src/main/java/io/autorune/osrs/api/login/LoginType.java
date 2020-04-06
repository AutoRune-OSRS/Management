package io.autorune.osrs.api.login;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.RSEnum;

public interface LoginType extends RSEnum {
	Client getClientInstance();
}
