package io.autorune.osrs.api;

import java.util.concurrent.Callable;

public interface SecureCallable extends Callable {
	Client getClientInstance();
}
