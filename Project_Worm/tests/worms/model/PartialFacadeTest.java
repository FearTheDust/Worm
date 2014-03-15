package worms.model;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import worms.util.Position;
import worms.util.Util;

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
	 * Test getMaxActionPoints
	 */
	@Test
	public void testGetMaxActonPoints() {
		Worm worm = facade.createWorm(0, 0, 0, 1, "Test Max AP");
		assertEquals(4448, facade.getMaxActionPoints(worm));
	}

	/**
	 * Test move horizontally
	 */
	@Test
	public void testMove_Horizontal() {
		Worm worm = facade.createWorm(0, 0, 0, 1, "Test move horizontal");
		int oldAP=facade.getActionPoints(worm);
		facade.move(worm, 5);
		assertEquals(facade.getX(worm), 5, EPS);
		assertEquals(facade.getY(worm), 0, EPS);
		assertEquals(facade.getActionPoints(worm),oldAP-5);
	}

	/**
	 * Test move vertically
	 */
	@Test
	public void testMove_Vertical() {
		Worm worm = facade.createWorm(0, 0, Math.PI / 2,  1, "Test move vertical");
		int oldAP = facade.getActionPoints(worm);
		facade.move(worm, 5);
		assertEquals(facade.getX(worm), 0, EPS);
		assertEquals(facade.getY(worm), 5, EPS);
		assertEquals(facade.getActionPoints(worm), oldAP-20);
	}

	/**
	 * Test jump facing downwards
	 */
	@Test
	public void testJump_Exception() {
		Worm worm = facade.createWorm(0, 0, 3 * Math.PI / 2, 1, "Test");
		int OldAP=facade.getActionPoints(worm);
		facade.jump(worm);
		
		assertEquals(facade.getActionPoints(worm),OldAP);
		assertEquals(facade.getX(worm),0,0);
		assertEquals(facade.getY(worm),0,0);
	}

}
