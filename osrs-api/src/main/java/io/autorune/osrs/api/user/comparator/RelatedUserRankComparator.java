package io.autorune.osrs.api.user.comparator;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.comparator.AbstractComparator;

public interface RelatedUserRankComparator extends AbstractComparator {
	Client getClientInstance();
}
