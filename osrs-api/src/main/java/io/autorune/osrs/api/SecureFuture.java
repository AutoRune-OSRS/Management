package io.autorune.osrs.api;

import java.security.SecureRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public interface SecureFuture {
	ExecutorService getExecutorService();

	void setExecutorService(ExecutorService value);

	Future getFuture();

	void setFuture(Future value);

	Client getClientInstance();

	SecureRandom instance();

	boolean done();

	void shutdown();
}
