package io.autorune.osrs.api.graphics;

import io.autorune.osrs.api.Client;
import java.awt.Graphics;
import java.awt.Image;

public interface RasterProvider extends AbstractRasterProvider {
	Image getImage();

	void setImage(Image value);

	Client getClientInstance();

	void drawFull(Graphics param0, int param1, int param2);
}
