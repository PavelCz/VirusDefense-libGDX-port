package engine.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class TwoColoredBar extends RenderObject {
	private LibGDXRectangle base;
	private LibGDXRectangle health;
	private LibGDXRectangle border;
	private float length;
	private float height;
	private float length2;

	private boolean bordered = false;

	public TwoColoredBar(float length, float height) {
		this.length = length;
		this.height = height;
		this.border = new LibGDXRectangle(this.length, this.height, Color.BLACK, ShapeRenderer.ShapeType.Line);
		this.base = new LibGDXRectangle((int) this.length, (int) this.height, Color.RED, ShapeRenderer.ShapeType.Filled);
		this.health = new LibGDXRectangle((int) this.length, (int) this.height, Color.GREEN, ShapeRenderer.ShapeType.Filled);

		this.length2 = length;
	}

	@Override
	public void draw(float x, float y,  SpriteBatch batch) {
		this.base.draw(x, y,  batch);
		this.health.setWidth(this.length2);
		this.health.draw(x, y,  batch);
		if (this.bordered) {
			this.border.draw(x, y,  batch);

		}

	}

	public void setBordered(boolean bordered) {
		this.bordered = bordered;
	}

	public void setFractionLeft(float fraction) {
		this.length2 = this.length * fraction;
	}

}
