package io.autorune.osrs.api.audio;

import io.autorune.osrs.api.Client;
import java.lang.Runnable;

public interface SoundSystem extends Runnable {
	Client getClientInstance();
}
