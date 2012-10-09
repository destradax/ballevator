package client;

import java.io.*;
import java.net.Socket;

public class DataReceiver extends Thread {

	private InputListener inputListener;
	private Socket socket;
	private BufferedReader in;
	
	public DataReceiver(InputListener inputListener, Socket socket){
		this.inputListener = inputListener;
		this.socket = socket;
	}
	
	public void run(){
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputLine;
            inputListener.setClientId(in.readLine());
//            System.out.println("Listening...");
            while ((inputLine = in.readLine()) != null) {
                inputListener.filterMessage(inputLine);
                if (inputLine.equals("q")) {
                    break;
                }
            }
            in.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Connection Reset by client");
        }
	}
}
