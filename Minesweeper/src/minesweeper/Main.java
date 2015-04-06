package minesweeper;


public class Main
{
	public static void main(String[] args)
	{
		//MinesweeperGui gui = new MinesweeperGui();
		Board board = new Board(4,5,3,0,0);
		board.Display();
		System.out.println("Az ismeretle mezõk száma: " + board.getNumUnknown() );
		
		//System.out.println( board.closeMines(3, 1) );
	}

}
