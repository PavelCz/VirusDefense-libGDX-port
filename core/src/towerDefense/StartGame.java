package towerDefense;

import java.util.List;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import engine.TextFileToString;

public class StartGame extends ApplicationAdapter {
	private TowerDefense game;
	AppGameContainer appGameContainer;

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
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//
		try {
			this.game.update((int) (Gdx.graphics.getDeltaTime() * 1000));
			this.game.render();

		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// this.updateWorld();
		// this.drawWorld();
	}

}