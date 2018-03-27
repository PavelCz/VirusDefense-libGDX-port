package engine.gui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import towerDefense.Gameplay;
import towerDefense.towers.Tower;

public class TowerButton extends ImageButton {

	public TowerButton(final Tower tower, final Gameplay game, ImageButtonStyle style) {
		super(style);

		this.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(TowerButton.super.isChecked()) {
					game.setCurrentTower(tower);
					game.getCurrentTower().getSprite().setAlpha(0.5f);
				} else {
					game.setCurrentTower(null);
				}
			}
		});
	}
}
