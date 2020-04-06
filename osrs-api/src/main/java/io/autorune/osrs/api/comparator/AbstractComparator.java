package io.autorune.osrs.api.comparator;

import io.autorune.osrs.api.Client;
import java.util.Comparator;

public interface AbstractComparator extends Comparator {
	Client getClientInstance();
}
