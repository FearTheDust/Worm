package worms.model;

import be.kuleuven.cs.som.annotate.*;
import worms.util.Position;
import worms.util.Util;

/**
 * Defensive
 * Position DONE
 * Shape DONE
 * Mass DONE
 * Name DONE
 * Jumping DONE
 * Moving DONE
 * 
 * Nominal
 * Direction DONE
 * Turning DONE
 * 
 * Total
 * ActionPoint DONE
 */

//TODO: Check access modifiers and modify them to the most suited one.
//TODO: Check invariants

/**
 * 
 * @author Derkinderen Vincent 
 * @author Coosemans Brent
 * 
 * @invar	This worm's action points amount is at all times less than or equal to the maximum amount.
 * 			| this.getCurrentActionPoints() <= this.getMaximumActionPoints()
 * 
 * @invar	| This worm's name is at all times a valid name or equal to an empty String.
 * 			| this.getName().equals("") || isValidName(this.getName())
 * 
 * @invar	This worm's radius is at all times equal to or higher than the minimum radius.
 * 			| this.getRadius() >= this.getMinimumRadius()
 * 
 * @invar	This worm's angle is greater than or equal to 0 and less than 2*Math.PI.
 * 			| this.getAngle() >= 0 && this.getAngle() < 2*Math.PI
 * 
 * @invar	No double will have the value of "Not A Number" and neither will any double returning function return it.
 *			| !Double.isNaN(...)
 *				//TODO: Implement checks in the code for this checking.
 *
 *	//TODO: Use Util.fuzzy.. where possible.
 */
public class Worm {

	/**
	 * Acceleration on earth while falling.
	 */
	public static final double EARTH_ACCELERATION = 9.80665;
	
	/**
	 * The time the force is exerted on a worm's body. (e.g. while jumping.)
	 */
	public static final double FORCE_TIME = 0.5;
	
	
	/**
	 * Initialize a new worm with a certain position, angle, radius, name and amount of action points.
	 * 
	 * @param position The position of the new worm.
	 * @param angle The angle of the new worm.
	 * @param radius The radius of the new worm.
	 * @param name The name of the new worm.
	 * @param actionPoints The amount of action points for the new worm.
	 * 
	 * @effect	A new worm will be initialized with a position, angle, radius, name and the chosen amount of action points.
	 * 			In the case the amount of action points is greater than the maximum allowed amount the amount will be set to that maximum.
	 * 			| this(position, angle, radius, name);
	 * 			| this.setCurrentActionPoints(actionPoints)
	 * 
	 */
	public Worm(Position position, double angle, double radius, String name, int actionPoints) {
		this(position, angle, radius, name);
		this.setCurrentActionPoints(actionPoints);
	}
	
	/**
	 * Initialize a new worm with a maximum amount of action points possible for this worm.
	 * 
	 * @param position The position of the new worm.
	 * @param angle The angle of the new worm.
	 * @param radius The radius of the new worm.
	 * @param name The name of the new worm.
	 * 
	 * @effect	The position of the new worm is set to position.
	 * 			| this.setPosition(position)
	 * 
	 * @effect	The angle of the new worm is set to angle.
	 * 			| this.setAngle(angle)
	 * 
	 * @effect	The radius of the new worm is set to radius.
	 * 			| this.setRadius(radius)
	 * 
	 * @effect	The name of the new worm is set to name.
	 * 			| this.setName(name)
	 * 
	 * @effect	The amount of current action points of the new worm will be set to the maximum possible for this worm.
	 * 			| this.setCurrentActionPoints(this.getMaximumActionPoints())
	 * 
	 */
	public Worm(Position position, double angle, double radius, String name) {
		this.setPosition(position);
		this.setAngle(angle);
		this.setRadius(radius);
		this.setName(name);
		this.setCurrentActionPoints(this.getMaximumActionPoints());
	}
	
	
	
	
	/**
	 * Returns the position of this worm.
	 */
	@Basic
	public Position getPosition() {
		return position;
	}
	
	/**
	 * This worm jumps to a certain position.
	 * @post	The current amount of action points is drained to 0.
	 * 			| new.getCurrentActionPoints() == 0
	 * 
	 * @effect The new position of this worm is calculated and set.
	 * 			| this.setPosition(this.jumpStep(this.jumpTime()))
	 */
	public void jump() {
		this.setPosition(this.jumpStep(this.jumpTime()));
		this.setCurrentActionPoints(0);
	}
	
	
	/**
	 * Returns the position where this worm would be at a certain time whilst jumping.
	 * 
	 * @param time The time of when we return the position.
	 * @return The position this worm has at a certain time in a jump when time isn't equal to zero.
	 * 			| if(time != 0) Then
	 * 			| force = 5 * this.getCurrentActionPoints() + this.getMass() * EARTH_ACCELERATION
	 * 			| startSpeed = (force / this.getMass()) * FORCE_TIME
	 * 			| startSpeedX = startSpeed * Math.cos(this.getAngle())
	 * 			| startSpeedY = startSpeed * Math.sin(this.getAngle())
	 * 
	 * 			| x = this.getPosition().getX() + (startSpeedX * time)
	 * 			| y = this.getPosition().getY() + (startSpeedY * time - EARTH_ACCELERATION * Math.pow(time,2) / 2)
	 * 			| result == new Position(x,y)
	 * 
	 * @return	When the time equals 0, the current position will be returned.
	 * 			| if(time == 0) Then
	 * 			| result == this.getPosition();
	 * 
	 * @throws IllegalArgumentException
	 * 			When time exceeds the time required to jump or time is a negative value.
	 * 			| (time > this.jumpTime() || time < 0)
	 * 
	 */
	public Position jumpStep(double time) throws IllegalArgumentException {
		if(! Util.fuzzyLessThanOrEqualTo(time, jumpTime()))
			throw new IllegalArgumentException("The time musn't be greater than the time needed to perform the whole jump. Time: " + time + " and jumpTime: " + jumpTime());
		if(time < 0)
			throw new IllegalArgumentException("The time musn't be less negative.");

		if(time == 0) {
			return this.getPosition();
		}
		
		if(Util.fuzzyGreaterThanOrEqualTo(this.getAngle(), Math.PI)) { //TODO: If we change from 0 - 360 to -180 -> 180 we have to change this as well.
			return this.getPosition();
		}
		
		
		//Calculation
		double force = 5 * this.getCurrentActionPoints() + this.getMass() * EARTH_ACCELERATION;
		double startSpeed = (force / this.getMass()) * FORCE_TIME;
		
		double startSpeedX = startSpeed * Math.cos(this.getAngle());
		double startSpeedY = startSpeed * Math.sin(this.getAngle());
		
		double x = this.getPosition().getX() + (startSpeedX * time);
		double y = this.getPosition().getY() + (startSpeedY * time - EARTH_ACCELERATION * Math.pow(time,2) / 2);
		
		//Return
		return new Position(x,y);
	}
	
	
	/**
	 * Returns the jump time if jumped from this position with the current angle.
	 * @return The time used to jump.
	 * 			| If this.getAngle() >= Math.PI Then result == 0
	 * 			| Else
	 * 			| force = 5 * this.getCurrentActionPoints() + this.getMass() * EARTH_ACCELERATION
	 * 			| startSpeed = (force / this.getMass()) * FORCE_TIME
	 * 			| distance = (Math.pow(startSpeed, 2) * Math.sin(2 * this.getAngle())) / EARTH_ACCELERATION
	 * 			| time = distance / (startSpeed * Math.cos(this.getAngle()))
	 * 			| result == time
	 */
	public double jumpTime() { //TODO: checking isNaN --> exception
		//this.getAngle() will automatically be less than 2*Math.PI because of the invariant.
		if(Util.fuzzyGreaterThanOrEqualTo(this.getAngle(), Math.PI)) {
			return 0;
		}

		//sin(2X) = 2sin(X)cos(X); so 2sin(X)cos(X)/cos(X) => 2sin(X) => return 0   => time can never be negative.
		double force = 5 * this.getCurrentActionPoints() + this.getMass() * EARTH_ACCELERATION;
		double startSpeed = (force / this.getMass()) * FORCE_TIME;
		double distance = (Math.pow(startSpeed, 2) * Math.sin(2 * this.getAngle())) / EARTH_ACCELERATION;
		double time = Math.abs(distance / (startSpeed * Math.cos(this.getAngle())));
		
		return time;
	}
	
	
	/**
	 * Move this worm a certain amount of steps in the direction it's facing.
	 * @param steps The amount of steps this worm takes.
	 * 
	 * @post	The new position is the old position plus the amount of steps proportional to the direction this worm is facing.
	 * 			| new.getPosition()==new Position(
	 * 				this.getPosition().getX() + steps*Math.cos(this.getAngle())*this.getRadius(),
	 * 				this.getPosition().getY() + steps*Math.sin(this.getAngle())*this.getRadius())
	 * 
	 * @post	The new action points is equal to the old action points minus the cost to move.
	 * 			| new.getCurrentActionPoints() ==
	 * 				this.getCurrentActionPoints() - getMoveCost(steps,this.getAngle())
	 * 
	 * @throws IllegalArgumentException
	 * 			If steps is smaller than zero or if this worm doesn't have enough action points.
	 * 			| steps<0 || this.getMoveCost(steps,this.getAngle())>this.getCurrentActionPoints()
	 */
	public void move(int steps) throws IllegalArgumentException{
		if(steps < 0)
			throw new IllegalArgumentException("Steps must be higher than or equal to zero");
		if(getMoveCost(steps,getAngle()) > getCurrentActionPoints())
			throw new IllegalArgumentException("You don't have enough Action Points");
		
		double x= this.getPosition().getX() + steps*Math.cos(getAngle())*getRadius();
		double y= this.getPosition().getY() + steps*Math.sin(getAngle())*getRadius();
		
		setPosition(new Position(x,y));
		
		int actionPoints=getCurrentActionPoints()-getMoveCost(steps,getAngle());
		setCurrentActionPoints(actionPoints);
	}
	
	
	/**
	 * Returns the cost to move a certain amount of steps to move in a certain direction.
	 * @param steps The amount of steps to move.
	 * @param angle Determines the direction to move in.
	 * 
	 * @return 	The cost to move.
	 * 			| (steps*Math.ceil((Math.abs(Math.cos(angle)) + Math.abs(4*Math.sin(angle)))))
	 */
	public static int getMoveCost(int steps, double angle){
		return (int) (steps * Math.ceil(Math.abs(Math.cos(angle)) + Math.abs(4*Math.sin(angle))) );
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
	 * 
	 * @pre		The absolute value of the angle must be valid.
	 * 			| isValidAngle(Math.abs(angle)) 			
	 * @pre		The cost to turn should be less or equal to the amount we have.
	 * 			| this.getCurrentActionPoints() >= getTurnCost(angle)
	 * 
	 * @post	The new worm's action points is equal to the old amount minus the cost to turn.
	 * 			| new.getCurrentActionPoints() = this.getCurrentActionPoints() - getTurnCost(angle)
	 * @post	The new angle is equal to the old angle + the given angle.
	 * 			| new.getAngle() = this.getAngle() + angle
	 */
	public void turn(double angle) {
		assert isValidAngle(Math.abs(angle*2));
		assert this.getCurrentActionPoints() >= getTurnCost(angle);
		
		System.out.println("Our angle combined:" + (this.getAngle() + angle)); //TODO: Delete debug messages
		System.out.println("set to angle: " + ((this.getAngle() + angle + 2*Math.PI) % 2*Math.PI));
		System.out.println("Our modulo thingy: " + Util.modulo(this.getAngle() + angle + 2*Math.PI, 2*Math.PI));
		
		this.setAngle(Util.modulo(this.getAngle() + angle + 2*Math.PI, 2*Math.PI));
		this.setCurrentActionPoints(this.getCurrentActionPoints() - getTurnCost(angle));
	}
	
	/**
	 * Returns the cost to move the angle.
	 * @param angle The angle to turn.
	 * @return The cost to turn.
	 */
	public static int getTurnCost(double angle) {
		return (int) Math.abs(Math.ceil(30 * (angle / Math.PI)));
	}
	
	/**
	 * The angle provided has to be greater than or equal to 0 and less than or equal to 2*Math.PI.
	 * @param angle The angle to check.
	 * @return Whether or not the given angle is valid.
	 */
	public static boolean isValidAngle(double angle){
		return Util.fuzzyGreaterThanOrEqualTo(angle, 0) && Util.fuzzyLessThanOrEqualTo(angle, 2*Math.PI);
	}
	
	/**
	 * Set the new angle of this worm.
	 * @param angle The new angle of this worm.
	 * 
	 * @pre		The angle provided has to be a valid angle.
	 * 			| isValidAngle(angle)
	 * 
	 * @post	The angle of this worm is equal to the given angle.
	 * 			| (new.getAngle() == angle)
	 */
	private void setAngle(double angle) {
		assert isValidAngle(angle); //TODO: invariant
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
	 * 
	 * @effect	The mass of this worm will be updated accordingly.
	 * 			| this.setMass()
	 * 
	 * @throws IllegalArgumentException
	 * 			When the given radius is less than the minimum radius.
	 * 			| radius < this.getMinimumRadius()
	 */
	@Model
	public void setRadius(double radius) throws IllegalArgumentException {
		if(! Util.fuzzyGreaterThanOrEqualTo(radius, getMinimumRadius()))
			throw new IllegalArgumentException("The radius has to be greater than or equal to the minimum radius " + minRadius);
		
		this.radius = radius;
		this.setCurrentActionPoints(this.getCurrentActionPoints());
	}
	
	/**
	 * Returns the minimum radius of this worm.
	 */
	@Basic @Immutable
	public double getMinimumRadius() {
		return minRadius;
	}
	
	private double radius;
	private final double minRadius = 0.25; //Initialize in constructor later on.
	
	
	/**
	 * Returns the mass of this worm.
	 */
	@Basic
	public double getMass() {
		return getDensity() * (4.0/3.0) * Math.PI * Math.pow(this.getRadius(),3);
	}
	
//	/**
//	 * Sets the mass of this worm.
//	 * @post	The mass of this worm is equal to the result of the formula "Mass = (getDensity()) * (4/3) * Math.PI * (radius)^3"
//	 * 			| new.getMass() == getDensity() * (4/3) * Math.PI * Math.pow(this.getRadius(),3)
//	 * 
//	 * @post	see setCurrentActionPoints(this.getCurrentActionPoints()) //@effect?
//	 */
//	@Raw
//	private void setMass() /*throws IllegalArgumentException*/ {
//		//We'd better use an argument if we use this...
//		/*if(this.radius >= Math.pow(Double.MAX_VALUE * (3/4) / DENSITY / Math.PI, 1/3))
//			throw new IllegalArgumentException();*/
//			
//		this.mass = getDensity() * (4.0/3.0) * Math.PI * Math.pow(this.getRadius(),3);
//		this.setCurrentActionPoints(this.getCurrentActionPoints());
//	}
	
	/**
	 * Returns this worm's density.
	 */
	@Basic @Immutable
	public static final double getDensity() {
		return DENSITY;
	}
	
//	private double mass;
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
	 * @post	The name of this worm is equal to name.
	 * 			| new.getName() == name
	 * 
	 * @throws IllegalArgumentException
	 * 			When name isn't a valid name.
	 * 			| !isValidName(name)
	 */
	@Raw
	public void setName(String name) throws IllegalArgumentException {
		if (!isValidName(name))
			throw new IllegalArgumentException("Invalid name.");
		
		this.name = name;
	}
	
	/**
	 * Returns whether the name is a valid name.
	 * @param name The name to be checked.
	 * 
	 * @return  True if the name is longer than 2 characters, starts with an uppercase and when every character is one from the following:
	 * 			[A-Z] || [a-z] || a space || ' || "
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
	 * 			| new.getCurrentActionPoints() == (actionPoints > this.getMaxActionPoints()) ? this.getMaxActionPoints() : actionPoints
	 */
	@Raw @Model
	private void setCurrentActionPoints(int actionPoints) {
		this.currentActionPoints = (actionPoints > getMaximumActionPoints()) ? getMaximumActionPoints() : actionPoints;
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
	public int getMaximumActionPoints() {
		if(this.getMass() > Integer.MAX_VALUE)
			return Integer.MAX_VALUE;
		
		return (int) Math.round(this.getMass());
	}
	
	private int currentActionPoints;
	
}
