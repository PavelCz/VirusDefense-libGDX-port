package engine.graphics;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Ellipse;

/**
 * @author Pavel A Rectangle based on my other Project JBreakout. This Rectangle is based on LWJGL. I tworked once, but now it doesn't
 *         seem to work anymore
 */
public class SlickEllipse extends SlickRectangle {

	public SlickEllipse(Graphics graphics, float width, float height, Color color) {
		super(graphics, width, height, color);
	}

	public SlickEllipse(Graphics graphics, float width, float height) {
		super(graphics, width, height);
	}

	public SlickEllipse(Graphics graphics, float width, float height, float r, float g, float b) {
		super(graphics, width, height, r, g, b);
	}

	@Override
	public void draw(float x, float y, float globalScale) {
		this.graphics.fill(new Ellipse(x, y, this.width / 2 * globalScale, this.height / 2 * globalScale), new GradientFill(0, 0,
				this.color, this.width / 2 * globalScale, this.height / 2 * globalScale, this.color));
	}

}
