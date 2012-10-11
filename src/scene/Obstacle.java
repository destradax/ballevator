package scene;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;


public class Obstacle extends Rectangle{

	private float speedX;
	private float speedY;
	private int windowWidth;
	private int windowHeight;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Obstacle(float x, float y, float width, float height,
			float speedX, float speedY, int windowWidth, int windowHeight) {
		super(x, y, width, height);
		this.speedX = speedX;
		this.speedY = speedY;
		this.windowHeight = windowHeight;
		this.windowWidth = windowWidth;
	}
	
	public void move(){
		x = (x + speedX) % windowWidth;
		if (x < 0) x+= windowWidth;
		y = (y + speedY) % windowHeight;
		if (y < 0) y += windowHeight;
		createPoints();
	}
	
	public void render(Graphics g){
		g.draw(this);
	}
}
