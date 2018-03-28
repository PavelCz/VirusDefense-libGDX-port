package engine.graphics;

import towerDefense.TowerDefense;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 * @author Pavel A Rectangle based on my other Project JBreakout. This Rectangle is based on LWJGL. I tworked once, but now it doesn't
 *         seem to work anymore
 */
public class LibGDXEllipse extends LibGDXRectangle {
	// Set this to ShapeType.Line for unfilled ellipses and to ShapeType.Filled for filled ones
	protected ShapeType shapeType;

	public LibGDXEllipse(float width, float height, Color color, ShapeType shapeType) {
		super(width, height, color);
		this.shapeType = shapeType;
	}

	@Override
	public void draw(float x, float y, float globalScale, SpriteBatch batch) {
		batch.end();
		//OrthographicCamera camera = new OrthographicCamera();
		//camera.setToOrtho(false, TowerDefense.getWidth(), TowerDefense.getHeight());
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		//shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		shapeRenderer.begin(this.shapeType);
		shapeRenderer.setColor(this.color.r, this.color.g, this.color.b, 1); // r g b a
		shapeRenderer.circle(x, y, this.width / 2 * globalScale);
		shapeRenderer.end();
		batch.begin();
	}

}
