package engine.gui;

import org.newdawn.slick.GameContainer;

import towerDefense.TowerDefense;

public class StartClickable extends ClickableText {
	private TowerDefense towerDefense;
	private GameContainer container;

	public StartClickable(float x, float y, TowerDefense towerDefense, GameContainer container) {
		super(x, y, "Start new Game", 1f, towerDefense.getGameplay(), false);
		this.towerDefense = towerDefense;
		this.container = container;
	}

	@Override
	public void onRelease() {
		if (this.active) {
			super.onRelease();
			this.towerDefense.setMode(TowerDefense.MODE_MAPS);
			this.towerDefense.reinitChooseLevel(this.container);
			this.towerDefense.deactivateMenu();
		}
	}
}
