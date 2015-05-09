package minesweeper;

abstract class Network {
	protected Control ctrl;

	Network(Control c) {
		ctrl = c;
	}

	abstract int connect(String ip);

	abstract void disconnect();

	abstract void sendBoard(Board messageBoard);

}
