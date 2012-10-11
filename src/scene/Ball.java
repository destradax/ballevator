package scene;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;


public class Ball extends Circle{
	
	private static final long serialVersionUID = 1L;
	public static final int IN_GAME = 1;
	public static final int FREE_FALL = 0;
	public static final int DEAD = -1;
	private Platform platform;
	private float speed;
	private float x1, x2, y1, y2;
	private int state;
	
	public Ball(Platform platform){
		super(platform.getCenterX(), platform.getCenterY(), 
				(platform.getX2()-platform.getX1()) / 10.0f);
		this.platform = platform;
		speed = 0f;
		state = IN_GAME;
	}

	public Ball(float centerPointX, float centerPointY, float radius) {
		super(centerPointX, centerPointY, radius);
		speed = 0f;
		state = IN_GAME;
	}
	
	public void move(){
		if (state == IN_GAME){
			x1 = platform.getX1(); y1 = platform.getY1();
			x2 = platform.getX2(); y2 = platform.getY2();
			speed = (y1-y2) / 20;
			
			setCenterX(getCenterX() - speed);
			if (getCenterX() < x1 || getCenterX() > x2){
				state = FREE_FALL;
				speed = Math.abs(speed);
			}
			
			float dy = y2-y1;
			float dx = x2-x1;
			double h = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)); 
			float x3 = getCenterX() - x1;
			float y3 = (float) (x3 * Math.tan(Math.asin(dy/h))) + y1 - radius;
			setCenterY(y3);
		}else if(state == FREE_FALL){
			setCenterY(getCenterY() + speed);
		}
		
	}
	
	public void die(){
		state = DEAD;
	}

	public void render(Graphics g){
		g.draw(this);
	}
	
	public int getState(){
		return state;
	}
}
