package io.autorune.osrs.api.grandexchange;

import io.autorune.osrs.api.Client;
import java.util.Comparator;

public interface GrandExchangeOfferUnitPriceComparator extends Comparator {
	Client getClientInstance();
}
