package engine.graphics;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import towerDefense.TowerDefense;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class LibGDXUnfilledEllipse extends LibGDXEllipse {

	public LibGDXUnfilledEllipse(Graphics graphics, float width, float height, Color color) {
		super(graphics, width, height, color);
	}

	public LibGDXUnfilledEllipse(Graphics graphics, float width, float height, float r, float g, float b) {
		super(graphics, width, height, r, g, b);
	}

	public LibGDXUnfilledEllipse(Graphics graphics, float width, float height) {
		super(graphics, width, height);
	}

	@Override
	public void draw(float x, float y, float globalScale) {
		// this.graphics.draw(new Ellipse(x, y, this.width / 2 * globalScale, this.height / 2 * globalScale), new GradientFill(0, 0,
		// this.color, this.width / 2 * globalScale, this.height / 2 * globalScale, this.color));
		OrthographicCamera camera = new OrthographicCamera();
		camera.setToOrtho(false, TowerDefense.getWidth(), TowerDefense.getHeight());
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), 1); // r g b a
		// shapeRenderer.line(x, y, x2, y2);
		shapeRenderer.circle(x, TowerDefense.getHeight() - y, this.width / 2 * globalScale);
		// shapeRenderer.circle(x, y, radius);
		shapeRenderer.end();
	}
}
