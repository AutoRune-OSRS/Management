package io.autorune.osrs.api.chat;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.DoublyNode;

public interface Message extends DoublyNode {
	Client getClientInstance();
}
