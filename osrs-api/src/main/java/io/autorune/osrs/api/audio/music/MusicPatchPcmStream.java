package io.autorune.osrs.api.audio.music;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.audio.pcm.PcmStream;

public interface MusicPatchPcmStream extends PcmStream {
	Client getClientInstance();
}
