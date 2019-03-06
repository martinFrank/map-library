package de.elite.games.maplib;

/**
 * The walker is used by the astar algortihm from the map - the walker 'walks'
 * through a map and determines how difficult it is to enter a certain field
 * 
 * @author martinFrank
 *
 * @param <F> any desired field data object
 * @param <E> any desired edge data object
 * @param <P> any desired point data object
 */
public abstract class Walker<F,E,P> {

	/**
	 * the walker returns false if the field cannot be entered, otherwise true - if a field can be entered the distance is used to calculate the accessibility of a field
	 * @param from start field
	 * @param into destination field
	 * @return 
	 */
	public abstract boolean canEnter(MapField<F,E,P> from, MapField<F,E,P> into);

	/**
	 * some field can be accessed easier than other, eg. 'swamp' have a higher walking cost than grass plains - the amount of walk cost is determined here
	 * @param from start field
	 * @param into destination field
	 * @return walking costs
	 */
	public int getEnterCosts(MapField<F,E,P> from, MapField<F,E,P> into) {
		// FIXME diagonale kosten:
		// diagonal = 14 (nur bei MapStyle.SQUARE8)
		// ansonsten immer 10
		// if(style == MapStyle.SQUARE8){}
		return 10;
	}

}
