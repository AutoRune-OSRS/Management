package io.autorune.osrs.api;

import java.lang.String;
import java.lang.Throwable;

public interface RSException {
	Throwable getThrowable();

	void setThrowable(Throwable value);

	String getReason();

	void setReason(String value);

	Client getClientInstance();
}
