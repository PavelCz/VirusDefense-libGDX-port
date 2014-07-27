package engine.graphics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Sprite extends RenderObject {
	private Image image;
	private String imagePath;
	private float defaultScale;

	public Sprite(String imagePath) {
		this.imagePath = imagePath;
		try {
			this.image = new Image("data/graphics/" + imagePath);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.defaultScale = 1f;

	}

	public Sprite(String imagePath, float scale) {

		this(imagePath);

		this.defaultScale = scale;

	}

	@Override
	public void draw(float xCoordinate, float yCoordinate, float globalScale) {
		this.image.draw(xCoordinate, yCoordinate, globalScale * this.defaultScale);
	}

	public float getWidth() {
		return this.image.getWidth() * this.defaultScale;
	}

	public float getHeight() {
		return this.image.getHeight() * this.defaultScale;
	}

	public void setAlpha(float alpha) {
		this.image.setAlpha(alpha);

	}

	public void setColor(float r, float g, float b) {
		this.image.setImageColor(r, g, b);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone() returns a completely new Sprite with no references to the old one or attribute to the old one
	 */
	@Override
	public Sprite clone() {
		return new Sprite(this.imagePath, this.defaultScale);
	}

}
