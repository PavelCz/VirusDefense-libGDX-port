package engine.graphics;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class SlickTwoColoredBar extends RenderObject {
	private LibGDXRectangle base;
	private LibGDXRectangle health;
	private float length;
	private float height;
	private float length2;
	private Graphics graphics;

	private boolean bordered = false;

	public SlickTwoColoredBar(Graphics graphics, float length, float height) {
		this.length = length;
		this.height = height;
		this.graphics = graphics;

		this.base = new LibGDXRectangle(graphics, (int) this.length, (int) this.height, Color.red);
		this.health = new LibGDXRectangle(graphics, (int) this.length, (int) this.height, Color.green);

		this.length2 = length;
	}

	@Override
	public void draw(float x, float y, float globalScale) {
		this.base.draw(x, y, globalScale);
		this.health.setWidth(this.length2);
		this.health.draw(x, y, globalScale);
		if (this.bordered) {
			new LibGDXUnfilledRectangle(this.graphics, this.length, this.height, Color.black).draw(x, y, globalScale);
			;
		}

	}

	public void setBordered(boolean bordered) {
		this.bordered = bordered;
	}

	public void setFractionLeft(float fraction) {
		this.length2 = this.length * fraction;
	}
}
