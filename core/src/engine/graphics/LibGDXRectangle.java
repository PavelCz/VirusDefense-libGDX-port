package engine.graphics;

import towerDefense.TowerDefense;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 * @author Pavel A Rectangle based on my other Project JBreakout. This Rectangle is based on LWJGL. I tworked once, but now it doesn't
 *         seem to work anymore
 */
public class LibGDXRectangle extends RenderObject {
	protected Color color;
	protected float width;
	protected float height;

	public LibGDXRectangle(float width, float height) {
		this(width, height, Color.PINK);
	}

	public LibGDXRectangle(float width, float height, float r, float g, float b) {
		this(width, height, new Color(r, g, b, 1));

	}

	public LibGDXRectangle(float width, float height, Color color) {
		this.width = width;
		this.height = height;
		this.color = color;
	}

	@Override
	public void draw(float x, float y, float globalScale, SpriteBatch batch) {
		batch.end();

		// batch.setProjectionMatrix(camera.combined);
		float scaling = globalScale;
		y = TowerDefense.getHeight() - y; // sets coordinate System from up - right to down - right
		y = y - this.height * scaling; // sets picture anchor to top left corner instead of bottom left
		// this.graphics.fill(new Rectangle(x, y, this.width * globalScale, this.height * globalScale), new GradientFill(0, 0,
		// this.color, this.width * globalScale, this.height * globalScale, this.color));
		// OrthographicCamera camera = new OrthographicCamera();
		// camera.setToOrtho(false, TowerDefense.getWidth(), TowerDefense.getHeight());
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		// shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(this.color.r, this.color.g, this.color.b, 1); // r g b a
		// shapeRenderer.line(x, y, x2, y2);
		shapeRenderer.rect(x, y, this.width * globalScale, this.height * globalScale);
		// shapeRenderer.circle(x, y, radius);
		shapeRenderer.end();
		batch.begin();
	}

	public void setWidth(float width) {
		this.width = (int) width;
	}

}
