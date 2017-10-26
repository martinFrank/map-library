package de.elite.games.maplib;

import de.elite.games.drawlib.PanScale;

public interface Point extends PanScale{
	
	int xScaled();
	
	int yScaled();

	void set(int x, int y);

	int x();

	int y();
	
}
