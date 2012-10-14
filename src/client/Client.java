package client;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import scene.Ball;
import scene.Obstacle;
import scene.Platform;


public class Client extends BasicGame{
	
	private MessageQueue messages;
	private boolean quit = false;
	private InputListener inputListener;
	
	private Platform platform;
	private Ball ball;
	private List<Obstacle> obstacles;
	
	private int windowWidth, windowHeight;
	private float platformSpeedL, platformSpeedR;
	
	public Client(InputListener inputListener, MessageQueue messages, 
			int windowWidth, int windowHeight, float platformSpeedL, float platformSpeedR) {
		super("Ballevator");
		this.inputListener = inputListener;
		this.messages = messages;
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		this.platformSpeedL = platformSpeedL;
		this.platformSpeedR = platformSpeedR;
		resetGame();
	}
	
	private void resetGame(){
		platform = new Platform(windowWidth, windowHeight, platformSpeedL, platformSpeedR);
		ball = new Ball(platform);
		obstacles = new ArrayList<Obstacle>();
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		platform.render(g);
		ball.render(g);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		container.getInput().addKeyListener(inputListener);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		if (!messages.isEmpty()){
			processMessage(messages.getMessage());
		}
		
		platform.move();
		ball.move();
		for (Obstacle o : obstacles) {
			o.move();
		}
		if (quit) container.exit();
	}
	
	private void processMessage(String message){
		System.out.println("Process: " + message);
		if (message.matches(".*q") || message == null){
			quit = true;
			return;
		}
		if (message.equals("lu")){
			platform.leftUp();
			return;
		}
		if (message.equals("ld")){
			platform.leftDown();
			return;
		}
		if (message.equals("ls")){
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
		if (message.equals("rs")){
			platform.rightStop();
			return;
		}
	}
}
