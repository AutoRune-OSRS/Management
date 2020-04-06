package io.autorune.osrs.api.cache;

import io.autorune.osrs.api.Client;
import java.lang.Runnable;

public interface ArchiveDiskActionHandler extends Runnable {
	Client getClientInstance();
}
