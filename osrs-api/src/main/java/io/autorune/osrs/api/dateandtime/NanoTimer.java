package io.autorune.osrs.api.dateandtime;

import io.autorune.osrs.api.Client;

public interface NanoTimer extends AbstractTimer {
	Client getClientInstance();
}
