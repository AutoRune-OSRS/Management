package io.autorune.osrs.api.io;

import io.autorune.osrs.api.Client;
import java.lang.Runnable;

public interface NetSocket extends AbstractSocket, Runnable {
	Client getClientInstance();
}
