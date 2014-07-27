package core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlaneGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	@Override
	public void create() {
		this.batch = new SpriteBatch();
		this.img = new Texture("badlogic.jpg");
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.batch.begin();
		this.batch.draw(this.img, 20, 20);
		this.batch.end();
	}
}
