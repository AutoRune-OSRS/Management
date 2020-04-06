package io.autorune.osrs.api.url;

import io.autorune.osrs.api.Client;
import java.lang.Runnable;

public interface UrlRequestHandler extends Runnable {
	Client getClientInstance();
}
