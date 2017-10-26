package de.elite.games.maplib;

public abstract class Walker<T> {

	public abstract boolean canEnter(Field<T> from, Field<T> into);

	public int getDistance(Field<T> center, Field<T> to, MapStyle style) {
		//FIXME diagonale kosten
		//diagonal = 14 (nur bei MapStyle.SQUARE8)
		//ansonsten immer 10 		
		//if(style == MapStyle.SQUARE8){}
		return 10;
	}
	


}
