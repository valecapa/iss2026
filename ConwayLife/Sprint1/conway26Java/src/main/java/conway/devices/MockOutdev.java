package main.java.conway.devices;
import main.java.conway.domain.IGrid;
import main.java.conway.domain.IOutDev;
import unibo.basicomm23.utils.CommUtils;

public class MockOutdev implements IOutDev{

	@Override
	public void display(String msg) {
		//CommUtils.outblue(msg);
		
	}

	@Override
	public void displayCell(IGrid grid, int x, int y) {
		CommUtils.outcyan("cell x="+x + " y="+y + " " +grid.getCell(x, y).isAlive());
	}
	
	@Override
	public void displayGrid( IGrid grid ) {
//		int rows = grid.getRowsNum();
//		int cols = grid.getColsNum();
//		for (int i = 0; i < rows; i++) {
//			for (int j = 0; j < cols; j++) {
//				displayCell(grid,i,j);
//				//CommUtils.outcyan("cell x="+ i + "y="+j+ ":" +  getCellValue(i,j));
//			}
//			CommUtils.outcyan("\n");
//		}
		 System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"  );
		 System.out.println(grid.toString());
		 System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"  );
	}

	  
	@Override
	public void close() {
		CommUtils.outcyan("MockOutdev closed");
	}


}
