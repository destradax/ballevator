package gameobjects;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Line;

public class Platform extends Line{
	
	private static final long serialVersionUID = 1L;
	private float speedl;
	private float speedr;
	private float leftSpeed;
	private float rightSpeed;

//	public Platform(float x1, float y1, float x2, float y2) {
//		super(x1, y1, x2, y2);
//		speed = 2.0f;
//		
//	}
	
	public Platform(float x1, float y1, float x2, float y2, float speedl, float speedr) {
		super(x1, y1, x2, y2);
		this.speedl = speedl;
		this.speedr = speedr;
		leftSpeed = 0f;
		rightSpeed = 0f;
	}
	
	
	public void move(){
		set(getX1(), getY1() + leftSpeed, getX2(), getY2() + rightSpeed);
	}
	
	public void render(Graphics g){
		g.setLineWidth(2.0f);
		g.draw(this);
		g.setLineWidth(1.0f);
	}
	
	public void leftUp(){
		leftSpeed = -1 * speedl;
	}
	
	public void leftDown(){
		leftSpeed = speedl;
	}
	
	public void leftStop(){
		leftSpeed = 0f;
	}
	
	public void rightUp(){
		rightSpeed = (-1) * speedr;
	}
	
	public void rightDown(){
		rightSpeed = speedr;
	}
	
	public void rightStop(){
		rightSpeed = 0f;
	}
	
}
