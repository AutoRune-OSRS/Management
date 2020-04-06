package io.autorune.osrs.api;

import java.lang.String;

public interface Language extends RSEnum {
	String getLanguage();

	int getId();

	Client getClientInstance();

	String language();

	int ordinal();
}
