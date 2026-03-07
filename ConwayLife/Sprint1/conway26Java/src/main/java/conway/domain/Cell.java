package main.java.conway.domain;

public class Cell implements ICell {
	/* Definisco la rappresentazione concreta di una cella*/
    private int value;
    
    public Cell() {
    	value = -1;
    }
    
	@Override
	public void setStatus(boolean v) {
		if( v ) value = 1;
		else value = -1;
	}

	@Override
	public boolean isAlive() {
		return value > 0;
	}
	
	@Override
	public void switchCellState() {
		if( isAlive() ) value = -1;
		else value=1;
	}

}
