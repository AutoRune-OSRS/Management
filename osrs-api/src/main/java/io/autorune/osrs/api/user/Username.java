package io.autorune.osrs.api.user;

import io.autorune.osrs.api.Client;
import java.lang.Comparable;
import java.lang.String;

public interface Username extends Comparable {
	String getCleanName();

	void setCleanName(String value);

	String getName();

	void setName(String value);

	Client getClientInstance();

	boolean hasCleanName();
}
