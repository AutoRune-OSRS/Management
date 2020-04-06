package io.autorune.osrs.api.collection;

import io.autorune.osrs.api.Client;
import java.lang.Object;

public interface ObjectNode extends Node {
	Object getObject();

	Client getClientInstance();
}
