package minesweeper.gui;

import javax.swing.JButton;

import minesweeper.Cell;

public class MineButton extends JButton
{
	private static final long serialVersionUID = 1L;
	
	// TODO get a cell in the constructor
	public MineButton()
	{
		super();
		this.setText("");
		this.setSize(20, 20);
	}
	
	public void updateButton(Cell cell)
	{
		switch (cell.getCellState())
		{
			case Hidden:
				this.setText("");
				break;
			case Clicked:
				this.setText(String.valueOf(cell.getCellValue()));
				break;
			case Flagged:
				this.setText("#");
				break;
			default:
				break;
		}
	}
}
