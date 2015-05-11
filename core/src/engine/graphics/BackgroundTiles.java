package engine.graphics;

import towerDefense.Gameplay;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BackgroundTiles extends Background {
	private int horizontalTiles, verticalTiles;
	private OwnSprite pictureOutOfBounds;

	public BackgroundTiles(float scale, String backgroundPath, int horizontalTiles, int verticalTiles, Gameplay game) {
		super(scale, backgroundPath, game);
		this.horizontalTiles = horizontalTiles;
		this.verticalTiles = verticalTiles;
		this.pictureOutOfBounds = new OwnSprite(backgroundPath);
		// this.pictureOutOfBounds.setAlpha(0.8f);

		this.pictureOutOfBounds.setColor(0.7f, 0.7f, 0.7f);

	}

	@Override
	public void draw(SpriteBatch batch) {
		// if (this.game != null) {
		for (int i = 0; i < this.horizontalTiles + 1; ++i) {
			for (int j = 0; j < this.verticalTiles + 1; ++j) {
				if (i < this.horizontalTiles && j < this.verticalTiles) {
					this.picture.draw(i * Gameplay.SIZE, j * Gameplay.SIZE, Gameplay.CURRENT_GAME_SCALE, batch);
				} else {
					this.pictureOutOfBounds.draw(i * Gameplay.SIZE, j * Gameplay.SIZE, Gameplay.CURRENT_GAME_SCALE, batch);
				}
			}
			// }
		}
	}
}
