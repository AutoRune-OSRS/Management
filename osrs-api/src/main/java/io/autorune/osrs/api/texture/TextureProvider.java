package io.autorune.osrs.api.texture;

import io.autorune.osrs.api.Client;

public interface TextureProvider extends TextureLoader {
	Client getClientInstance();
}
