package main.java.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.conway.domain.Grid;
import main.java.conway.domain.Life;
import main.java.conway.domain.LifeInterface;
 
/*
 * TEST PLAN
 */
public class LifeTest {
private LifeInterface lifeModel;

	@Before
	public void setup() {
		System.out.println("LifeTest | setup");	
		lifeModel = new Life(5, 5);
	}
	@After
	public void down() {
		System.out.println("LifeTest | down");
	}
	
	@Test
	public void testSetCellAlive() {
		System.out.println("LifeTest | setting cell alive");	
	    lifeModel.setCell(3, 3, true);
	    assertTrue(lifeModel.isAlive(3, 3));
	}
	
	@Test
	public void testSetCellDead() {
		System.out.println("LifeTest | setting cell dead");
	    lifeModel.setCell(1, 2, false);
	    assertFalse(lifeModel.isAlive(1, 2));
	}
	
	@Test
	public void testLonelyCellDies() {
		System.out.println("LifeTest | test lonely cell");
	    lifeModel.setCell(4, 1, true);
	    lifeModel.nextGeneration();
	    assertFalse(lifeModel.isAlive(4, 1));
	}
	
	@Test
	public void testBlockStillLife() {
		System.out.println("LifeTest | test block 2x2");
		lifeModel.setCell(1,1,true);
		lifeModel.setCell(1,2,true);
		lifeModel.setCell(2,1,true);
		lifeModel.setCell(2,2,true);

		lifeModel.nextGeneration();

	    assertTrue(lifeModel.isAlive(1,1));
	    assertTrue(lifeModel.isAlive(1,2));
	    assertTrue(lifeModel.isAlive(2,1));
	    assertTrue(lifeModel.isAlive(2,2));
	}
	
	@Test
	public void testReset() {
		
		System.out.println("LifeTest | reset");
		
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				lifeModel.setCell(i, j, true);
			}
		}

		lifeModel.resetGrids();
		
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				assertFalse(lifeModel.isAlive(i,j));
			}
		}
	}
	
	@Test
	public void testGetGrid() {
		System.out.println("LifeTest | get grid");
	    Grid grid = lifeModel.getGrid();
	    assertNotNull(grid);
	}

}
