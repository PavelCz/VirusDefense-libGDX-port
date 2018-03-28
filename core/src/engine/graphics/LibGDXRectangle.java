package engine.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 * @author Pavel A Rectangle based on my other Project JBreakout. This Rectangle is based on LWJGL.
 */
public class LibGDXRectangle extends RenderObject {
	protected Color color;
	protected float width;
	protected float height;
	// Set this to ShapeType.Line for unfilled primitives and to ShapeType.Filled for filled ones
	protected ShapeType shapeType;

	public LibGDXRectangle(float width, float height, Color color, ShapeType shapeType) {
		this.width = width;
		this.height = height;
		this.color = color;
		this.shapeType = shapeType;
	}

	@Override
	public void draw(float x, float y,  SpriteBatch batch) {
		batch.end(); // pause batch drawing and start shape drawing
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		shapeRenderer.begin(this.shapeType);
		shapeRenderer.setColor(this.color);
		shapeRenderer.rect(x, y, this.width, this.height);
		shapeRenderer.end();
		batch.begin(); // restart batch drawing
	}

	public void setWidth(float width) {
		this.width = (int) width;
	}

}
