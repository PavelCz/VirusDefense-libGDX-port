package engine.gui;

import towerDefense.Gameplay;
import towerDefense.TowerDefense;

import com.badlogic.gdx.graphics.Color;

public class SetGameModeButton extends ClickableText {
	private TowerDefense towerDefense;
	private int gameMode;

	public SetGameModeButton(float x, float y, String text, TowerDefense towerDefense, int gameMode) {
		super(x, y, text, Gameplay.GLOBAL_GUI_SCALE, towerDefense.getGameplay(), false);
		this.towerDefense = towerDefense;
		this.gameMode = gameMode;
		this.setColor(Color.BLACK);

	}

	@Override
	public void onRelease() {
		if (this.active) {
			super.onRelease();
			this.towerDefense.setMode(this.gameMode);
		}
	}
}