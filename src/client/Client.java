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
	
	private static final int GAME = 0;
	private static final int WIN = 1;
	private static final int LOSE = -1;
	
	private MessageQueue messages;
	private boolean quit = false;
	private InputListener inputListener;
	
	private Platform platform;
	private Ball ball;
	private List<Obstacle> obstacles;
	
	private int windowWidth, windowHeight;
	private float platformSpeedL, platformSpeedR;
	
	private int score;
	private int gamestate;
	
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
		gamestate = GAME;
	}
	
	private void resetGame(){
		platform = new Platform(windowWidth, windowHeight, platformSpeedL, platformSpeedR);
		ball = new Ball(platform);
		obstacles = new ArrayList<Obstacle>();
		score = 10000;
		gamestate = GAME;
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		platform.render(g);
		ball.render(g);
		for (Obstacle o : obstacles){
			o.render(g);
		}
		g.drawString("Score: " + (score/10), 10f, 20f);
		if (gamestate == WIN){
			g.drawString("Congratulations!", 10f, 30f);
		}else if (gamestate == LOSE){
			g.drawString("Game Over", 10f, 30f);
		}
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
		if (gamestate == GAME){
			platform.move();
			ball.move();
			score -= 1;
			if (ball.getCenterY() - ball.getRadius() <= 0){
				gamestate = WIN;
				inputListener.sendMessage(inputListener.clientId + "win");
			}else if (ball.getCenterY() + ball.getRadius() >= windowHeight){
				gamestate = LOSE;
				inputListener.sendMessage(inputListener.clientId + "lose");
			}
		}
		if(gamestate == GAME){
			for (Obstacle o : obstacles) {
				o.move();
				if (o.intersects(ball)){
					gamestate = LOSE;
					inputListener.sendMessage(inputListener.clientId + "lose");
					break;
				}
			}
		}
		if (quit) container.exit();
		//TODO besides gamestate, should send something else?
	}
	
	private void processMessage(String message){
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
		if (message.matches(".*reset")){
			resetGame();
			return;
		}
		if (message.matches(".*win")){
			gamestate = WIN;
			return;
		}
		if (message.matches(".*lose")){
			gamestate = LOSE;
			return;
		}
		System.err.println("Can't understand message: " + message);
	}
}
