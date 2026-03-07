package main.java.conway;
import main.java.conway.devices.MockOutdev;
import main.java.conway.domain.*;
//import unibo.basicomm23.utils.CommUtils;
public class MainConwayLifeJava  {

	public void configureTheSystemWitMockOutdev() {
		System.out.println("MainConway | configureTheSystemWitMockOutdev" );  
        // 1. Inizializziamo il Modello (Il "Cosa")
        // Life crea internamente la sua Grid (Composizione)
        LifeInterface gameModel = new Life(5, 5);

        // 2. Inizializziamo l'Output (Il "Dove")
        // Possiamo decidere qui se usare la Console o una GUI
        IOutDev outputDevice = new MockOutdev();

        // 3. Inizializziamo il Controller (Il "Come")
        // Iniettiamo il modello e l'output nel controller (Dependency Injection)
         GameController lifeController = new LifeController(gameModel, outputDevice);

        // 4. Inizializziamo l'Input (L' "Innesco")
        // L'input comunica con il controller
//        InputDev inputDevice = new InputDev(lifeController);
         lifeController.switchCellState(2, 1);
         lifeController.switchCellState(2, 2);
         lifeController.switchCellState(2, 3);
        // 5. Avvio del sistema (simula l'esistenza di un input dev)
        System.out.println("MainConwayLifeJava: Inizializzazione completata.");  
        lifeController.onStart();
        //CommUtils.delay(2000);
        lifeController.onStop();
        lifeController.onClear();
        
        outputDevice.close();
    }

	public void configureTheSystemWithSwing() {
   	}
	
	public void configureTheSystemWithHtmlWs(boolean pageexternal) {
 	}
 	
    public static void main(String[] args) {
    System.out.println("MainConway | STARTS " );  
    MainConwayLifeJava app = new MainConwayLifeJava();
    app.configureTheSystemWitMockOutdev();
    System.out.println("MainConway | ENDS " );  
    }

}