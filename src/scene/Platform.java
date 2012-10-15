package scene;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Line;

public class Platform extends Line{
	
	private static final long serialVersionUID = 1L;
	private float speedl;
	private float speedr;
	private float leftSpeed;
	private float rightSpeed;
	private int windowWidth;
	private int windowHeight;

	public Platform(int width, int height, float speedl, float speedr){
		super(width/10.0f, height*0.9f, width*0.9f, height*0.9f);
		this.windowHeight = height;
		this.windowWidth = width;
		this.speedl = speedl;
		this.speedr = speedr;
	}
	
	
	public void move(){
		//TODO respect window boundaries when moving platform
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
