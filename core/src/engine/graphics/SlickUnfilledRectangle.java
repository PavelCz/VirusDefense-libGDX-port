package engine.graphics;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 * @author Pavel A Rectangle based on my other Project JBreakout. This Rectangle is based on LWJGL. I tworked once, but now it doesn't
 *         seem to work anymore
 */
public class SlickUnfilledRectangle extends SlickRectangle {

	public SlickUnfilledRectangle(Graphics graphics, float width, float height, Color color) {
		super(graphics, width, height, color);
	}

	public SlickUnfilledRectangle(Graphics graphics, float width, float height) {
		super(graphics, width, height);
	}

	public SlickUnfilledRectangle(Graphics graphics, float width, float height, float r, float g, float b) {
		super(graphics, width, height, r, g, b);
	}

	@Override
	public void draw(float x, float y, float globalScale) {
		// this.graphics.draw(new Rectangle(x, y, this.width * globalScale, this.height * globalScale), new GradientFill(0, 0,
		// this.color, this.width * globalScale, this.height * globalScale, this.color));
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		// shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(1, 0, 0, 1); // r g b a
		// shapeRenderer.line(x, y, x2, y2);
		shapeRenderer.rect(x, y, this.width * globalScale, this.height * globalScale);
		// shapeRenderer.circle(x, y, radius);
		shapeRenderer.end();
	}

}
