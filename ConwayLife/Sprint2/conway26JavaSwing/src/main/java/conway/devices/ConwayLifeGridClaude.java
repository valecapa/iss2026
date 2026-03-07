package main.java.conway.devices;
import javax.swing.*;
import main.java.conway.domain.GameController;
import main.java.conway.domain.ICell;
import main.java.conway.domain.IGrid;
import main.java.conway.domain.IOutDev;
import unibo.basicomm23.utils.CommUtils;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Rappresentazione grafica di una griglia 20x20 per il gioco Conway's Life.
 * Le celle possono essere morte (bianche) o vive (rosse).
 */
public class ConwayLifeGridClaude extends JFrame implements IOutDev {
    
    private static final int GRID_SIZE = 20;
    private static final int CELL_SIZE = 25;
    private static final Color DEAD_COLOR = Color.WHITE;
    private static final Color ALIVE_COLOR = Color.RED;
    
    private boolean[][] gridRep;
    private JPanel gridPanel;
    private GameController controller;
     
    
    /**
     * Costruttore della griglia.
     * @param controller Il controller del gioco che riceverà i comandi START/STOP
     */
    public ConwayLifeGridClaude( GameController controller ) {
        this.controller = controller;
        this.gridRep    = new boolean[GRID_SIZE][GRID_SIZE];       
        initializeUI();
        setVisible(true);       
    } 
    public ConwayLifeGridClaude(   ) {
        this.gridRep = new boolean[GRID_SIZE][GRID_SIZE];       
        initializeUI();
        setVisible(true);       
    } 
    
    public void setController(GameController controller) {
    	this.controller = controller;
    }
    
    
    /**
     * Inizializza l'interfaccia grafica.
     */
    private void initializeUI() {
        setTitle("Conway's Game of Life Swing GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Pannello della griglia
        gridPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGrid(g);
            }
        };
        gridPanel.setPreferredSize(new Dimension(
            GRID_SIZE * CELL_SIZE, 
            GRID_SIZE * CELL_SIZE
        ));
        gridPanel.setBackground(Color.LIGHT_GRAY);
        
        // Aggiunge listener per il click del mouse
        gridPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleCellClick(e.getX(), e.getY());
            }
        });
        
        add(gridPanel, BorderLayout.CENTER);
        
        // Pannello dei pulsanti
        JPanel buttonPanel = new JPanel();
        
        JButton startButton = new JButton("START");
        startButton.addActionListener(e -> {
        	//CommUtils.outblue("clicked START "  + controller);
            if (controller != null) {
                controller.onStart();
            }
        });
        
        JButton stopButton = new JButton("STOP");
        stopButton.addActionListener(e -> {
            if (controller != null) {
                controller.onStop();
            }
        });

        JButton clearButton = new JButton("CLEAR");
        clearButton.addActionListener(e -> {
            if (controller != null) {
            	CommUtils.outmagenta("clear");
                controller.onClear();
            }
        });

        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(clearButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    /**
     * Disegna la griglia.
     */
    private void drawGrid(Graphics g) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                // Imposta il colore in base allo stato della cella
                g.setColor(gridRep[row][col] ? ALIVE_COLOR : DEAD_COLOR);
                g.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                
                // Disegna il bordo della cella
                g.setColor(Color.GRAY);
                g.drawRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }
    
    /**
     * Gestisce il click su una cella.
     */
    private void handleCellClick(int x, int y) {
        int col = x / CELL_SIZE;
        int row = y / CELL_SIZE;
        CommUtils.outblue("handleCellClick x=" + x + " y=" + y + " row="+row+ " col="+col);
        if (row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE) {
            //toggleCell(row, col);
            controller.switchCellState(row,col);
        }
    }
    
    /**
     * Commuta lo stato di una cella (da morta a viva e viceversa).
     * @param row Riga della cella
     * @param col Colonna della cella
     */
    public void toggleCell(int row, int col) {
        if (row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE) {
            gridRep[row][col] = !gridRep[row][col];
            gridPanel.repaint();
        }
    }
    
    /**
     * Imposta lo stato di una cella specifica.
     * @param row Riga della cella
     * @param col Colonna della cella
     * @param alive true se la cella deve essere viva, false se morta
     */
    public void setCell(int row, int col, boolean alive) {
        if (row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE) {
            gridRep[row][col] = alive;
            gridPanel.repaint();
        }
    }
    
    /**
     * Visualizza l'intera griglia con uno stato specifico.
     * @param newGrid Array bidimensionale che rappresenta lo stato della griglia
     */
     
    public void displayGrid(boolean[][] newGrid) {
    	//CommUtils.outcyan("displayGrid");
        if (newGrid != null && newGrid.length == GRID_SIZE && 
            newGrid[0].length == GRID_SIZE) {
            for (int row = 0; row < GRID_SIZE; row++) {
                System.arraycopy(newGrid[row], 0, gridRep[row], 0, GRID_SIZE);
            }
            gridPanel.repaint();
        }
    }
    
    /**
     * Restituisce lo stato attuale della griglia.
     * @return Array bidimensionale che rappresenta lo stato della griglia
     */
    public boolean[][] getGrid() {
        boolean[][] copy = new boolean[GRID_SIZE][GRID_SIZE];
        for (int row = 0; row < GRID_SIZE; row++) {
            System.arraycopy(gridRep[row], 0, copy[row], 0, GRID_SIZE);
        }
        return copy;
    }
    
    /**
     * Pulisce la griglia (tutte le celle morte).
     */
    public void clearGrid() {
        gridRep = new boolean[GRID_SIZE][GRID_SIZE];
        gridPanel.repaint();
    }
    

	@Override //IOutDev
	public void display(String msg) {
		CommUtils.outyellow("GuiClaude | display  " + msg);
		
	}

 	
	@Override //IOutDev
	public void displayCell(IGrid grid, int x, int y) {
		try {
			ICell cell = grid.getCell(x, y);
			//CommUtils.outyellow("GuiClaude | displayCell " + cell);
			boolean value = cell.isAlive() ;
			String msg = "cell(" + y + "," + x + ","+ value + ")";		
			setCell(x,y,value);
		} catch (Exception e) {
			CommUtils.outred("GuiClaude | displayCell ERROR");
  		}		
		
	}
	
	
	protected boolean[][] gridAsBoolArray(IGrid grid) {
		int rows = GRID_SIZE;
		int cols = GRID_SIZE;
		boolean[][] simplegrid = new boolean[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				simplegrid[i][j] = grid.getCell(i, j).isAlive();
			}
		}
		return simplegrid;
	}
	
	@Override //IOutDev
	public void displayGrid(IGrid newGrid) {
		displayGrid( gridAsBoolArray(newGrid) );	
	}	
	
	@Override
	public void close() {
		// TODO Auto-generated method stub		
	}

	
    /**
     * Esempio di utilizzo.
     */
    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            GameController controller = new GameController() {
//                @Override
//                public void onStart() {
//                    System.out.println("Gioco avviato!");
//                }
//                
//                @Override
//                public void onStop() {
//                    System.out.println("Gioco fermato!");
//                }
//            };
//            
//            ConwayLifeGridClaude grid = new ConwayLifeGridClaude(controller);
//            grid.setVisible(true);
//        });
    }


 

	
}