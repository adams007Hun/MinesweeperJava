package minesweeper.gui;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import minesweeper.Board;
import minesweeper.Cell;
import minesweeper.CellState;
import minesweeper.Control;


public class MineBoardPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private static int rows = 15;
	private static int columns = 15;

	private MineButton mineField[][];
	private Board board;
	private Control control;
	private boolean playable;
	
	public void updateBoard(Board received) {
		if (board == null)
			return;
		board = received;
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < columns; j++)
			{
				mineField[i][j].updateButton(board.getCells()[i][j]);
			}
		}
		return;
	}

	public MineBoardPanel(Control _ctrl, boolean _playable)
	{
		super(new GridLayout(rows, columns, 0,0));
		this.control = _ctrl;
		this.board = new Board(15,15,control.getMineCount());
		this.playable = _playable;
		mineField = new MineButton[rows][columns];
		this.setSize(rows*20, columns*20);
		
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < columns; j++)
			{
				mineField[i][j] = new MineButton();
				if (playable)
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
			Cell clickedCell = board.getCellAt(r,c);
			if (e.getButton() == MouseEvent.BUTTON3)
			{
				if (clickedCell.getCellState() == CellState.Hidden) {
					board.flag(r, c);
					((MineButton)e.getSource()).updateButton(clickedCell);
				}
				else if (clickedCell.getCellState() == CellState.Flagged) {
					board.unFlag(r,c);
					((MineButton)e.getSource()).updateButton(clickedCell);
				}
			}
			else
			{
				// Debug only message
				//System.out.print(r + " " + c + "\n");
				if (clickedCell.getCellState() == CellState.Hidden) {
					board.reveal(r, c);
					updateBoard(board);
					if (board.getWin())
					{
						JOptionPane.showMessageDialog(null, "You win the game! :)");
					}
					else if (board.getDefeat())
					{
						JOptionPane.showMessageDialog(null, "You lost the game! :(");
					}
				}
			}
			control.sendBoard(board);
		}
		
	}
}
