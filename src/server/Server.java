package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	
	private ServerSocket serverSocket;
	private int port;
	
	private int width, height, framerate;
	private float speedL, speedR;
	
	private Socket client1 = null;
	private Socket client2 = null;
	private PrintWriter out1 = null;
	private PrintWriter out2 = null;
	
	private boolean multiplayer;
	
	public Server(int width, int height, int framerate, float speedL, float speedR, int players){
		this.port = 4444;
		this.width = width;
		this.height = height;
		this.framerate = framerate;
		this.speedL = speedL;
		this.speedR = speedR;
		this.multiplayer = players == 2 ? true : false;
		initNetwork();
	}

	private void initNetwork(){
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
        if (multiplayer){
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
        }
        new DataReceiver(this, client1).start();
        out1.println("l");
        if (multiplayer){
        new DataReceiver(this, client2).start();
        out2.println("r");
        }
        
        out1.println(width);
        out1.println(height);
        out1.println(framerate);
        out1.println(speedL);
        out1.println(speedR);
        
        if (multiplayer){
        out2.println(width);
        out2.println(height);
        out2.println(framerate);
        out2.println(speedL);
        out2.println(speedR);
        }
	}

	public void processMessage(String message){
		System.out.println("Broadcasting message to all clients: " + message);
		out1.println(message);
		if(multiplayer){out2.println(message);}
		if (message == null || message.equals("q")){
			terminateServer();
		}
	}
	
	private void terminateServer(){
		System.out.println("Closing server...");
		try{
			out1.close();
			client1.close();
			if(multiplayer){ 
			out2.close();
			client2.close();
			}
			serverSocket.close();
		}catch (Exception e){
			System.err.println("Error closing server: ");
			System.err.println(e.getMessage());
			System.exit(4);
		}
	}
	
	public static void main(String [] args){
		
		if (args.length != 6){
			System.out.println("Please specify arguments: width, height, framerate, speedL, speedR players");
			System.exit(1);
		}
		
		int width = Integer.parseInt(args[0]);
		int height = Integer.parseInt(args[1]);
		int framerate = Integer.parseInt(args[2]);
		float speedL = Float.parseFloat(args[3]);
		float speedR = Float.parseFloat(args[4]);
		int players = Integer.parseInt(args[5]);
		
		//TODO check what is the minimum window size required to display everything properly
		if (width < 100 || height < 100){
			System.out.println("Width and height should both be greater than 100");
			System.exit(1);
		}
		
		System.out.println("Window Width: " + width);
		System.out.println("Platform Height: " + height);
		System.out.println("Framerate: " + framerate);
		System.out.println("SpeedL: " + speedL);
		System.out.println("SpeedR: " + speedR);
		
		new Server(width, height, framerate, speedL, speedR, players);
	}
}
