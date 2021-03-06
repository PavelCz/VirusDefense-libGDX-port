package engine.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OwnSprite extends RenderObject {
	Texture img;
	private Sprite sprite;
	private String imagePath;
	private float defaultScale;

	public OwnSprite(String imagePath) {
		this.imagePath = imagePath;
		this.img = new Texture("data/graphics/" + imagePath);

		this.sprite = new Sprite(this.img);
		this.defaultScale = 1f;

	}

	public OwnSprite(String imagePath, float scale) {

		this(imagePath);
		this.defaultScale = scale;

	}

	public void draw(float xCoordinate, float yCoordinate, float scale,  SpriteBatch batch) {
		float scaling = this.defaultScale * scale;
		this.sprite.setSize(this.img.getWidth() * scaling, this.img.getHeight() * scaling);
		this.sprite.setPosition(xCoordinate, yCoordinate);
		this.sprite.draw(batch);
	}

	@Override
	public void draw(float xCoordinate, float yCoordinate, SpriteBatch batch) {
		float scaling = this.defaultScale;
		this.sprite.setSize(this.img.getWidth() * scaling, this.img.getHeight() * scaling);
		this.sprite.setPosition(xCoordinate, yCoordinate);
		this.sprite.draw(batch);
	}

	public float getWidth() {
		return this.img.getWidth() * this.defaultScale;
	}

	public float getHeight() {
		return this.img.getHeight() * this.defaultScale;
	}

	public void setAlpha(float alpha) {
		this.sprite.setAlpha(alpha);

	}

	public void setColor(float r, float g, float b) {
		this.sprite.setColor(r, g, b, 1f);
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

	public String getImagePath() {
		return imagePath;
	}

	public Sprite getGDXSprite() {
		return this.sprite;
	}


}
