package worms.util;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class representing a position in a 2 dimensional space, represented by 2 coordinates x and y.
 * 
 * @invar 	The x- or y-coordinates are never "Not a Number".
 * 			| !Double.isNaN(this.getX()) && !Double.isNan(this.getY())
 * 
 *  @author Derkinderen Vincent
 *  @author Coosemans Brent
 *  
 *  @version 1.1 //TODO: moet dat erbij?
 */

public class Position {
	
	/**
	 * Initialize this new position with the given x and y coordinates.
	 * 
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
	 * 			When x- or y-coordinate aren't valid coordinates.
	 * 			| Double.isNaN(x) || Double.isNaN(y)
	 */
	@Raw
	public Position(double x, double y) throws IllegalArgumentException {
		if(Double.isNaN(x) || Double.isNaN(y))
			throw new IllegalArgumentException("A coordinate was Not a Number.");
		
		this.x = x;
		this.y = y;
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
	
	@Override
	public boolean equals(Object otherObject) { //TODO: Implement a test
		if(otherObject instanceof Position) {
			Position otherPosition = (Position) otherObject;
			return (this.getX() == otherPosition.getX() && this.getY() == otherPosition.getY());
		}
		return false;
	}
}
