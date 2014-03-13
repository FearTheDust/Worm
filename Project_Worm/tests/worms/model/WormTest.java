package worms.model;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import worms.model.Worm;
import worms.util.Position;

public class WormTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testWorm() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testWormPositionDoubleDoubleString() {
		fail("Not yet implemented"); // TODO
	}

	
	
	
	@Test
	public void testJump() {
		//Worm worm = new Worm(new Position(0,0), 0, 1, "Test jump from", 1);	
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testJumpStep_LegalArgument() {
		Worm worm = new Worm(new Position(0,0), Math.PI/4, 1, "Test JumpTime", 1);
		
		Position position = worm.jumpStep(worm.jumpTime());
		assertEquals(position.getX(), 2.45222451, 1E-3);
		assertEquals(position.getY(), 0, 1E-4);
	}
	
	/**
	 * Argument time too high to handle.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testJumpStep_TooHighArgument() {
		Worm worm = new Worm(new Position(0,0), Math.PI/4, 1, "Test JumpTime", 1);				
		worm.jumpStep(worm.jumpTime()+10);
	}
	
	/**
	 * Argument time too low (<0)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testJumpStep_TooLowArgument() {
		Worm worm = new Worm(new Position(0,0), Math.PI/4, 1, "Test JumpTime", 1);	
		worm.jumpStep(-1);
	}
	

	/**
	 * Test jumpTime for angle PI/2, 1 Action Point and 1 radius.
	 */
	@Test
	public void testJumpTime() {
		Worm worm = new Worm(new Position(0,0), Math.PI/2, 1, "Test JumpTime", 1);
		assertEquals(worm.jumpTime(), 1.00011, 1E-5);
	}

	/**
	 * Test Move regular case
	 */
	@Test
	public void testMove_RegularCase() {
		Worm worm = new Worm(new Position(0,0), 0, 1, "Test Move Regular Case");
		worm.move(2);
		
		assertEquals(worm.getPosition().getX(), 2, 0);
		assertEquals(worm.getPosition().getY(), 0, 0);
	}
	
	/**
	 * Test the move with an illegal amount of steps (<0).
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testMove_IllegalSteps() {
		Worm worm = new Worm(new Position(0,0), 0, 1, "Test Move Illegal step amount Case");
		worm.move(-2);
	}
	
	/**
	 * Test the move with an insufficient amount of AP to perform the move. 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testMove_IllegalActionPoints() {
		Worm worm = new Worm(new Position(0,0), 0, 1, "Test Move Illegal AP.", 1);
		worm.move(10);
	}

	/**
	 * Test the moveCost for 2 steps and PI/4 angle (regular case)
	 */
	@Test
	public void testGetMoveCost() {
		assertEquals(Worm.getMoveCost(2, Math.PI/4), 8);
	}

	@Test
	public void testTurn() {
		Worm worm = new Worm(new Position(0,0), 3/2 * Math.PI, 1, "Test");
		
	}

	/**
	 * Case angle -PI
	 */
	@Test
	public void testGetTurnCost() {
		assertEquals(Worm.getTurnCost(-Math.PI), 30);
	}

	/**
	 * Test the radius setter in a regular case.
	 */
	@Test
	public void testSetRadius() {
		Worm worm = new Worm(new Position(0,0), 0, 2, "Test Regular Radius");
		worm.setRadius(1);
		assertEquals(worm.getRadius(), 1, 0);
	}


	/**
	 * Test getMass
	 */
	@Test
	public void testGetMass() {
		Worm worm = new Worm(new Position(0,0), 0, 1, "Test Mass");
		assertEquals(worm.getMass(), 4448.495, 1E-3);
	}

	/**
	 * Test Legal name.
	 */
	@Test
	public void testSetName() {
		Worm worm = new Worm(new Position(0,0), 0, 1, "HELLO");
		
		worm.setName("Eric");
		assertEquals(worm.getName(), "Eric");
	}
	
	/**
	 * Test illegal Name case (lower first letter)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetName_IllegalName() {
		Worm worm = new Worm(new Position(0,0), 0, 1, "HELLO");
		worm.setName("eric");
	}

	/**
	 * Legal case
	 */
	@Test
	public void testIsValidName_Legal() {
		assert(Worm.isValidName("James o'Hara"));
		assert(Worm.isValidName("James o\"Hara"));
	}

	/**
	 * Test illegal cases
	 * First letter lower
	 * Non alphabetic letter (excl space, ' and ")
	 * null
	 * length < 2
	 * Illegal character \n 
	 */
	@Test
	public void testIsValidName_Illegal() {
		assertFalse(Worm.isValidName("james o'Hara"));
		assertFalse(Worm.isValidName("James 0 Hara"));
		assertFalse(Worm.isValidName("James \n Hara"));
		assertFalse(Worm.isValidName("N"));
		assertFalse(Worm.isValidName(null));
	}
	
	/**
	 * Test getMaximumActionPoints in the case that the radius is too high resulting in the MaxActionPoints being bigger than Integer.MAX_VALUE.
	 */
	@Test
	public void testGetMaximumActionPoints_HighestMaximum() {
		Worm worm = new Worm(new Position(0,0), 0, Double.MAX_VALUE, "Test High Max Points");
		
		assertEquals(worm.getMaximumActionPoints(), Integer.MAX_VALUE);
	}
	
	/**
	 * Test getMaximumActionPoints in a regular case.
	 */
	@Test
	public void testGetMaximumActionPoints_RegularCase() {
		Worm worm = new Worm(new Position(0,0), 0, 1, "Test High Regular Case");
		assertEquals(worm.getMaximumActionPoints(), 4448);
	}

}
