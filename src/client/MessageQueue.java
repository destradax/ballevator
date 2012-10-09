package client;
import java.util.LinkedList;
import java.util.Queue;


public class MessageQueue {
	private Queue<String> messages;
	
	public MessageQueue(){
		messages = new LinkedList<String>();
	}
	
	public void addMessage(String message){
		messages.add(message);
	}
	
	public String getMessage(){
		return messages.remove();
	}
	
	public boolean isEmpty(){
		return messages.isEmpty();
	}
	
	public void clear(){
		messages.clear();
	}
}
