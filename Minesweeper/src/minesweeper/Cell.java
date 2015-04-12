package minesweeper;

import java.io.Serializable;

public class Cell implements Serializable
{
	//+1 version to the board number
	private static final long serialVersionUID = 3074637006192374139L;

	public static final int BOMB = 0xB;
	
	private CellState cellState;
	private int cellValue;
	
	public Cell(CellState cellState, int cellValue)
	{
		this.setCellState(cellState);
		this.setCellValue(cellValue);
	}

	public CellState getCellState() {
		return cellState;
	}

	public void setCellState(CellState cellState) {
		this.cellState = cellState;
	}

	public int getCellValue() {
		return cellValue;
	}

	public void setCellValue(int cellValue) {
		this.cellValue = cellValue;
	}
}
