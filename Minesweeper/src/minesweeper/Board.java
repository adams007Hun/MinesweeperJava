package minesweeper;

import java.util.Random;

public class Board{
	public static final int BOMB = 0xB;
	
	// Board size
	private int row,column;
	
	// The board
	private Cell [][] cells;
	
	// Number of mines, flagged, hidden
	private int numMines;
	private int numFlagged;
	private int numHidden;
	
	
	/*   */
	public Board(int row, int column, int numMines, int firstRow, int firstColumn){
		// firstRow/Column can't be mine
		this.row = row;
		this.column = column;
		this.numMines = numMines;
		this.numFlagged = 0;
		this.numHidden = row * column;
		
		// Allocate storage for game board
		// CellValue = 0, CellState = Hidden to all cell
		cells = new Cell[row][column];
		// and create the objects
	    for(int i = 0; i < cells.length; i++){
	    	for(int j = 0; j < cells[i].length; j++){
	    		cells[i][j] = new Cell(CellState.Hidden, 0);
	    	}
	    }
		
		this.genMines(firstRow, firstColumn);
	    
		// Fill Values
		this.fillValues();
		
		// Set state to clicked
		this.reveal(firstRow, firstColumn);
		this.updateNumHidden();
		
	}
	
	private void fillValues(){
		//Fill the values of the cells
		for(int i = 0; i < this.row; i++){
			for(int j = 0; j < this.column; j++){
				this.cells[i][j].setCellValue(this.closeMines(i, j));
			}
		}
	}
	
	private int closeMines(int row, int column){
		int minrow, maxrow, mincolumn, maxcolumn;
		int result = 0;
		
		if(this.cells[row][column].getCellValue() == BOMB)
			return BOMB;
		
		// Don't check outside
		if(row <= 0)
			minrow = 0;
		else 
			minrow = row-1;
		if(column <= 0)
			mincolumn = 0;
		else 
			mincolumn = column-1;
		
		if(row >= this.row-1 )
			maxrow = this.row-1;
		else
			maxrow = row+1;
		if(column >= this.column-1)
			maxcolumn = this.column-1;
		else
			maxcolumn = column+1;
		//----
		for (int i = minrow; i <= maxrow; i++){
			for(int j = mincolumn; j <= maxcolumn; j++){
				if( this.cells[i][j].getCellValue() == BOMB )
					result++;
			}
		}	
		return result;
	}
	
	private void genMines(int firstRow, int firstColumn){
		// Generate mines
		int temp = 0;
		Random rand = new Random();
		
		while(temp < numMines){
			int nextMineRow = rand.nextInt(row);
			int nextMineColumn = rand.nextInt(column);
			
			if( (cells[nextMineRow][nextMineColumn].getCellValue() != BOMB) && !(( nextMineRow == firstRow ) && ( nextMineColumn == firstColumn ))  ){
				cells[nextMineRow][nextMineColumn].setCellValue(BOMB);
				temp++;
			}
		}
	}
	
	private void updateNumHidden(){
		
		for(int i=0; i<this.row;i++){
			for(int j=0; j<this.column;j++){
				if(this.cells[i][j].getCellState() != CellState.Hidden)
					this.numHidden--;
			}
		}
		
		
	}

	/* Change the state of the cell, and reveal recursively */
	public void reveal(int row, int column){
		int minrow, mincolumn, maxrow, maxcolumn;
		
		
		if( this.cells[row][column].getCellValue() != 0 ){
			cells[row][column].setCellState(CellState.Clicked);
			return;
		}
		
		// Edges
		minrow = ( row <= 0 ? 0 : row-1 );
		mincolumn = ( column <= 0 ? 0 : column-1 );
		maxrow = ( row >= this.row-1 ? this.row-1 : row+1 );
		maxcolumn = ( column >= this.column-1 ? this.column-1 : column+1 );
		
		// Loop
		for(int i = minrow; i<=maxrow; i++){
			for(int j = mincolumn; j<=maxcolumn;j++){
				if(this.cells[i][j].getCellState() == CellState.Hidden ){
					this.cells[i][j].setCellState(CellState.Clicked);
					reveal(i,j);
				}
			}
		}
		
	}

	
	public void flag(int row, int column){
		// Change the state to flagged
		cells[row][column].setCellState(CellState.Flagged);
		numFlagged++;
	}
	
	public void unFlag(int row, int column){
		// Change the state to hidden
		cells[row][column].setCellState(CellState.Hidden);
		numFlagged--;
	}

	
	public void Display(){
		// Display the board 
		int i,j;
		int val;
		
		for(i=0; i < row; i++){
			for(j=0; j < column; j++){
				val = cells[i][j].getCellValue();
				if( val == BOMB )
					System.out.print( "B" );
				else
					System.out.print( val );
			}
			System.out.print( "\n" );
		}
		System.out.print( "\n" );
		for(i=0; i < row; i++){
			for(j=0; j < column; j++){
				
				if( cells[i][j].getCellState() == CellState.Clicked )
					System.out.print( "1" );
				else
					System.out.print( "0" );
			}
			System.out.print( "\n" );
		}
		
	}


	/* Setters, Getters */
	public int getrow() {
		return row;
	}
	public void setrow(int row) {
		this.row = row;
	}
	public int getcolumn() {
		return column;
	}
	public void setcolumn(int column) {
		this.column = column;
	}
	public int getNumMines() {
		return numMines;
	}
	public void setNumMines(int numMines) {
		this.numMines = numMines;
	}
	public int getNumFlagged() {
		return numFlagged;
	}
	public void setNumFlagged(int numFlagged) {
		this.numFlagged = numFlagged;
	}
	public int getNumUnknown() {
		return numHidden;
	}
	public void setNumUnknown(int numUnknown) {
		this.numHidden = numUnknown;
	}
	/*---------------------------*/
	
	
}
