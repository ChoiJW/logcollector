package logcollector;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketDock implements Runnable {

	ServerSocket serverSocket;
	String logType;
	
	public SocketDock(int port, String logType) throws IOException
	{
		this.serverSocket = new ServerSocket(port);
		this.logType = logType;
	}
	
	public void run() {
		
		try {
			
			while (true) {
				Socket socket = serverSocket.accept();
				//cat.info("Connected to client at " + socket.getInetAddress());
				//cat.info("Starting new socket node.");
				new Thread(new SocketElement(socket, this.logType)).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
