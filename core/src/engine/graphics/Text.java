package engine.graphics;

import org.newdawn.slick.Color;

import towerDefense.TowerDefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Text extends RenderObject {
	private String text;
	private BitmapFont bmfont;
	private Color color;
	private float height;
	private boolean visible = true;

	public Text(int height, String text, Color color, float globalScale) {
		this.bmfont = new BitmapFont(Gdx.files.internal("arial.fnt"));
		this.height = this.bmfont.getCapHeight();
		this.setHeight(height);
		this.text = text;
		this.color = color;

	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void draw(float x, float y, float globalScale) {
		this.bmfont.setColor(this.color.getRedByte(), this.color.getGreenByte(), this.color.getBlueByte(), 1);
		if (this.visible) {
			SpriteBatch batch = new SpriteBatch();
			batch.begin();
			// this.bmfont.draw(batch, this.text, x, y);

			for (String line : this.text.split("\n")) {
				this.bmfont.draw(batch, line, x, TowerDefense.getHeight() - y);
				y += this.bmfont.getLineHeight();
			}
			batch.end();
		}

	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getWidth() {
		String[] lines = this.text.split("\n");
		int maxWidth = 0;
		for (int i = 0; i < lines.length; ++i) {
			int currentWidth = this.getWidth(i);
			if (maxWidth < currentWidth) {
				maxWidth = currentWidth;
			}
		}
		return maxWidth;

	}

	public int getWidth(int line) {
		String[] lines = this.text.split("\n");
		// return this.font.getWidth(lines[line]);
		return this.text.toCharArray().length * 5;
	}

	public int getTextHeight() {
		return (int) this.bmfont.getLineHeight();
	}

	public int getActualHeight() {
		return this.getTextHeight() * this.text.split("\n").length;
	}

	public void setHeight(int height) {
		this.bmfont.setScale(height / this.height);
		this.height = height;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;

	}
}
