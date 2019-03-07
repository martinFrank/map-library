package de.elite.games.maplib;

/**
 * The walker is used by the astar algortihm from the map - the walker 'walks'
 * through a map and determines how difficult it is to enter a certain field
 * 
 * @author martinFrank
 *
 *  */
public abstract class Walker<T extends MapField<?, ?, ?>> {


	private final MapStyle style;

	public Walker(MapStyle style) {
		this.style = style;
	}

	/**
	 * the walker returns false if the field cannot be entered, otherwise true - if a field can be entered the distance is used to calculate the accessibility of a field
	 * @param from start field
	 * @param into destination field
	 * @return 
	 */
	public abstract boolean canEnter(T from, T into);

	/**
	 * some field can be accessed easier than other, eg. 'swamp' have a higher walking cost than grass plains - the amount of walk cost is determined here
	 * @param from start field
	 * @param into destination field
	 * @return walking costs
	 */
	public int getEnterCosts(T from, T into) {
		// FIXME diagonale kosten:
		// diagonal = 14 (nur bei MapStyle.SQUARE8)
		// ansonsten immer 10
//		 if(style == MapStyle.SQUARE8){ return 14;}
		return 10;
	}


}
