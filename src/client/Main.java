package client;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;



public class Main {
	public static void main(String [] args){
		
		if (args.length != 2){
			System.out.println("Please specify arguments: host, port");
			System.exit(1);
		}
		String host = args[0];
		int port = Integer.parseInt(args[1]);
		
		int width, height, framerate;
		float speedL, speedR;
		
		MessageQueue messages = new MessageQueue();
		InputListener inputListener = new InputListener(messages, host, port);
		
		while (messages.isEmpty());
		width = Integer.parseInt(messages.getMessage());
		System.out.println("Window Width: " + width);
		while (messages.isEmpty());
		height = Integer.parseInt(messages.getMessage());
		System.out.println("Window Height: " + height);
		while (messages.isEmpty());
		framerate = Integer.parseInt(messages.getMessage());
		System.out.println("Target Framerate: " + framerate);
		while (messages.isEmpty());
		speedL = Float.parseFloat(messages.getMessage());
		System.out.println("Platform Left Speed: " + speedL);
		while (messages.isEmpty());
		speedR = Float.parseFloat(messages.getMessage());
		System.out.println("Platform Right Speed: " + speedR);
		
		try {
			Client client = new Client(inputListener, messages, width, height, speedL, speedR);
            AppGameContainer app = new AppGameContainer(client);
            app.setDisplayMode(width, height, false);
            app.setTargetFrameRate(framerate);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
	}
}
