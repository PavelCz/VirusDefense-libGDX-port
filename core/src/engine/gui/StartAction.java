package engine.gui;

import towerDefense.TowerDefense;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class StartAction extends ChangeListener {
	private TowerDefense towerDefense;

	public StartAction(TowerDefense towerDefense) {
		this.towerDefense = towerDefense;
	}

	@Override
	public void changed(ChangeEvent event, Actor actor) {
		this.towerDefense.setMode(TowerDefense.MODE_MAPS);
		// this.towerDefense.reinitChooseLevel(this.container);
		this.towerDefense.deactivateMenu();
	}

	// public void onRelease() {
	// if (this.active) {
	// super.onRelease();
	// this.towerDefense.setMode(TowerDefense.MODE_MAPS);
	// // this.towerDefense.reinitChooseLevel(this.container);
	// this.towerDefense.deactivateMenu();
	// }
	// }
}
