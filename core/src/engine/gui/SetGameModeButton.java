package engine.gui;

import org.newdawn.slick.Color;

import towerDefense.Gameplay;
import towerDefense.TowerDefense;

public class SetGameModeButton extends ClickableText {
	private TowerDefense towerDefense;
	private int gameMode;

	public SetGameModeButton(float x, float y, String text, TowerDefense towerDefense, int gameMode) {
		super(x, y, text, Gameplay.GLOBAL_GUI_SCALE, towerDefense.getGameplay(), false);
		this.towerDefense = towerDefense;
		this.gameMode = gameMode;
		this.setColor(Color.black);

	}

	@Override
	public void onRelease() {
		if (this.active) {
			super.onRelease();
			this.towerDefense.setMode(this.gameMode);
		}
	}
}
