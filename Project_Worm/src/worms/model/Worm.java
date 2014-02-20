package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import worms.util.Position;

/**
 * Defensive
 * Position DONE
 * Shape DONE
 * Mass DONE
 * Name
 * 
 * Nominal
 * Direction DONE
 * 
 * Total
 * ActionPoint
 */




/**
 * 
 * 
 * 
 * TODO: Implement this whole class.
 * @author Admin
 *
 */
public class Worm {
	
	/**
	 * Returns the position of this worm.
	 */
	@Basic
	public Position getPosition() {
		return position;
	}
	
	/**
	 * Set the new position of this worm.
	 * @param position The new position of this worm.
	 * 
	 * @post	This worm's position is equal to the given position.
	 * 			| new.getPosition() == position
	 */
	public void setPosition(Position position) {
		this.position = position;
	}
	
	private Position position;
	

	/**
	 * Returns the angle of this worm.
	 */
	@Basic
	public double getAngle() {
		return angle;
	}
	
	/**
	 * Set the new angle of this worm.
	 * @param angle The new angle of this worm.
	 * 
	 * @pre		The angle provided has to be greater than or equal to 0 and less than 2*Math.PI.
	 * 			| (angle >= 0 && angle < 2*Math.PI)
	 * 
	 * @post	The angle of this worm is equal to the given angle.
	 * 			| (new.getAngle() == angle)
	 */
	public void setAngle(double angle) {
		assert (angle >= 0 && angle < 2*Math.PI);
		this.angle = angle;
	}
	
	private double angle;
	

	/**
	 * Returns the radius of this worm.
	 */
	@Basic
	public double getRadius() {
		return radius;
	}
	
	/**
	 * Set the new radius of this worm and update the mass accordingly.
	 * @param radius
	 * 			The new radius of this worm.
	 * 
	 * @post	The radius of this worm is equal to the given radius.
	 * 			| new.getRadius() == radius
	 * @post	see getMass()
	 * 
	 * @throws IllegalArgumentException
	 * 			When the given radius is less than the minimum radius.
	 * 			| radius < getMinimumRadius()
	 */
	public void setRadius(double radius) throws IllegalArgumentException {
		if(radius < getMinimumRadius())
			throw new IllegalArgumentException("The radius has to be greater than or equal to the minimum radius " + minRadius);
		
		this.radius = radius;
		this.setMass();
	}
	
	/**
	 * Returns the minimum radius of this worm.
	 */
	@Basic @Immutable
	public double getMinimumRadius() {
		return minRadius;
	}
	
	private double radius;
	private final double minRadius = 0.25; //TODO: Initialize in constructor
	
	/**
	 * Return the mass of this worm.
	 */
	@Basic
	public double getMass() {
		return mass;
	}
	
	/**
	 * Sets the mass of this worm.
	 * @post	The mass of this worm is equal to the result of the formula "Mass = (density) * (4/3) * Math.PI * (radius)^3"
	 * 			| new.getMass() == DENSITY * (4/3) * Math.PI * Math.pow(this.getRadius(),3)
	 * 
	 */
	private void setMass() {//TODO: Do we have to check possible overFlow? Double.MAX_VALUE == 1E308?
		this.mass = DENSITY * (4/3) * Math.PI * Math.pow(this.getRadius(),3);
	}
	
	private double mass;
	private static final int DENSITY = 1062;
	
	
	
	
	

	
	
	
	
	
}
