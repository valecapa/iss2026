package main.java.conway.domain;

public interface GameController {
	 int getGenTime();
	 void switchCellState(int x, int y);
	 void onStart();
	 void onStop();
	 void onClear();
	 int numEpoch();
}
