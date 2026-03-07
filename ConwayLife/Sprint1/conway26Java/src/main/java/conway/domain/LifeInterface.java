package main.java.conway.domain;

public interface LifeInterface {
	/** Calcola l'evoluzione dello stato alla generazione successiva */
    void nextGeneration();

    /** Restituisce lo stato di una cella specifica */
    boolean isAlive(int row, int col);

    /** Imposta lo stato di una cella */
    void setCell(int row, int col, boolean alive);

    /** Restituisce il numero di righe e colonne */
//    int getRows();
//    int getCols();
    
    /** Restituisce la Cella */
    ICell getCell(int x, int y);
    
    /** Restituisce la grid */
    Grid getGrid();
    
    /** pulisce */
    void resetGrids();
    
    /** Restituisce una rappresentazione grafica testuale della grglia*/
    //public String gridRep( );
}
