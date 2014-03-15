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
	 * Test createWorm(double, double, double, double, String) legal case.
	 * 
	 */
	@Test
	public void testCreateWorm_Legal() {
		Worm worm = facade.createWorm(1, 1, 0, 1, "Test legal createWorm");
		assertTrue(worm != null);
	}
	
	/**
	 * Test createWorm(double, double, double, double, String) illegal case.
	 * When an illegal coordinate is provided.
	 * 
	 * All tests for throwing exceptions by the constructor new Worm are tested in WormTest.class
	 * Therefore we decided not to write any test cases whether the exception was passed.
	 */
	@Test(expected=ModelException.class)
	public void testCreateWorm_Illegal() {
		facade.createWorm(Double.NaN, 1, 0, 1, "Test illegal createWorm");
	}
	
	/**
	 * Test canMove(Worm, int)
	 * case nbSteps < 0
	 * case Worm.getMoveCost(nbSteps, worm.getAngle()) > worm.getCurrentActionPoints()
	 * case Worm.getMoveCost(nbSteps, worm.getAngle()) <= worm.getCurrentActionPoints() && nbSteps > 0
	 */
	@Test
	public void testCanMove() {
		Worm wormPoor = new Worm(new Position(0,0), 0, 1, "Test", 0);
		Worm wormRich = facade.createWorm(0, 0, 0, 1, "Test");
		
		assertFalse(facade.canMove(wormPoor, 1));
		assertFalse(facade.canMove(wormRich, -1));
		
		assertTrue(facade.canMove(wormPoor,	0));
		assertTrue(facade.canMove(wormRich, 2));	
	}
	
	@Test
	public void testMaximumActionPoints() {
		Worm worm = facade.createWorm(0, 0, 0, 1, "Test");
		assertEquals(4448, facade.getMaxActionPoints(worm));
	}

	@Test
	public void testMove_Horizontal() { //TODO Test decrease in AP
		Worm worm = facade.createWorm(0, 0, 0, 1, "Test");
		facade.move(worm, 5);
		assertEquals(5, facade.getX(worm), EPS);
		assertEquals(0, facade.getY(worm), EPS);
	}

	@Test
	public void testMove_Vertical() { //TODO Test decrease in AP
		Worm worm = facade.createWorm(0, 0, Math.PI / 2,  1, "Test");
		facade.move(worm, 5);
		assertEquals(0, facade.getX(worm), EPS);
		assertEquals(5, facade.getY(worm), EPS);
	}

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
