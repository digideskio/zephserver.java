package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {

	public static final String root="c:\\test\\"; 
	
	// Port to listen for connections on
	private static final int recvPort = 80;
	
	public static void main(String[] args) {
		new SimpleServer().go();
	}

	public void go() {
		System.out.println("HTTP server started.");
		try {
			ServerSocket server = new ServerSocket(recvPort);
			System.out.println("Listening on: " + server);
			Socket recv;
			while (true)
			{
				// Wait for a connection [Block main thread]
				recv = server.accept();
				try {
					// Start up a listener thread for it
					new Handler(recv).start();
				}
				catch (IOException e) {
					System.err.println("Failed to handle connection. " + e);
				}
			}
		} 
		catch (Exception e) 
		{
			System.err.println("FATAL EXCEPTION: " + e);
			e.printStackTrace();
		}
	}
}
