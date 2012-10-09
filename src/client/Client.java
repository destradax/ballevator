package client;

import gameobjects.Platform;
import gameobjects.Scene;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;


public class Client extends BasicGame{
	
	private MessageQueue messages;
	private boolean quit = false;
	private InputListener inputListener;
	
	private Scene scene;
	private Platform platform; 
	
	public Client(String title) {
		super(title);
		messages = new MessageQueue();
		inputListener = new InputListener(messages);
		scene = new Scene();
		platform = scene.getPlatform();
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		platform.render(g);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		container.getInput().addKeyListener(inputListener);
		while(messages.isEmpty());
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		if (!messages.isEmpty()){
			processMessage(messages.getMessage());
		}
		
		platform.move();
		if (quit) container.exit();
	}
	
	private void processMessage(String message){
		System.out.println("Process: " + message);
		if (message.equals("q")) quit = true;
		if (message.equals("lu")){
			platform.leftUp();
			return;
		}
		if (message.equals("ld")){
			platform.leftDown();
			return;
		}
		if (message.equals("lru") || message.equals("lrd")){
			platform.leftStop();
			return;
		}
		if (message.equals("ru")){
			platform.rightUp();
			return;
		}
		if (message.equals("rd")){
			platform.rightDown();
			return;
		}
		if (message.equals("rru") || message.equals("rrd")){
			platform.rightStop();
			return;
		}
	}
	
	public static void main(String[] args) {
    	try {
    		Client client = new Client("Client");
            AppGameContainer app = new AppGameContainer(client);
            app.setDisplayMode(400, 400, false);
            app.setTargetFrameRate(60);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
