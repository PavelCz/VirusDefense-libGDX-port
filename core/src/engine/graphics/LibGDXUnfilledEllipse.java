package engine.graphics;

import towerDefense.TowerDefense;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class LibGDXUnfilledEllipse extends LibGDXEllipse {

	public LibGDXUnfilledEllipse(float width, float height, Color color) {
		super(width, height, color);
		this.shapeType = ShapeType.Line;
	}

	public LibGDXUnfilledEllipse(float width, float height, float r, float g, float b) {
		super(width, height, r, g, b);
		this.shapeType = ShapeType.Line;
	}

	public LibGDXUnfilledEllipse(float width, float height) {
		super(width, height);
		this.shapeType = ShapeType.Line;
	}
}
