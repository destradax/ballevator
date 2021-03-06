package server;

import java.io.*;
import java.net.Socket;

public class DataReceiver extends Thread {

	private Server server;
	private Socket socket;
	private BufferedReader in;

	public DataReceiver(Server server, Socket socket){
		this.server = server;
		this.socket = socket;
	}
	
	public void run(){
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputLine;
            System.out.println("Listening...");
            do{
            	inputLine = in.readLine();
            	server.processMessage(inputLine);
            	if (inputLine == null || inputLine.equals("q")) {
            		break;
            	}
            }while (true);

            System.out.println("Closing DataServer");
            in.close();
        } catch (IOException e) {
            System.out.println("Connection Reset by client");
        }
	}
}
