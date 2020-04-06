package io.autorune.osrs.api.audio.pcm;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.Node;

public interface PcmStreamMixerListener extends Node {
	Client getClientInstance();
}
