package minesweeper;

import minesweeper.gui.MinesweeperGui;

public class Control
{	
	private Network net;
	private MinesweeperGui gui;
	
	private Board localBoard;
	private Board remoteBoard;
	
	private static final int size = 15;
	int mineCount = 40; // TODO: set from GUI

	public Control()
	{
		localBoard = new Board(size, size, mineCount);
		remoteBoard = new Board(size, size, mineCount);
	}
	
	public Board getLocalBoard() {
		return localBoard;
	}

	public Board getRemoteBoard() {
		return remoteBoard;
	}
	
	void setGUI(MinesweeperGui g) {
		gui = g;
	}

	void startServer() {
		if (net != null)
			net.disconnect();
		net = new SerialServer(this);
		net.connect("localhost");
	}

	void startClient() {
		if (net != null)
			net.disconnect();
		net = new SerialClient(this);
		net.connect("localhost");
	}

	void sendBoard(Board sendableBoard) {
		if (net == null)
			return;
		net.sendBoard(sendableBoard);
	}

	void boardReceived(Board receivedBoard) {
		if (gui == null)
			return;
		System.out.println("Jipyyyy new board came, so fluffy");	/*TODO GUI kirajzol*/
	}

}
