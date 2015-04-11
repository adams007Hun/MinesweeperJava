package minesweeper.gui;

import java.awt.Insets;

import javax.swing.JButton;

import minesweeper.Cell;

// MineButton is the graphical implementation of a single tile of the board.
// It is basically a JButton with a few extra features.
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
	 * The method updateButton sets the text on the button based on 
	 * the given parameter
	 * @param cell: the cell to be displayed
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
