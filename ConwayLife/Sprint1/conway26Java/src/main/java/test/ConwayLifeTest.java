package main.java.test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.conway.MainConwayLifeJava;
import main.java.conway.devices.MockOutdev;
import main.java.conway.domain.GameController;
import main.java.conway.domain.IOutDev;
import main.java.conway.domain.Life;
import main.java.conway.domain.LifeController;
import main.java.conway.domain.LifeInterface;
import unibo.basicomm23.utils.CommUtils;


public class ConwayLifeTest {
	private static final int nRows=5;
	private static final int nCols=5;
	private LifeInterface lifemodel; 

	@Before
	public void setup() {
		System.out.println("ConwayLifeTest | setup");	
		lifemodel = Life.CreateLife(nRows,nCols);
	}

	@After
	public void down() {
		System.out.println("ConwayLifeTest | down");
	}

 
	@Test
	public void testAppl() {
		LifeInterface gameModel       = new Life(nRows, nCols);
		IOutDev outputDevice          = new MockOutdev();
		GameController lifeController = new LifeController(gameModel, outputDevice);
		int genTime                   = lifeController.getGenTime();
        lifeController.switchCellState(2, 1);
        lifeController.switchCellState(2, 2);
        lifeController.switchCellState(2, 3);
        lifeController.onStart();
        //gameModel.nextGeneration();
        int nstep = 4;
        int delay = genTime * nstep;
        lifeController.onStart();
        CommUtils.delay(delay);
        lifeController.onStop();
        assertTrue( lifeController.numEpoch() == (nstep-1) );
        lifeController.onClear();
        outputDevice.close();
        assertTrue( lifeController.numEpoch() == 0 );
        
//        MainConwayLifeJava app = new MainConwayLifeJava();
//        app.configureTheSystemWitMockOutdev();
	}
	
	
	//@Test
	public void testOscilla() {
		System.out.println("testOscilla ---------"  );
		// Configurazione orizzontale
	    lifemodel.setCell(2, 1, true); 
	    lifemodel.setCell(2, 2, true);
	    lifemodel.setCell(2, 3, true);
	    System.out.println("testOscilla | Stato Iniziale:\n" + lifemodel.getGrid());

	    lifemodel.nextGeneration();
	    System.out.println("testOscilla | after 1 gen:\n" + lifemodel.getGrid());
	    // Verifica che sia diventato verticale
	    assertTrue(lifemodel.isAlive(1, 2)); 
	    assertTrue(lifemodel.isAlive(2, 2));
	    assertTrue(lifemodel.isAlive(3, 2));
	    assertFalse(lifemodel.isAlive(2, 1));

	    lifemodel.nextGeneration();
	    System.out.println("testOscilla | after 2 gen :\n" + lifemodel.getGrid());
	    // Verifica che sia tornato orizzontale (Periodo 2)
	    assertTrue(lifemodel.isAlive(2, 1));
	    assertTrue(lifemodel.isAlive(2, 2));
	    assertTrue(lifemodel.isAlive(2, 3));
	}	

	//@Test
	//Eliminato perchè basato su rappresentazione concreta
//	public void testOscillaFromFile() throws Exception {
//	    // Carico un Blinker (periodo 2)
//	    System.out.println( "testOscillaFromFile ---------------------" );
//	    boolean[][] initial = PatternLoader.loadFromResource("src/test/resources/blinker.txt", 5, 5);
//	    
// 	    Life lifemodel = new Life(initial);
//	    
//	    System.out.println( lifemodel.gridRep() );
//	    System.out.println( "________________________ testOscillaFromFile " );    
////
//	    lifemodel.nextGeneration(); // Generazione 1 (cambia stato)
//	    System.out.println( lifemodel.gridRep() );
//	    System.out.println( "________________________ testOscillaFromFile " );
//	    lifemodel.nextGeneration(); // Generazione 2 (deve tornare all'originale)
//	    System.out.println( lifemodel.gridRep() );
//	    System.out.println( "________________________ testOscillaFromFile " );
////
// 	    assertArrayEquals("L'oscillatore deve tornare allo stato iniziale dopo 2 passi", 
// 	                      initial, lifemodel.getGrid());
//	}
	
/*
L'Approccio "Test-First" (TDD): 

Si scrive il test prima che la classe esista. Il test fallirà (non compila nemmeno). 
Questo la costringe a definire l'interfaccia (i nomi dei metodi, i parametri) 
dal punto di vista dell'utilizzatore e non dell'implementatore.

Scrivere il test per il "Blinker" costringe a decidere subito: 
"Come passo la griglia? Con una matrice di booleani? Con un array di oggetti Cell? 
Come leggo il risultato?".

- Definire l'interfaccia per prima, ha creato la Teoria. 
- I test diventeranno la Verifica della Teoria 
- La classe implementativa sarà la Pratica.

L'approccio di testare le regole di base su configurazioni specifiche (i cosiddetti 
"test unitari su pattern noti") rappresenta la fondamenta del testing 
per il Gioco della Vita. 

Tuttavi la sfida nel software scientifico o simulativo è garantire che il sistema si comporti 
correttamente anche su scale più ampie o in casi limite.	 

#. "Pattern Canonici" Invece di inventare configurazioni, 
   utilizziamo le classi di oggetti definite da Conway.
#. "Property-Based Testing" : si testano le leggi del mondo di gioco.

  - Il test del "Mondo Vuoto"
  - Il test della "Morte per Solitudine"
  - Invarianza per Traslazione
  - comportamento delle celle sui bordi della matrice


+++ Data-Driven Testing: 

- i test non devono essere necessariamente "hard-coded".
- La logica di evoluzione è separata dalla definizione dello stato iniziale.
- Fare sfide per trovare configurazioni che "rompono" le implementazioni altrui 
  (ad esempio configurazioni molto grandi che testano i limiti di memoria).

+++ Nota tecnica

Se nel costruttore si passa l'array direttamente 
(this.grid = initialGrid), si sta passando un riferimento. 
Se il test modifica la matrice, modifica anche l'interno della classe, 
rendendo i test inaffidabili.

una "copia profonda" (deep copy) della matrice è un'ottima lezione collaterale 
su come Java gestisce la memoria.

Occorrono due matrici (gridA e gridB) 

per evitare di dover creare una nuova matrice ad ogni generazione.
Lo scambio temp = gridA; gridA = gridB; gridB = temp; avviene in tempo costante

+++ Matrici sparse

In una configurazione tipica, il 90% della griglia è "morta" (vuota). 
Invece di memorizzare il vuoto, memorizziamo solo le celle vive.

Memorizziamo solo dove c'è vita. Se un punto non è nella mappa, è morto.
Map<Point, Boolean> liveCells = new HashMap<>();

Coordinate illimitate: Un punto può avere coordinate (1000000, 5000000). 
La mappa conterrà solo quella coordinata. Non abbiamo dovuto allocare i milioni di "vuoto" 
che la separano dall'origine.

+++ Crescita dinamica: 

Se una cella nasce a una distanza enorme, la mappa si limita ad aggiungere una voce. 
La "griglia" si espande virtualmente seguendo la vita, senza confini predefiniti

l'interfaccia protegge il comportamento, indipendentemente dalla struttura dati scelta.

Come si calcola in questo caso la nextGeneration con una Map?
 
- Si prendono tutte le celle vive presenti nella mappa.
- Si crea un set di "candidate": sono le celle vive più tutti i loro vicini 
  (perché solo lì può nascere nuova vita).
- Per ogni candidata, si contano i vicini vivi guardando nella mappa.
- Se la regola dice che sopravvive o nasce, la si inserisce nella nextMap.

*/
	
	
}
