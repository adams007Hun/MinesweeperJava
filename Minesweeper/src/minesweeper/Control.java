package minesweeper;

import minesweeper.gui.MinesweeperGui;

public class Control
{	
	private Network net;
	private MinesweeperGui gui;
	
	private static final int size = 15;
	int mineCount = 40; // TODO: set from GUI

	public Control()
	{
	}
	
	void setGUI(MinesweeperGui g) {
		gui = g;
	}
	
	public int getMineCount() {
		return mineCount;
	}

	public void setMineCount(int mineCount) {
		this.mineCount = mineCount;
	}

	public void startServer() {
		if (net != null)
			net.disconnect();
		net = new SerialServer(this);
		net.connect("localhost");
	}

	public void startClient(String ip) {
		if (net != null)
			net.disconnect();
		net = new SerialClient(this);
		net.connect("localhost");
	}

	public void sendBoard(Board sendableBoard) {
		if (net == null)
			return;
		net.sendBoard(sendableBoard);
	}

	public void boardReceived(Board receivedBoard) {
		if (gui == null)
			return;
		gui.updateRemoteBoard(receivedBoard);
		System.out.println("Jipyyyy new board came, so fluffy");
	}

}
