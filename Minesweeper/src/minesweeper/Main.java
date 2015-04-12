package minesweeper;


import minesweeper.gui.MinesweeperGui;


public class Main
{
	public static void main(String[] args)
	{
		Control c = new Control();
		MinesweeperGui gui = new MinesweeperGui(c);
		c.setGUI(gui);
		
		
		class ClientProbe implements Runnable {

			public void run() {
				Control cClient = new Control();
				cClient.startClient();
			}
		}
		
		c.startServer();
		Thread rec = new Thread(new ClientProbe());
		rec.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.sendBoard(c.getLocalBoard());
	}

}
