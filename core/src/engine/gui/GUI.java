package engine.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import engine.graphics.RenderObject;

public abstract class GUI {
	protected float x, y;

	public GUI(float x, float y) {
		this.x = x;
		this.y = y;
	}

	protected void draw(RenderObject renderObject, SpriteBatch batch) {
		renderObject.draw(this.x, this.y,  batch);

	}

	public abstract void draw(SpriteBatch batch);
}
