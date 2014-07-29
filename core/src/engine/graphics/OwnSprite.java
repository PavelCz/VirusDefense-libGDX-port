package engine.graphics;

import towerDefense.TowerDefense;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OwnSprite extends RenderObject {
	SpriteBatch batch;
	Texture img;
	private Sprite sprite;
	private String imagePath;
	private float defaultScale;

	public OwnSprite(String imagePath) {
		this.imagePath = imagePath;
		this.batch = new SpriteBatch();
		this.img = new Texture("data/graphics/" + imagePath);

		this.sprite = new Sprite(this.img);
		this.defaultScale = 1f;

	}

	public OwnSprite(String imagePath, float scale) {

		this(imagePath);

		this.defaultScale = scale;

	}

	@Override
	public void draw(float xCoordinate, float yCoordinate, float globalScale) {
		OrthographicCamera camera = new OrthographicCamera();
		camera.setToOrtho(false, TowerDefense.getWidth(), TowerDefense.getHeight());

		this.batch.setProjectionMatrix(camera.combined);
		this.batch.begin();
		this.sprite.setScale(this.defaultScale * globalScale);
		this.sprite.setPosition(xCoordinate, TowerDefense.getHeight() - yCoordinate);
		this.sprite.draw(this.batch);
		this.batch.end();
	}

	public float getWidth() {
		return this.img.getWidth() * this.defaultScale;
	}

	public float getHeight() {
		return this.img.getHeight() * this.defaultScale;
	}

	public void setAlpha(float alpha) {
		// this.img.setAlpha(alpha);

	}

	public void setColor(float r, float g, float b) {
		// this.img.setImageColor(r, g, b);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone() returns a completely new Sprite with no references to the old one or attribute to the old one
	 */
	@Override
	public OwnSprite clone() {
		return new OwnSprite(this.imagePath, this.defaultScale);
	}

}
