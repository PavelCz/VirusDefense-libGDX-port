package desktop;

import towerDefense.StartGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main(String[] arg) {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		Preferences prefs = Gdx.app.getPreferences("VirusDefense");
		String resolution = prefs.getString("resolution");
		String[] parts = resolution.split("\n");
		config.width = Integer.parseInt(parts[0]);
		config.height = Integer.parseInt(parts[1]);
		if (Integer.parseInt(parts[2]) == 1) {

			config.fullscreen = true;
		} else {
			config.fullscreen = false;
		}
		config.resizable = false;
		new LwjglApplication(new StartGame(), config);
	}
}
