package engine.graphics;

import towerDefense.Gameplay;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import engine.Drawable;

public class Background implements Drawable {
	protected OwnSprite picture;
	protected Gameplay game;

	public Background(float scale, String backgroundPath, Gameplay game) {
		this.picture = new OwnSprite(backgroundPath, scale);
		this.game = game;
	}

	@Override
	public void draw(SpriteBatch batch) {
		this.picture.draw(0, 0, 1f, batch);

	}

}
