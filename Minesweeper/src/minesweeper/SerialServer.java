package minesweeper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SerialServer extends Network{
	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;

	SerialServer(Control ctrl) {
		super(ctrl);
	}

	private class ReceiverThread implements Runnable {

		public void run() {
			try {
				System.out.println("SERVER: Waiting for Client");
				clientSocket = serverSocket.accept();
				ctrl.clientConnected();
				System.out.println("SERVER: Client connected.");
			} catch (IOException e) {
				System.err.println("SERVER: Accept failed.");
				disconnect();
				return;
			}

			try {
				out = new ObjectOutputStream(clientSocket.getOutputStream());
				in = new ObjectInputStream(clientSocket.getInputStream());
				out.flush();
			} catch (IOException e) {
				System.err.println("SERVER: Error while getting streams.");
				disconnect();
				return;
			}

			try {
				while (true) {
					Board received = (Board) in.readObject();
					ctrl.boardReceived(received);
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				System.err.println("SERVER: Client disconnected!");
			} finally {
				disconnect();
			}
		}
	}

	@Override
	void connect(String ip) {
		disconnect();
		try {
			serverSocket = new ServerSocket(10007);

			Thread rec = new Thread(new ReceiverThread());
			rec.start();
		} catch (IOException e) {
			System.err.println("SERVER: Could not listen on port: 10007.");
		}
	}

	@Override
	void sendBoard(Board messageBoard) {
		if (out == null)
			return;
		System.out.println("SERVER: Sending a board to Client");
		try {
			out.writeObject(messageBoard);
			out.flush();
			out.reset();
		} catch (IOException ex) {
			System.err.println("SERVER: Send error.");
		}
	}

	@Override
	void disconnect() {
		try {
			if (out != null)
				out.close();
			if (in != null)
				in.close();
			if (clientSocket != null)
				clientSocket.close();
			if (serverSocket != null)
				serverSocket.close();
		} catch (IOException ex) {
			Logger.getLogger(SerialServer.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

}
