package main.java.test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import main.java.conway.domain.Grid;

public class GridTest {
	private static final int nRows=5;
	private static final int nCols=5;
	
private Grid grid;

	@Before
	public void setup() {
		System.out.println("GridTest | setup");	
		grid= new Grid(nRows,nCols);
	}
	@After
	public void down() {
		System.out.println("GridTest | down");
	}
	
	@Test
	public void testDims() {
		System.out.println("testDims ---------------------" );
		int nr = grid.getRowsNum();
		int nc = grid.getColsNum();
		assertTrue( nr==nRows && nc==nCols );
	}
	@Test
	public void testCGridCellValue() {
		System.out.println("testCGridCellValue ---------------------" );
		grid.setCellValue(0,0,true);
		assertTrue(   grid.getCellValue(0,0) );
		assertFalse(  grid.getCellValue(0,1) );
	}
	@Test
	public void testGridRep() {
		System.out.println("testGridRep ---------------------" );
 		System.out.println(""+grid);
		assertTrue( grid.toString().startsWith(". . . . ."));
	}
	@Test
	public void testPrintGrid() {
		System.out.println("testPrintGrid ---------------------" );
		grid.setCellValue(0,0,true);
		grid.setCellValue(0,1,true);
		grid.setCellValue(0,2,true);
		grid.setCellValue(0,3,true);
		grid.setCellValue(0,4,true);
		//grid.printGrid();
	}

}
