package engine.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SlickTwoColoredBar extends RenderObject {
	private LibGDXRectangle base;
	private LibGDXRectangle health;
	LibGDXUnfilledRectangle border;
	private float length;
	private float height;
	private float length2;

	private boolean bordered = false;

	public SlickTwoColoredBar(float length, float height) {
		this.length = length;
		this.height = height;
		this.border = new LibGDXUnfilledRectangle(this.length, this.height, Color.BLACK);
		this.base = new LibGDXRectangle((int) this.length, (int) this.height, Color.RED);
		this.health = new LibGDXRectangle((int) this.length, (int) this.height, Color.GREEN);

		this.length2 = length;
	}

	@Override
	public void draw(float x, float y, float globalScale, SpriteBatch batch) {
		this.base.draw(x, y, globalScale, batch);
		this.health.setWidth(this.length2);
		this.health.draw(x, y, globalScale, batch);
		if (this.bordered) {
			this.border.draw(x, y, globalScale, batch);

		}

	}

	public void setBordered(boolean bordered) {
		this.bordered = bordered;
	}

	public void setFractionLeft(float fraction) {
		this.length2 = this.length * fraction;
	}

}
