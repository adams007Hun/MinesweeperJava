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
	
	public static int getSize() {
		return size;
	}
	
	public int getMineCount() {
		return mineCount;
	}

	public void setMineCount(int mineCount) {
		this.mineCount = mineCount;
	}

	public int startServer() {
		if (net != null)
			net.disconnect();
		net = new SerialServer(this);
		return net.connect("localhost");
	}

	public int startClient(String ip) {
		if (net != null)
			net.disconnect();
		net = new SerialClient(this);
		//return net.connect("localhost");
		return net.connect(ip);
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
	
	public void clientConnected()
	{
		gui.clientConnectedToServer();
	}
	
	public void DisconnectionOccured()
	{
		gui.disconnectionOccured();
	}

}
