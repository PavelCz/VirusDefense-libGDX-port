package engine.gui;

import towerDefense.TowerDefense;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class SetGameModeAction extends ChangeListener {
	private TowerDefense towerDefense;
	private int gameMode;

	public SetGameModeAction(TowerDefense towerDefense, int gameMode) {
		this.towerDefense = towerDefense;
		this.gameMode = gameMode;

	}

	@Override
	public void changed(ChangeEvent event, Actor actor) {
		this.towerDefense.setMode(this.gameMode);
	}

}
