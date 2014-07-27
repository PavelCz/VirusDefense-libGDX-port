package desktop;

import towerDefense.StartGame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		LwjglApplicationConfiguration.disableAudio = true;
		config.width = 600;
		config.height = 480;
		new LwjglApplication(new StartGame(), config);
	}
}
