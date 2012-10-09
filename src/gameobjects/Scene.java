package gameobjects;


public class Scene {
	Platform platform;
	
	public Scene(){
		loadScene();
	}
	
	public Platform getPlatform() {
		return platform;
	}

	public void loadScene(){
		float x1, y1, x2, y2, speedl, speedr;
		x1 = 10f; x2 = 90f;
		y1 = y2 = 90f;
		speedl = 0.1f;
		speedr = 0.5f;
		
		platform = new Platform(x1, y1, x2, y2, speedl, speedr);
	}
}
