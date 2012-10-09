import gameobjects.Platform;
import gameobjects.Scene;


public class GameServer {
	Scene scene;
	Platform platform;
	
	public GameServer(){
		scene = new Scene();
		platform = scene.getPlatform();
	}
	
	public void updateScene(){
		getUpdates();
		platform.move();
	}
	
	private void getUpdates(){
		
	}
}
