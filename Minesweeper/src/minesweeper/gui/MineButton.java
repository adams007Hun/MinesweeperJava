package minesweeper.gui;

import java.awt.Insets;

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
		this.setMargin(new Insets(0,0,0,0));
	}
	
	/**
	 * 
	 * @param cell
	 */
	public void updateButton(Cell cell)
	{
		switch (cell.getCellState())
		{
			case Hidden:
				this.setText("");
				break;
			case Clicked:
				if(cell.getCellValue() == Cell.BOMB)
					this.setText("M");
				else
					this.setText(String.valueOf(cell.getCellValue()));
				this.setEnabled(false);
				break;
			case Flagged:
				this.setText("#");
				break;
			default:
				break;
		}
	}
}
