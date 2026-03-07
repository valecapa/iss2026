package main.java.conway;
import main.java.conway.devices.ConwayLifeGridClaude;
import main.java.conway.devices.MockOutdev;
import main.java.conway.domain.*;
import unibo.basicomm23.utils.CommUtils;
public class MainConwayLifeJava  {

	public void configureTheSystemWitMockOutdev() {
    }

	public void configureTheSystemWithSwing() {
        Life life            = new Life( 20,20 );             //ncell in iomap.js        
        IOutDev swinggui     = new ConwayLifeGridClaude( );   //dispositivo di output e anche di input 
        GameController  cc   = new LifeController(life, swinggui) ;   //un GameController che deve usare un outdev
        ((ConwayLifeGridClaude) swinggui).setController(cc);          //iniezione del controller nella GUI
        //Il sistema termina quando si chiude la swinggui
   	}
	
	public void configureTheSystemWithHtmlWs(boolean pageexternal) {
 	}
 	
    public static void main(String[] args) {
    System.out.println("MainConway | STARTS " );  
    MainConwayLifeJava app = new MainConwayLifeJava();
    app.configureTheSystemWithSwing();
    System.out.println("MainConway | ENDS " );  
    }

}