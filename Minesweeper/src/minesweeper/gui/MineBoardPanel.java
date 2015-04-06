package minesweeper.gui;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class MineBoardPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private static int rows = 15;
	private static int columns = 15;

	private MineButton mineField[][];
	
	public MineBoardPanel()
	{
		super(new GridLayout(rows, columns, 0,0));
		mineField = new MineButton[rows][columns];
		this.setSize(rows*20, columns*20);
		
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < columns; j++)
			{
				mineField[i][j] = new MineButton();
				add(mineField[i][j]);
			}
		}
	}
}
