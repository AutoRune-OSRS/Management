package io.autorune.osrs.api.grandexchange;

import io.autorune.osrs.api.Client;

public interface GrandExchangeOffer {
	byte getState();

	void setState(byte value);

	int getId();

	void setId(int value);

	int getUnitPrice();

	void setUnitPrice(int value);

	int getTotalQuantity();

	void setTotalQuantity(int value);

	int getCurrentQuantity();

	void setCurrentQuantity(int value);

	int getCurrentPrice();

	void setCurrentPrice(int value);

	Client getClientInstance();
}
