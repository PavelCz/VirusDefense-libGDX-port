package engine.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import engine.graphics.TwoColoredBar;

/**
 * @author Pavel see LWJGLRectangle
 */
public class Healthbar extends GUI {
	// protected MyVector2f coordinates;
	private int maxHealth;
	private float healthLeft;
	private int length;
	private int height;
	private TwoColoredBar bar;

	public Healthbar(float x, float y, int maxHealth, int length, int height) {
		super(x, y);
		this.maxHealth = maxHealth;
		this.healthLeft = maxHealth;
		this.length = length;
		this.height = height;

		this.bar = new TwoColoredBar(this.length, this.height);

	}

	public void changeHealth(float amount) {
		this.healthLeft += amount;
		this.bar.setFractionLeft(this.healthLeft / this.maxHealth);
	}

	public void setHealth(float amount) {
		this.healthLeft = amount;
		this.bar.setFractionLeft(this.healthLeft / this.maxHealth);
	}

	
	public void draw(SpriteBatch batch) {
		this.bar.setFractionLeft(this.healthLeft / this.maxHealth);
		super.draw(this.bar, batch);

	}

	public void setBordered(boolean bordered) {
		this.bar.setBordered(bordered);
	}

	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}

	public void setX(float f) {
		this.x = f;

	}

	public void setY(float f) {
		this.y = f;

	}

	public void setMaxHealth(int f) {
		this.maxHealth = f;
	}

}
