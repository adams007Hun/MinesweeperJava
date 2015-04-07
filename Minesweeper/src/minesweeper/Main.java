package minesweeper;

import minesweeper.gui.MinesweeperGui;


public class Main
{
	public static void main(String[] args)
	{
		Board board = new Board(15,15,40,0,0);
		MinesweeperGui gui = new MinesweeperGui(board);
		
		board.Display();
		System.out.println("Az ismeretle mezõk száma: " + board.getNumUnknown() );
		
		//System.out.println( board.closeMines(3, 1) );
	}

}
