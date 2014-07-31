package desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import towerDefense.StartGame;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		LwjglApplicationConfiguration.disableAudio = true;
		config.width = 1024;
		config.height = 768;
		new LwjglApplication(new StartGame(), config);
		// new StartGame().create();
	}
}
