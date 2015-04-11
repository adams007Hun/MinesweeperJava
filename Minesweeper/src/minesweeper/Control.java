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

	void sendBoard(String message) {				/*TODO ne stringküldés hanem board*/
		// gui.addPoint(p); //for drawing locally
		if (net == null)
			return;
		net.sendBoard(message);						/*TODO ne stringküldés hanem board*/
	}

	void boardReceived(String message) {
		if (gui == null)
			return;
													/*TODO GUI kirajzol*/
	}

}
