package engine.gui;

import towerDefense.Gameplay;
import engine.Drawable;
import engine.graphics.Sprite;

public class InterfaceBackground extends GUI implements Drawable {
	private Sprite picture;

	public InterfaceBackground(String backgroundPath) {
		super(Gameplay.INTERFACE_START_X, 0);
		this.picture = new Sprite(backgroundPath, 1f);
	}

	@Override
	public void draw() {
		this.picture.draw(this.x, this.y, Gameplay.GLOBAL_GUI_SCALE);

	}

}
