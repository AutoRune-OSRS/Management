package io.autorune.osrs.api.audio.midi;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.audio.pcm.PcmStream;

public interface MidiPcmStream extends PcmStream {
	Client getClientInstance();
}
