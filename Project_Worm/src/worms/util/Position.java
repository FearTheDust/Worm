package worms.util;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

/**
 * 
 * A class representing a position in a 2 dimensional space, represented by 2 coordinates.
 * 
 * @invar 	The x- and y-coordinates are always valid.
 * 			| isValidPosition(this.getX(), this.getY())
 * 
 *  @author Admin
 *  @version 1.0
 */

public class Position {
	
	/**
	 * Initialize this new position with the given x and y coordinates.
	 * @param x The x-coordinate of this new position.
	 * @param y The y-coordinate of this new position.
	 * 
	 * @post	The x-coordinate of this new position is equal to the given x.
	 * 			| new.getX() == x
	 * 
	 * @post	The y-coordinate of this new position is equal to the given y.
	 * 			| new.getY() == y
	 * 
	 * @throws	IllegalArgumentException
	 * 			| !isValidPosition(x,y)
	 */
	public Position(double x, double y) throws IllegalArgumentException {
		if(!isValidPosition(x,y))
			throw new IllegalArgumentException("A negative Coordinate is not allowed.");
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Check whether the x and y given are valid arguments for a position.
	 * @param x The x-coordinate to check.
	 * @param y The y-coordinate to check.
	 * 
	 * @return True if and only if the given x and y are both greater than or equal to 0.
	 */
	public static boolean isValidPosition(double x, double y) {
		return (x >= 0 && y >= 0);
	}
	
	/**
	 * Return the X-coordinate of this position.
	 */
	@Basic @Immutable
	public double getX() {
		return x;
	}
	
	private final double x;
	
	
	
	/**
	 * Return the Y-coordinate of this position.
	 */
	@Basic @Immutable
	public double getY() {
		return y;
	}

	private final double y;
	
	
	

}
