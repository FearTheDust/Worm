package worms.model;

import static org.junit.Assert.*;
import org.junit.*;
import worms.util.*;

/**
 * @author ...
 * @author Derkinderen Vincent
 * @author Coosemans Brent
 */
public class PartialFacadeTest {

	/**
	 * Default Epsilon value as stated in Util.
	 */
	private static final double EPS = Util.DEFAULT_EPSILON;

	/**
	 * Our facade instance to test on.
	 */
	private IFacade facade;

	@Before
	public void setup() {
		facade = new Facade();
	}
 
	/**
	 * Test createWorm with all legal arguments
	 */
	@Test
	public void testCreateWorm_Legal() {
		Worm worm = facade.createWorm(1, 1, 0, 1, "Test createWorm legal");
		assertTrue(worm != null);
	}
	
	/**
	 * Test createWorm with an illegal argument: an illegal coordinate.
	 *
	 * All tests for throwing exceptions by the constructor new Worm are tested in WormTest.class
	 * Therefore we decided not to write any test cases whether the exception was passed.
	 */
	@Test(expected=ModelException.class)
	public void testCreateWorm_Illegal() {
		facade.createWorm(Double.NaN, 1, 0, 1, "Test createWorm illegal");
	}
	
	/**
	 * Test canMove
	 * with insufficient amount of action points
	 * with number of steps smaller than zero
	 * with legal number of steps and legal amount of action points
	 */
	@Test
	public void testCanMove() {
		Worm wormPoor = new Worm(new Position(0,0), 0, 1, "Test canMove Zero AP", 0);
		Worm wormRich = facade.createWorm(0, 0, 0, 1, "Test canMove");
		
		assertFalse(facade.canMove(wormPoor, 1));
		assertFalse(facade.canMove(wormRich, -1));
		
		assertTrue(facade.canMove(wormPoor,	0));
		assertTrue(facade.canMove(wormRich, 2));	
	}

	/**
	 * Test canTurn(Worm, double)
	 * with an insufficient AP amount
	 * with a sufficient AP amount
	 */
	@Test
	public void testCanTurn() {
		Worm wormPoor = new Worm(new Position(0,0), 0, 1, "Test worm insufficient AP", 5);
		Worm wormRich = facade.createWorm(0, 0, 0, 1, "Test Worm enough AP");
		
		assertFalse(facade.canTurn(wormPoor, Math.PI));
		
		assertTrue(facade.canTurn(wormRich, Math.PI));
	}

	/**
	 * Tests the turn(Worm, double)
	 * in case of 4*Math.PI and -4*Math.PI
	 * in case of 3*Math.PI and -3*Math.PI
	 * in case of -Math.PI / 8
	 * in case of 0
	 */
	@Test
	public void testTurn() {
		Worm worm = facade.createWorm(0, 0, 0, 1, "Test Turn");
		
		facade.turn(worm, 4*Math.PI);
		assertEquals(worm.getAngle(), 0, EPS);
		
		facade.turn(worm, -4*Math.PI);
		assertEquals(worm.getAngle(), 0, EPS);
		
		facade.turn(worm, 3*Math.PI);
		assertEquals(worm.getAngle(), Math.PI, EPS);
		
		facade.turn(worm, -3*Math.PI);
		assertEquals(worm.getAngle(), 0, EPS);
		
		facade.turn(worm, -Math.PI / 8);
		assertEquals(worm.getAngle(), 15*Math.PI / 8, EPS);
		
		facade.turn(worm, 0);
		assertEquals(worm.getAngle(), 15*Math.PI / 8, EPS);
	}
}
