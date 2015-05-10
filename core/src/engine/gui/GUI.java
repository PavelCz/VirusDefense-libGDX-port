package engine.gui;

import towerDefense.Gameplay;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import engine.graphics.RenderObject;

public abstract class GUI {
	protected float x, y;

	public GUI(float x, float y) {
		this.x = x;
		this.y = y;
	}

	protected void draw(RenderObject renderObject, SpriteBatch batch) {
		renderObject.draw(this.x, this.y, Gameplay.GLOBAL_GUI_SCALE, batch);

	}

	public abstract void draw(SpriteBatch batch);
}
