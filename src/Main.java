import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.AppGameContainer;

public class Main extends BasicGame{
	
	public static final int WIDTH=400;
	public static final int HEIGHT=700;
	public static final int FPS=60;
	public static final boolean FULLSCREEN=false;
	
	private final float SPEED_SCALE=2.0f;
	private final float BALL_RADIUS=20f;
	private final int GAME_OVER = -1;
	private final int IN_GAME = 0;
	private final int DONE = 1;
	private int score;
	private int state;
	private Platform platform;
	private Ball ball;
	private List<Obstacle> obstacles;
	private Input input;
	
	public Main(){
		super("Ballevator");
	}
	
	public void addObstacle(){
		Random r = new Random();
		int directionX = r.nextInt(2) == 1?1:-1;
		int directionY = r.nextInt(2) == 1?1:-1;
		obstacles.add(new Obstacle(
				0, 
				r.nextInt(HEIGHT), 
				30, 
				30, 
				(r.nextFloat() * SPEED_SCALE * directionX), 
				(r.nextFloat() * SPEED_SCALE * directionY), 
				WIDTH, 
				HEIGHT));
	}
	
	public void reset(){
		input.removeAllListeners();
		obstacles = new ArrayList<Obstacle>();
		platform = new Platform(100, 600, 300, 600);
		ball = new Ball(WIDTH/2, platform.getY()-BALL_RADIUS, BALL_RADIUS);
		score = 10000;
		state = IN_GAME;
		for (int i = 0; i < 10; i++) {
			addObstacle();
		}
		input.addKeyListener(platform);
	}
	
	@Override
    public void init(GameContainer container) throws SlickException {
		obstacles = new ArrayList<Obstacle>();
		platform = new Platform(100, 600, 300, 600);
		ball = new Ball(WIDTH/2, platform.getY()-BALL_RADIUS, BALL_RADIUS);
		score = 10000;
		state = IN_GAME;
		for (int i = 0; i < 10; i++) {
			addObstacle();
		}
		
		input = container.getInput();
		input.addKeyListener(platform);
		
	}

    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {
    	if (state == IN_GAME){
	    	platform.move();
	    	ball.move(platform);
	    	if (ball.getCenterY() <= ball.radius) state = DONE;
	    	if (ball.getState() == Ball.DEAD) state = GAME_OVER;
	    	for (Obstacle o : obstacles) {
				o.move();
				if (o.intersects(ball)){
					state = GAME_OVER;
				}
			}
	        score -= 1;
    	}
    	if (input.isKeyPressed(Input.KEY_F2)){
    		reset();
    	}else if (input.isKeyPressed(Input.KEY_SPACE)){
    		addObstacle();
    	}
    }

    @Override
    public void render(GameContainer container, Graphics g)
            throws SlickException {
		platform.render(g);
		ball.render(g);
		for (Obstacle o : obstacles) {
			o.render(g);
		}
    	if (state == GAME_OVER){
    		g.drawString("GAME OVER", (WIDTH/2)-40, HEIGHT/2);
    	}else if (state == DONE){
    		g.drawString("CONGRATULATIONS", (WIDTH/2)-50, HEIGHT/2);
    	}
    	
    	g.drawString(("Obstacles: "+obstacles.size()), 120, 10);
		g.drawString("Score:" + score, 280, 10);
		g.drawString("W/S or Up/Down: move left/right side.", 10, HEIGHT-40);
		g.drawString("Space: add obstacle.     F2: Restart.", 10, HEIGHT-20);
    }
	
    public static void main(String[] args) {
    	try {
            AppGameContainer app = new AppGameContainer(new Main());
            app.setDisplayMode(Main.WIDTH, Main.HEIGHT, Main.FULLSCREEN);
            app.setTargetFrameRate(Main.FPS);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
