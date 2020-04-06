package io.autorune.osrs.api.audio.music;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.Node;

public interface MusicTrack extends Node {
	Client getClientInstance();
}
