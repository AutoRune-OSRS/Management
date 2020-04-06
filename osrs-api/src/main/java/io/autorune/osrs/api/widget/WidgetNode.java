package io.autorune.osrs.api.widget;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.Node;

public interface WidgetNode extends Node {
	int getGroup();

	void setGroup(int value);

	int getId();

	void setId(int value);

	Client getClientInstance();
}
