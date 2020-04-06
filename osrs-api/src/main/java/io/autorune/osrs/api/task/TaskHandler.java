package io.autorune.osrs.api.task;

import io.autorune.osrs.api.Client;
import java.lang.Runnable;

public interface TaskHandler extends Runnable {
	Client getClientInstance();
}
