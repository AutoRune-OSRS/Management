package io.autorune.osrs.api.io;

import io.autorune.osrs.api.Client;
import java.lang.Runnable;

public interface BufferedSink extends Runnable {
	Client getClientInstance();
}
