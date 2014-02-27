package worms.model;

import be.kuleuven.cs.som.annotate.*;
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
 * @invar	This worm's mass is at all times equal to the mass if we would set the mass.
 * 			| this.getMass() == this.setMass() //TODO: (vraag) How to describe this best?
 * 
 * @invar	This worm's radius is at all times equal to or higher than the minimum radius.
 * 			| this.getRadius() >= this.getMinimumRadius()
 * 
 * TODO: (vraag) Double.isNaN never valid also invariant?
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
	 * 
	 * @throws NullPointerException
	 * 			When position is null.
	 * 			| position == null
	 */
	public void setPosition(Position position) throws NullPointerException {
		if(position == null)
			throw new NullPointerException();
		
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
		assert (angle >= 0 && angle < 2*Math.PI); //TODO: (vraag) invariant? isValidAngle??
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
	 * @post	see setMass() //TODO: (vraag) @effect?
	 * 
	 * @throws IllegalArgumentException
	 * 			When the given radius is less than the minimum radius.
	 * 			| radius < this.getMinimumRadius()
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
	 * Returns the mass of this worm.
	 */
	@Basic
	public double getMass() {
		return mass;
	}
	
	/**
	 * Sets the mass of this worm.
	 * @post	The mass of this worm is equal to the result of the formula "Mass = (getDensity()) * (4/3) * Math.PI * (radius)^3"
	 * 			| new.getMass() == getDensity() * (4/3) * Math.PI * Math.pow(this.getRadius(),3)
	 * 
	 * @post	see setCurrentActionPoints(this.getCurrentActionPoints()) //TODO: (vraag) @effect?
	 */
	@Raw
	private void setMass() /*throws IllegalArgumentException*/ {//TODO: (vraag) Do we have to check possible overFlow?
		//We'd better use an argument if we use this...
		/*if(this.radius >= Math.pow(Double.MAX_VALUE * (3/4) / DENSITY / Math.PI, 1/3))
			throw new IllegalArgumentException();*/
			
		this.mass = getDensity() * (4/3) * Math.PI * Math.pow(this.getRadius(),3);
		this.setCurrentActionPoints(this.getCurrentActionPoints());
	}
	
	/**
	 * Returns this worm's density.
	 */
	@Basic @Immutable
	private static double getDensity() {
		return DENSITY;
	}
	
	private double mass;
	private static final double DENSITY = 1062;
	
	
	/**
	 * Returns this worm's name.
	 */
	@Basic
	public String getName() {
		return name;
	}
	
	/**
	 * Set a new name for this worm.
	 * @param name The new name of this worm.
	 * 
	 * @post	new.getName() == name
	 * 
	 * @throws IllegalArgumentException
	 * 			When name isn't a valid name.
	 * 			| !isValidName(name)
	 */
	public void setName(String name) throws IllegalArgumentException {
		if (!isValidName(name))
			throw new IllegalArgumentException();
		
		this.name = name;
	}
	
	/**
	 * Returns whether the name is a valid name.
	 * @param name The name to be checked.
	 * 
	 * @return  True if the name is longer than 2 characters, starts with an uppercase and when every character is one from the following: [A-Z] || [a-z] || a space || ' || " 
	 * 			| result != ((name == null) &&
	 * 			| (name.length() < 2) &&
	 * 			| (!Character.isUpperCase(name.charAt(0)) &&
	 * 			| (for each index i in 0..name.toCharArray().length-1:
     *       	|   (!(Character.isLetter(name.toCharArray[i]) || name.toCharArray[i] == ' ' || name.toCharArray[i] == '\'' || name.toCharArray[i] == '\"'))))
	 */
	public static boolean isValidName(String name) {
		if(name == null)
			return false;
		if(name.length() < 2)
			return false;
		if(!Character.isUpperCase(name.charAt(0)))
				return false;
		for(Character ch : name.toCharArray()) {
			if(!(Character.isLetter(ch) || ch == ' ' || ch == '\'' || ch == '\"'))
				return false;
		}
		return true;
	}
	
	private String name;
	
	/**
	 * Set the current action points.
	 * @param actionPoints The new action points amount.
	 * 
	 * @post	If actionPoints is greater than the maximum allowed the maximum will be set.
	 * 			If actionPoints is less than or equal to the maximum allowed, the current action points will be set to actionPoints. 
	 * 			| new.getCurrentActionPoints() == (actionPoints > this.getMaxActionPoints()) ? this.getMaxActionPoints() : actionPoints;
	 */
	public void setCurrentActionPoints(int actionPoints) {
		this.currentActionPoints = (actionPoints > getMaxActionPoints()) ? getMaxActionPoints() : actionPoints;
	}
	
	/**
	 * Returns the current action points amount.
	 */
	@Basic 
	public int getCurrentActionPoints() {
		return currentActionPoints;
	}
	
	/**
	 * Returns the maximum amount of action points.
	 */
	private int getMaxActionPoints() {
		if(this.getMass() > Integer.MAX_VALUE)
			return Integer.MAX_VALUE;
		
		return (int) Math.round(this.getMass());
	}
	
	private int currentActionPoints;
	
	
	
	
	
	

	
	
	
	
	
}
