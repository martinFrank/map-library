package de.frank.martin.maplib;

/**
 * The walker is used by the astar algortihm from the map - the walker 'walks'
 * through a map and determines how difficult it is to enter a certain field
 * 
 * @author martinFrank
 *
 * @param <T> any desired field object
 */
public abstract class Walker<T,V,U> {

	/**
	 * the walker returns false if the field cannot be entered, otherwise true - if a field can be entered the distance is used to calculate the accessibility of a field
	 * @param from start field
	 * @param into destination field
	 * @return 
	 */
	public abstract boolean canEnter(MapField<T,V,U> from, MapField<T,V,U> into);

	/**
	 * some field can be accessed easier than other, eg. 'swamp' have a higher walking cost than grass plains - the amount of walk cost is determined here
	 * @param from start field
	 * @param into destination field
	 * @param style sometimes the walk costs depend on the map style
	 * @return walking costs
	 */
	public int getEnterCosts(MapField<T,V,U> from, MapField<T,V,U> into, MapStyle style) {
		// FIXME diagonale kosten
		// diagonal = 14 (nur bei MapStyle.SQUARE8)
		// ansonsten immer 10
		// if(style == MapStyle.SQUARE8){}
		return 10;
	}

}
