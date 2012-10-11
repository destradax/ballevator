package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	
	private ServerSocket serverSocket;
	private int port;
	
	private Socket client1 = null;
	private Socket client2 = null;
	private PrintWriter out1 = null;
	private PrintWriter out2 = null;
	
	public Server(int port){
		this.port = port;
		initNetwork();
	}

	public void initNetwork(){
		try {
			serverSocket = new ServerSocket(port);
		} catch (Exception e) {
			System.out.print("Couldn't start listening on port " + port + ": ");
			System.err.println(e.getMessage());
			System.exit(1);
		}
		
		System.out.println("Waiting for client 1...");
        try {
        	client1 = serverSocket.accept();
        	out1 = new PrintWriter(client1.getOutputStream(), true);
        } catch (IOException e) {
        	System.err.println("Coud not get output strem for client 1");
        	terminateServer();
        	System.exit(2);
		} catch (Exception e) {
            System.err.println("Could not accept client1");
            terminateServer();
            System.exit(3);
        }
        
        System.out.println("Waiting for client 2...");
        try {
        	client2 = serverSocket.accept();
        	out2 = new PrintWriter(client2.getOutputStream(), true);
        } catch (IOException e) {
        	System.err.println("Coud not get output strem for client 1");
        	terminateServer();
        	System.exit(2);
		} catch (Exception e) {
            System.err.println("Could not accept client2");
            terminateServer();
            System.exit(3);
        }
        
        new DataReceiver(this, client1).start();
        new DataReceiver(this, client2).start();
        out1.println("l");
        out2.println("r");
        out1.println("s");
        out2.println("s");
	}

	public void processMessage(String message){
		System.out.println("Broadcasting message to all clients: " + message);
		out1.println(message);
		out2.println(message);
		if (message.equals("q")){
			terminateServer();
		}
	}
	
	private void terminateServer(){
		System.out.println("Closing server...");
		try{
			out1.close();
			client1.close();
			serverSocket.close();
		}catch (Exception e){
			System.err.println("Error closing server: ");
			System.err.println(e.getMessage());
			System.exit(4);
		}
	}
	
	public static void main(String [] args){
		new Server(4444);
	}
}
