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
	//TODO: moeten alle gevallen als commentaar beschreven worden of is de naam vd worm + de naam vd functie voldoende?
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
	public void testWormPositionAngleRadiusNameActionpoints() {
		Worm worm = new Worm(new Position(-1,2), 3, 4, "Testworm", 5);
		assertEquals(worm.getPosition(),new Position(-1,2));
		assertEquals(worm.getAngle(),3,0);
		assertEquals(worm.getRadius(),4,0);
		assertEquals(worm.getName(),"Testworm");
		assertEquals(worm.getCurrentActionPoints(),5);	
	}

	@Test
	public void testWormPositionAngleRadiusName() {
		Worm worm = new Worm(new Position(-1,2), 3, 4, "Testworm");
		assertEquals(worm.getPosition(),new Position(-1,2));
		assertEquals(worm.getAngle(),3,0);
		assertEquals(worm.getRadius(),4,0);
		assertEquals(worm.getName(),"Testworm");
		assertEquals(worm.getCurrentActionPoints(),worm.getMaximumActionPoints());
	}

	@Test
	public void testJump_LegalAngle() {
		Worm worm = new Worm(new Position(0,0), Math.PI/4, 1, "Test Jump Legal", 1);
		Position oldJump=worm.jumpStep(worm.jumpTime());
		worm.jump();
		assertEquals(worm.getPosition(),oldJump); //jumpStep and jumpTime already tested.
		assertEquals(worm.getCurrentActionPoints(),0);
	}
	
	@Test
	public void testJump_LegalAngleRight() {
		Worm worm = new Worm(new Position(0,0), 0, 1, "Test Jump Right", 1);
		Position oldJump=worm.jumpStep(worm.jumpTime());
		worm.jump();
		assertEquals(worm.getPosition(),oldJump);
		assertEquals(worm.getCurrentActionPoints(),0);
	}
	
	@Test
	public void testJump_LegalAngleLeft() {
		Worm worm = new Worm(new Position(0,0), Math.PI, 1, "Test Jump Left", 1);
		Position oldJump=worm.jumpStep(worm.jumpTime());
		worm.jump();
		assertEquals(worm.getPosition(),oldJump);
		assertEquals(worm.getCurrentActionPoints(),0);
	}
	
	@Test
	public void testJump_IllegalAngle() {
		Worm worm = new Worm(new Position(0,0), 3*Math.PI/2, 1, "Test Jump Illegal", 1);
		worm.jump();
		assertEquals(worm.getPosition(),new Position(0,0));
		assertEquals(worm.getCurrentActionPoints(),1);
	}

	@Test
	public void testJumpStep_LegalArgument() {
		Worm worm = new Worm(new Position(0,0), Math.PI/4, 1, "Test JumpStep Legal", 1);
		
		Position position = worm.jumpStep(worm.jumpTime());
		assertEquals(position.getX(), 2.45222451, 1E-3);
		assertEquals(position.getY(), 0, 1E-4);
	}
	
	/**
	 * Argument time too high to handle.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testJumpStep_TooHighArgument() {
		Worm worm = new Worm(new Position(0,0), Math.PI/4, 1, "Test JumpStep Too High", 1);				
		worm.jumpStep(worm.jumpTime()+10);
	}
	
	/**
	 * Argument time too low (<0)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testJumpStep_TooLowArgument() {
		Worm worm = new Worm(new Position(0,0), Math.PI/4, 1, "Test JumpStep Too Low", 1);	
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

	@Test
	public void testJumpTime_Zero() {
		Worm worm = new Worm(new Position(0,0), 3*Math.PI/2, 1, "Test JumpTime Zero", 1);
		assertEquals(worm.jumpTime(), 0, 0);
	}
	
	/**
	 * Test Move regular case
	 */
	@Test
	public void testMove_Legal() {
		Worm worm = new Worm(new Position(0,0), 0, 1, "Test Move Legal", 3);
		worm.move(2);
		
		assertEquals(worm.getPosition().getX(), 2, 0);
		assertEquals(worm.getPosition().getY(), 0, 0);
		assertEquals(worm.getCurrentActionPoints(),1); //TODO: heb ik hier toegevoegd, vergeten of moet niet?
	}
	
	/**
	 * Test Move with an illegal amount of steps (<0).
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testMove_IllegalSteps() {
		Worm worm = new Worm(new Position(0,0), 0, 1, "Test Move Illegal Steps");
		worm.move(-2);
	}
	
	/**
	 * Test Move with an insufficient amount of AP to perform the move. 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testMove_IllegalActionPoints() {
		Worm worm = new Worm(new Position(0,0), 0, 1, "Test Move Illegal AP", 1);
		worm.move(10);
	}

	/**
	 * Test MoveCost for 2 steps and PI/4 angle (regular case)
	 */
	@Test
	public void testGetMoveCost() {
		assertEquals(Worm.getMoveCost(2, Math.PI/4), 8);
	}

	@Test
	public void testTurn_Positive() {
		Worm worm = new Worm(new Position(0,0), 3 * Math.PI / 2, 1, "Test Turn Positive", 16);
		worm.turn(Math.PI/2);
		assertEquals(worm.getAngle(),0,1E-9);
		assertEquals(worm.getCurrentActionPoints(),1);
	}

	@Test
	public void testTurn_Zero() {
		Worm worm = new Worm(new Position(0,0), 3.0/2.0 * Math.PI, 1, "Test Turn Zero", 16);
		worm.turn(0);
		assertEquals(worm.getAngle(),3.0/2.0 * Math.PI,1E-9);
		assertEquals(worm.getCurrentActionPoints(),16);
	}
	
	@Test
	public void testTurn_Negative() {
		Worm worm = new Worm(new Position(0,0), 3.0/2.0 * Math.PI, 1, "Test Turn Negative", 16);
		worm.turn(-Math.PI/2);
		assertEquals(worm.getAngle(),Math.PI,1E-9);
		assertEquals(worm.getCurrentActionPoints(),1);
	}
	
	/**
	 * Case angle -PI
	 */
	@Test
	public void testGetTurnCost_Negative() {
		assertEquals(Worm.getTurnCost(-Math.PI), 30);
	}
	
	/**
	 * Case angle 0
	 */
	@Test
	public void testGetTurnCost_Zero() {
		assertEquals(Worm.getTurnCost(0), 0);
	}
		
	/**
	 * Case angle PI
	 */
	@Test
	public void testGetTurnCost_Positive() {
		assertEquals(Worm.getTurnCost(Math.PI), 30);
	}

	public void TestIsValidAngle_Legal(){
		assert(Worm.isValidAngle(Math.PI));
		assert(Worm.isValidAngle(Math.PI));
	}
	
	public void TestIsValidAngle_Illegal(){
		assertFalse(Worm.isValidAngle(2*Math.PI));
		assertFalse(Worm.isValidAngle(-1));
	}
	
	/**
	 * Test the radius setter in a regular case.
	 */
	@Test
	public void testSetRadius() { //TODO: moeten de IllegalArgumentExceptions niet getest worden?
		Worm worm = new Worm(new Position(0,0), 0, 2, "Test Radius Legal");
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
	public void testSetName_Legal() {
		Worm worm = new Worm(new Position(0,0), 0, 1, "Test Name Legal");
		
		worm.setName("Eric");
		assertEquals(worm.getName(), "Eric");
	}
	
	/**
	 * Test illegal Name case (lower first letter)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetName_IllegalName() {
		Worm worm = new Worm(new Position(0,0), 0, 1, "Test Name Illegal");
		worm.setName("eric");
	}

	/**
	 * Legal case
	 */
	@Test
	public void testIsValidName_Legal() { //TODO: alle Legals en alle Illegals mochten samen he?
		assert(Worm.isValidName("James o'Hara"));
		assert(Worm.isValidName("James o\"Hara"));
	}

	/**
	 * Test illegal cases
	 * First letter lower
	 * Non alphabetic letter (excl space, ' and ")
	 * Illegal character \n 
	 * length < 2
	 * * null
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
