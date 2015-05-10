package engine.gui;

import towerDefense.Gameplay;
import towerDefense.TowerDefense;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import engine.graphics.RenderObject;

public abstract class GUI {
	protected float x, y;

	public GUI(float x, float y) {
		this.x = x;
		this.y = y;
	}

	protected void draw(RenderObject renderObject, SpriteBatch batch) {
		float y = this.y;

		y = TowerDefense.getHeight() - y; // sets coordinate System from up - right to down - right

		// y = this.y - this.height * scaling; // sets picture anchor to top left corner instead of bottom left

		y = this.y + Gameplay.DEFAULT_SIZE * Gameplay.CURRENT_GAME_SCALE;
		renderObject.draw(this.x, this.y, Gameplay.GLOBAL_GUI_SCALE, batch);

	}

	public abstract void draw(SpriteBatch batch);
}
