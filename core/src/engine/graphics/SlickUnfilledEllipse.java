package engine.graphics;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Ellipse;

public class SlickUnfilledEllipse extends SlickEllipse {

	public SlickUnfilledEllipse(Graphics graphics, float width, float height, Color color) {
		super(graphics, width, height, color);
	}

	public SlickUnfilledEllipse(Graphics graphics, float width, float height, float r, float g, float b) {
		super(graphics, width, height, r, g, b);
	}

	public SlickUnfilledEllipse(Graphics graphics, float width, float height) {
		super(graphics, width, height);
	}

	@Override
	public void draw(float x, float y, float globalScale) {
		this.graphics.draw(new Ellipse(x, y, this.width / 2 * globalScale, this.height / 2 * globalScale), new GradientFill(0, 0,
				this.color, this.width / 2 * globalScale, this.height / 2 * globalScale, this.color));
	}
}
