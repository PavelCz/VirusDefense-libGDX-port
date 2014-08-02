package towerDefense;

import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import engine.TextFileToString;

public class StartGame extends ApplicationAdapter {
	private TowerDefense game;

	@Override
	public void create() {
		// slick2d stuff here
		List<String> settings = TextFileToString.getLines("settings.txt");
		int width = Integer.parseInt(settings.get(0));
		int height = Integer.parseInt(settings.get(1));
		boolean fullscreen;
		if (Integer.parseInt(settings.get(2)) == 0) {
			fullscreen = false;
		} else {
			fullscreen = true;
		}
		this.game = new TowerDefense(false);
		this.game.init();

	}

	@Override
	public void render() {
		Gdx.graphics.setTitle("" + Gdx.graphics.getFramesPerSecond());
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//
		this.game.update((int) (Gdx.graphics.getDeltaTime() * 1000));
		this.game.render();

		// this.updateWorld();
		// this.drawWorld();
	}

}