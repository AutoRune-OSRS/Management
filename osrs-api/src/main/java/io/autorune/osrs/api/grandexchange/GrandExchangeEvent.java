package io.autorune.osrs.api.grandexchange;

import io.autorune.osrs.api.Client;

public interface GrandExchangeEvent {
	int getWorld();

	long getAge();

	GrandExchangeOffer getOffer();

	Client getClientInstance();
}
