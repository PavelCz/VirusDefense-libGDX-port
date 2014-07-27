package engine.gui;

import org.newdawn.slick.Color;

import engine.GameComponent;
import engine.graphics.Text;

public class ClickableText extends Clickable {
	private Text text;
	private Color savedColor;

	public ClickableText(float x, float y, String text, float globalScale, GameComponent game, boolean stayClicked) {
		super(x, y, game, stayClicked);
		this.text = new Text(15, text, Color.white, globalScale);
		this.savedColor = Color.white;
		this.collisionWidth = this.text.getWidth();
		this.collisionHeight = this.text.getTextHeight();
	}

	public void activate() {
		this.active = true;
	}

	public void deactivate() {
		this.active = false;
	}

	@Override
	public void onClick() {
		if (this.active) {
			super.onClick();
			this.text.setColor(Color.blue);
		}

	}

	@Override
	public void onRelease() {
		super.onRelease();
		this.text.setColor(Color.white);

	}

	@Override
	public void draw() {
		this.text.draw(this.x, this.y, 1f);

	}

	@Override
	public void onHover() {
		this.text.setColor(Color.gray);

	}

	@Override
	public void onUnHover() {
		this.text.setColor(this.savedColor);

	}

	public int getWidth(int line) {
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

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setVisible(boolean visible) {
		this.text.setVisible(visible);
	}

	public void setColor(Color color) {
		this.text.setColor(color);
		this.savedColor = color;

	}

}
