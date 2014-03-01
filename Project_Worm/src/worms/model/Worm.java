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
	 * Move this worm a certain amount of steps in the direction it's facing.
	 * @param steps The amount of steps this worm takes.
	 * @post	The new position is the old position plus
	 * 				the amount of steps proportional to the direction this worm is facing.
	 * 			| new.getPosition()==new Position(
	 * 				this.getPosition().getX()+steps*Math.cos(this.getAngle())*this.getRadius(),
	 * 				this.getPosition().getY()+steps*Math.sin(this.getAngle())*this.getRadius())
	 * @post	The new action points is equal to the old action points minus the 
	 * 				cost to move.
	 * 			| new.getCurrentActionPoints()==
	 * 				this.getCurrentActionPoints()-getMoveCost(steps,this.getAngle())
	 * @throws IllegalArgumentException
	 * 			If steps is smaller than zero or if this worm doesn't have enough action points.
	 * 			| steps<0 || this.getMoveCost(steps,this.getAngle())>this.getCurrentActionPoints()
	 */
	public void move(int steps) throws IllegalArgumentException{
		if(steps<0)
			throw new IllegalArgumentException("Steps must be higher than or equal to zero");
		if(getMoveCost(steps,getAngle())>getCurrentActionPoints())
			throw new IllegalArgumentException("You don't have enough Action Points");
		double x=this.getPosition().getX()+steps*Math.cos(getAngle())*getRadius();
		double y=this.getPosition().getY()+steps*Math.sin(getAngle())*getRadius();
		setPosition(new Position(x,y));
		int actionPoints=getCurrentActionPoints()-getMoveCost(steps,getAngle());
		setCurrentActionPoints(actionPoints);
	}
	
	/**
	 * Returns the cost to move a certain amount of steps to move in a certain direction.
	 * @param steps The amount of steps to move.
	 * @param angle Determines the direction to move in.
	 * @return 	The cost to move.
	 * 			| (steps*Math.ceil((Math.abs(Math.cos(angle))
	 *			+Math.abs(4*Math.sin(angle)))))
	 */
	public static int getMoveCost(int steps, double angle){
		return (int) (steps*Math.ceil((Math.abs(Math.cos(angle))
				+Math.abs(4*Math.sin(angle)))));
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
	private void setPosition(Position position) throws NullPointerException {
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
	 * Turn this worm with a given angle.
	 * @param angle The angle to turn.
	 * @pre		The angle must be between -2*Math.PI and 2*Math.PI
	 * 			| (angle > -2*Math.PI && angle < 2*Math.PI) 				//TODO: (vraag) isValidAngle(Math.abs) && isValidAngle() ...????
	 * @pre		The cost to turn should be less or equal to the amount we have.
	 * 			| this.getCurrentActionPoints() >= getTurnCost(angle)
	 * 
	 * @post	The new worm's action points is equal to the old amount minus the cost to turn.
	 * 			| new.getCurrentActionPoints() = this.getCurrentActionPoints() - getTurnCost(angle)
	 * @post	The new angle is equal to the old angle + the given angle.
	 * 			| new.getAngle() = this.getAngle() + angle
	 */
	public void turn(double angle) {
		assert (angle > -2*Math.PI && angle < 2*Math.PI);
		assert this.getCurrentActionPoints() >= getTurnCost(angle);
		
		this.setAngle(this.getAngle() + angle);
		this.setCurrentActionPoints(this.getCurrentActionPoints() - getTurnCost(angle));
	}
	
	/**
	 * Returns the cost to move the angle.
	 * @param angle The angle to turn.
	 * @return The cost to turn.
	 */
	public static int getTurnCost(double angle) {
		return (int) ((60 * angle) /2*Math.PI);
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
