package client;

import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

public class InputListener implements KeyListener{

	private MessageQueue messages;
	
	private Socket socket = null;
	private PrintWriter out = null;
	private String host = "localhost";
	private int port = 4444;
	private DataReceiver dataReceiver;
	private String clientId = null;

	
	public InputListener(MessageQueue messages){
		this.messages = messages;
		
		try {
            socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + host);
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to: " + host);
            System.exit(1);
        }
		
		dataReceiver = new DataReceiver(this, socket);
		dataReceiver.start();
	}
	
	@Override
	public void inputEnded() {
	}

	@Override
	public void inputStarted() {
	}

	@Override
	public boolean isAcceptingInput() {
		return true;
	}

	@Override
	public void setInput(Input arg0) {
	}

	@Override
	public void keyPressed(int key, char c) {
		switch(key){
		case Input.KEY_UP:
			processMessage(clientId+"u");
			sendMessage("u");
			break;
		case Input.KEY_DOWN:
			processMessage(clientId+"d");
			sendMessage("d");
			break;
		case Input.KEY_Q:
			processMessage("q");
			sendMessage("q");
			break;
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		switch(key){
		case Input.KEY_UP:
		case Input.KEY_DOWN:
			processMessage(clientId+"s");
			sendMessage("s");
			break;
		}
	}
	
	public void processMessage(String message){
		messages.addMessage(message);
	}
	
	public void filterMessage(String message){
		if (!message.startsWith(clientId)){
			processMessage(message);
		}
	}
	
	public void sendMessage(String message){
		out.println(clientId+message);
	}

	public void setClientId(String clientId) {
		System.out.println("CLIentID:" + clientId);
		this.clientId = clientId;
	}
}
