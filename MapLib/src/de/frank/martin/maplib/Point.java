package de.frank.martin.maplib;

import de.frank.martin.drawlib.PanScale;

public interface Point extends PanScale{
	
	int xScaled();
	
	int yScaled();

	void set(int x, int y);

	int x();

	int y();
	
}
