package minesweeper.gui;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import minesweeper.Board;

public class MineBoardPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private static int rows = 15;
	private static int columns = 15;

	private MineButton mineField[][];
	private Board board;
	
	
	public void setBoard(Board board) {
		this.board = board;
	}

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
				mineField[i][j].addMouseListener(new MouseHandler(i,j));
				add(mineField[i][j]);
			}
		}
	}

	private class MouseHandler extends MouseAdapter
	{
		private int r,c;
		public MouseHandler(int r, int c)
	    {
	        this.r = r;
	        this.c = c;
	    }
		
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			System.out.print(r + " " + c + "\n");
			board.reveal(r, c);
			//((MineButton)e.getSource()).updateButton(new Cell(CellState.Clicked,Cell.BOMB));
		}
		
	}
}
