package engine.gui;

import towerDefense.Gameplay;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import engine.graphics.Text;

public class StaticText extends GUI {
	private Text text;

	public StaticText(float x, float y, int height, Color color, String text) {
		super(x, y);

		this.text = new Text(height, text, color, Gameplay.GLOBAL_GUI_SCALE);

	}

	public StaticText(float x, float y, Color color, String text) {
		this(x, y, Gameplay.STANDARD_TEXT_SCALE, color, text);

	}

	public void setVisible(boolean visible) {
		this.text.setVisible(visible);
	}

	public void draw(SpriteBatch batch) {
		super.draw(this.text, batch);

	}

	public float getWidth(int line) {
		return this.text.getWidth(line);
	}

	public int getWidth() {
		return this.text.getWidth();
	}

	public int getTextHeight() {
		return this.text.getTextHeight();
	}

	public int getActualHeight() {
		return this.text.getActualHeight();
	}

	public void setHeight(int height) {
		this.text.setHeight(height);
	}

	public void setText(String text) {
		this.text.setText(text);
	}

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void setColor(Color color) {
		this.text.setColor(color);

	}

}
