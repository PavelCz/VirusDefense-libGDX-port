package engine.graphics;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import towerDefense.Gameplay;

public class Text extends RenderObject {
	private Font fontSettings;
	private TrueTypeFont font;
	private String text;
	private Color color;
	private int height;
	private boolean visible = true;

	public Text(int height, String text, Color color, float globalScale) {
		this.setHeight(height);
		this.text = text;
		this.color = color;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void draw(float x, float y, float globalScale) {
		if (this.visible) {
			// this.font.drawString(x, y, this.text, this.color);
			for (String line : this.text.split("\n")) {
				this.font.drawString(x, y, line, this.color);
				y += this.font.getHeight();
			}
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
		return this.font.getWidth(lines[line]);
	}

	public int getTextHeight() {
		return this.font.getHeight();
	}

	public int getActualHeight() {
		return this.font.getHeight() * this.text.split("\n").length;
	}

	public void setHeight(int height) {
		this.height = height;
		this.fontSettings = new Font("Verdana", Font.PLAIN, (int) (this.height * Gameplay.GLOBAL_GUI_SCALE));
		this.font = new TrueTypeFont(this.fontSettings, true);
	}

	public void setVisible(boolean visible) {
		this.visible = visible;

	}
}
