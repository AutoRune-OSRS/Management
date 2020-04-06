package io.autorune.osrs.api.grandexchange;

import io.autorune.osrs.api.Client;
import java.util.Comparator;

public interface GrandExchangeOfferNameComparator extends Comparator {
	Client getClientInstance();
}
