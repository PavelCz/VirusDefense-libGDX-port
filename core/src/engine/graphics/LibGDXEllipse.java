package engine.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 * @author Pavel A Rectangle based on my other Project JBreakout. This Rectangle is based on LWJGL.
 */
public class LibGDXEllipse extends LibGDXRectangle {

	public LibGDXEllipse(float width, float height, Color color, ShapeType shapeType) {
		super(width, height, color, shapeType);
	}

	@Override
	public void draw(float x, float y, SpriteBatch batch) {
		batch.end();
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		shapeRenderer.begin(this.shapeType);
		shapeRenderer.setColor(this.color);
		shapeRenderer.circle(x, y, this.width / 2);
		shapeRenderer.end();
		batch.begin();
	}

}
