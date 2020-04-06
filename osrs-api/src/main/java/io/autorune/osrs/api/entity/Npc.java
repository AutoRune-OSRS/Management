package io.autorune.osrs.api.entity;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.definitions.NpcDefinition;
import io.autorune.osrs.api.model.Model;
import java.awt.Shape;

public interface Npc extends Actor {
	NpcDefinition getDefinition();

	void setDefinition(NpcDefinition value);

	Client getClientInstance();

	Model model();

	Shape fetchNpcConvexHull();
}
