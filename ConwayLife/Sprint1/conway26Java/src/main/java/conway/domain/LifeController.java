package main.java.conway.domain;
//import java.util.concurrent.TimeUnit;
import unibo.basicomm23.utils.CommUtils;

/*
 * LifeController di conwaygui.  ]
 */

public class LifeController implements GameController {
    private int generationTime = 500;
    private  LifeInterface life;
    private  IOutDev outdev; 
 	protected boolean running = false;
    protected int epoch       = 0;

    
    public LifeController( LifeInterface game, IOutDev outdev ){  
        this.life   = game;       
        this.outdev = outdev;
       CommUtils.outyellow("LifeController CREATED outdev="+outdev   );
       //if( outdev != null ) outdev.displayGrid( life.getGrid() );
     }
    
    @Override
    public int getGenTime() {
    	return generationTime;
    }
 
/*
 * Funzioni di controllo del gioco
 */
	@Override
	public void switchCellState(int x, int y) {
		ICell c = life.getCell(x, y); 
		c.switchCellState( );   
		if( outdev != null ) outdev.displayGrid(life.getGrid());
	}
	
	protected void startTheGame() {
		if( running ) return;   //start sent while running
		running = true;
		epoch   = 0;
		play();		
	}
	
	protected void stopTheGame() {
		running = false;		
	}

	protected void clearTheGame() {
		if( outdev != null ) outdev.display("lfctrl: clearing");
 		stopTheGame();
 		//CommUtils.delay(500);   //prima fermo e poi ...
		epoch = 0;
		resetAndDisplayGrids(  );   
	}
	
	protected void printout( String s ) {
		if( outdev != null ) outdev.display(s);
	}
	
//	protected void exitTheGame() {
//		if( outdev != null ) outdev.close();
//		System.exit(0);
//	}
	
    protected void play() {  
			new Thread() {
			public void run() {			
				if( outdev != null ) outdev.displayGrid( life.getGrid()  ); 
				while( running ) {
//					try {
//						TimeUnit.MILLISECONDS.sleep(generationTime);
						CommUtils.delay(generationTime);
						life.nextGeneration();
						if( outdev != null ) outdev.displayGrid( life.getGrid()  );
						epoch++;
						//CommUtils.outblue("---------Epoch ---- "+epoch );
//						boolean gridEmpty  = life.gridEmpty();
//						boolean gridStable = life.gridStable();
//						if( gridEmpty || gridStable ) {
//				    		running = false;
//				    		String reason = gridStable ? "stable" : "empty";
//				    		String outInfo = "lfctrl: GAME ENDED after " + epoch + 
//				    				" Epochs since " + reason;
//				    		CommUtils.outyellow(outInfo);
//				    		outdev.display(outInfo);
//				    		epoch = 0;
//				    		running = false;
//				    	}
						
//					} catch (InterruptedException e) { //per lo sleep
//						e.printStackTrace();
//					}
				}//while
				printout("gamestopped"); 
			}
			}.start();
    }

 	

	protected void resetAndDisplayGrids(   ) {
		life.resetGrids();
		if(outdev != null) outdev.displayGrid( life.getGrid() );
	}
	
	
	@Override
	public void onStart() {
		startTheGame();	
	}

	@Override
	public void onStop() {
		stopTheGame();	
	}

	@Override
	public void onClear() {
		clearTheGame();	
	}
	@Override
	public int numEpoch() {
		return epoch;
	}

}
