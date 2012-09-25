import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.geom.Line;

public class Platform extends Line implements KeyListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float speed;
	private float leftSpeed;
	private float rightSpeed;

	public Platform(float x1, float y1, float x2, float y2) {
		super(x1, y1, x2, y2);
		speed = 2.0f;
		
	}
	
	public Platform(float x1, float y1, float x2, float y2, float speed) {
		super(x1, y1, x2, y2);
		this.speed = speed;
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

	@Override
	public void inputEnded() {
	}

	@Override
	public void inputStarted() {
	}

	@Override
	public boolean isAcceptingInput() {
		return true;
	}

	@Override
	public void setInput(Input arg0) {
	}

	@Override
	public void keyPressed(int key, char c) {
		switch (key){
		case Input.KEY_W:
			leftSpeed = (-1) * speed;
			break;
		case Input.KEY_S:
			leftSpeed = speed;
			break;
		case Input.KEY_UP:
			rightSpeed = (-1) * speed;
			break;
		case Input.KEY_DOWN:
			rightSpeed = speed;
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		switch (key){
		case Input.KEY_W:
		case Input.KEY_S:
			leftSpeed = 0f;
			break;
		case Input.KEY_UP:
		case Input.KEY_DOWN:
			rightSpeed = 0f;
			break;
		}
	}
	
	
	
}
