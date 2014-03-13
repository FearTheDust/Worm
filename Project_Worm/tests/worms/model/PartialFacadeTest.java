package worms.model;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import worms.util.Util;

public class PartialFacadeTest {

	private static final double EPS = Util.DEFAULT_EPSILON;

	private IFacade facade;

	@Before
	public void setup() {
		facade = new Facade();
	}

	@Test
	public void testMaximumActionPoints() {
		Worm worm = facade.createWorm(0, 0, 0, 1, "Test");
		assertEquals(4448, facade.getMaxActionPoints(worm));
	}

	@Test
	public void testMoveHorizontal() {
		Worm worm = facade.createWorm(0, 0, 0, 1, "Test");
		facade.move(worm, 5);
		assertEquals(5, facade.getX(worm), EPS);
		assertEquals(0, facade.getY(worm), EPS);
	}

	@Test
	public void testMoveVertical() {
		Worm worm = facade.createWorm(0, 0, Math.PI / 2,  1, "Test");
		facade.move(worm, 5);
		assertEquals(0, facade.getX(worm), EPS);
		assertEquals(5, facade.getY(worm), EPS);
	}

	@Test
	public void testJumpException() {
		Worm worm = facade.createWorm(0, 0, 3 * Math.PI / 2, 1, "Test");
		int OldAP=facade.getActionPoints(worm);
		facade.jump(worm);
		assertEquals(facade.getActionPoints(worm),OldAP);
		assertEquals(facade.getX(worm),0,0);
		assertEquals(facade.getY(worm),0,0);
	}

}
