package towerDefense;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;

public class StartGame extends ApplicationAdapter {
	private TowerDefense game;

	@Override
	public void create() {

		//Preferences prefs = Gdx.app.getPreferences("VirusDefense");
		//String resolution = prefs.getString("resolution");
		//String[] parts = resolution.split("\n");
		//int width = Integer.parseInt(parts[0]);
		//int height = Integer.parseInt(parts[1]);
		//boolean fullscreen;
        /*
		if (Integer.parseInt(parts[2]) == 1) {

			fullscreen = true;
		} else {
			fullscreen = false;
		}*/
		//Gdx.graphics.setDisplayMode(width, height, fullscreen);
		// List<String> settings = TextFileToString.getLines("settings.txt");
		// int width = Integer.parseInt(settings.get(0));
		// int height = Integer.parseInt(settings.get(1));
		// boolean fullscreen;
		// if (Integer.parseInt(settings.get(2)) == 0) {
		// fullscreen = false;
		// } else {
		// fullscreen = true;
		// }

		this.game = new TowerDefense(false);
		this.game.init();
	}

	@Override
	public void render() {
		Gdx.graphics.setTitle("" + Gdx.graphics.getFramesPerSecond());
		Gdx.gl.glClearColor(0.5f, 0.7f, 0.95f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//
		this.game.update((int) (Gdx.graphics.getDeltaTime() * 1000));
		this.game.render();

		// this.updateWorld();
		// this.drawWorld();
	}

}