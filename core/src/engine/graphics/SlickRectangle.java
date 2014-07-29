package engine.graphics;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 * @author Pavel A Rectangle based on my other Project JBreakout. This Rectangle is based on LWJGL. I tworked once, but now it doesn't
 *         seem to work anymore
 */
public class SlickRectangle extends RenderObject {
	protected Color color;
	protected Graphics graphics;
	protected float width;
	protected float height;

	public SlickRectangle(Graphics graphics, float width, float height) {
		this(graphics, width, height, Color.pink);
	}

	public SlickRectangle(Graphics graphics, float width, float height, float r, float g, float b) {
		this(graphics, width, height, new Color(r, g, b));

	}

	public SlickRectangle(Graphics graphics, float width, float height, Color color) {
		this.width = width;
		this.height = height;
		this.color = color;
		this.graphics = graphics;
	}

	@Override
	public void draw(float x, float y, float globalScale) {
		// this.graphics.fill(new Rectangle(x, y, this.width * globalScale, this.height * globalScale), new GradientFill(0, 0,
		// this.color, this.width * globalScale, this.height * globalScale, this.color));
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		// shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(1, 0, 0, 1); // r g b a
		// shapeRenderer.line(x, y, x2, y2);
		shapeRenderer.rect(x, y, this.width * globalScale, this.height * globalScale);
		// shapeRenderer.circle(x, y, radius);
		shapeRenderer.end();
	}

	public void setWidth(float width) {
		this.width = (int) width;
	}

}
