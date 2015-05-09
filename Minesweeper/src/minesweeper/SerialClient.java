package minesweeper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class SerialClient extends Network{

	private Socket socket = null;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
	private ObjectInputStream inMineNum = null;

	SerialClient(Control ctrl) {
		super(ctrl);
	}

	private class ReceiverThread implements Runnable {

		public void run() {
			System.out.println("CLIENT: Waiting for message...");
			try {
				while (true) {
					Board received = (Board) in.readObject();
					ctrl.boardReceived(received);
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				System.err.println("CLIENT: Server disconnected!");
			} finally {
				disconnect();
			}
		}
	}

	@Override
	void connect(String ip) {
		disconnect();
		try {
			socket = new Socket(ip, 10007);

			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			out.flush();
			inMineNum = new ObjectInputStream(socket.getInputStream());
			int GameMineNum = inMineNum.readInt();
			ctrl.setMineCount(GameMineNum);

			Thread rec = new Thread(new ReceiverThread());
			rec.start();
		} catch (UnknownHostException e) {
			System.err.println("CLIENT: Don't know about host");
		} catch (IOException e) {
			System.err.println("CLIENT: Couldn't get I/O for the connection. ");
			JOptionPane.showMessageDialog(null, "CLIENT: Cannot connect to server!");
		}
	}

	@Override
	void sendBoard(Board messageBoard) {
		if (out == null)
			return;
		System.out.println("CLIENT: Sending board to Server");
		try {
			out.writeObject(messageBoard);
			out.flush();
			out.reset();
		} catch (IOException ex) {
			System.err.println("CLIENT: Send error.");
		}
	}

	@Override
	void disconnect() {
		try {
			if (out != null)
				out.close();
			if (in != null)
				in.close();
			if (inMineNum != null)
				inMineNum.close();
			if (socket != null)
				socket.close();
		} catch (IOException ex) {
			System.err.println("CLIENT: Error while closing conn.");
		}
	}
}
