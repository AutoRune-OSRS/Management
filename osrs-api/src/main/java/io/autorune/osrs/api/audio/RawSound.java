package io.autorune.osrs.api.audio;

import io.autorune.osrs.api.Client;

public interface RawSound extends AbstractSound {
	Client getClientInstance();
}
