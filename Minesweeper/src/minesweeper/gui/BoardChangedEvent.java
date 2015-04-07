package minesweeper.gui;

import java.util.EventObject;

public class BoardChangedEvent extends EventObject
{
	private static final long serialVersionUID = 1L;
	private int row;
	private int column;
	
	
	public BoardChangedEvent(Object source, int row, int column)
	{
		super(source);
		this.row = row;
		this.column = column;
	}
	
	public int getRow() {
		return row;
	}


	public int getColumn() {
		return column;
	}
}
