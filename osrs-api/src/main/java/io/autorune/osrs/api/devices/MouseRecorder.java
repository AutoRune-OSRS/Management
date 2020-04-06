package io.autorune.osrs.api.devices;

import io.autorune.osrs.api.Client;
import java.lang.Runnable;

public interface MouseRecorder extends Runnable {
	Client getClientInstance();
}
