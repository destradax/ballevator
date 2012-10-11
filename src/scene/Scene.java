package scene;


public class Scene {
	private Platform platform;
	private Ball ball;

	public Scene(){
		loadScene();
	}
	
	public void loadScene(){
		float x1, y1, x2, y2, speedl, speedr;
		x1 = 10f; x2 = 90f;
		y1 = y2 = 90f;
		speedl = 1.1f;
		speedr = 0.5f;
		
		platform = new Platform(x1, y1, x2, y2, speedl, speedr);
		ball = new Ball(platform);
	}
	
	public Ball getBall() {
		return ball;
	}
	public Platform getPlatform() {
		return platform;
	}
}
