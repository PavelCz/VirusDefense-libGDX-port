package engine.gui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import towerDefense.Gameplay;
import towerDefense.towers.Tower;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import engine.graphics.OwnSprite;

public class TowerButton extends ImageButton {
	private Tower tower;
	private Gameplay game;

	public TowerButton(final Tower tower, final Gameplay game, ImageButtonStyle style) {
		super(style);
		this.tower = tower;
		this.game = game;

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
	/*
	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
		OwnSprite s = this.tower.getSprite();
		s.setAlpha(0.8f);
		float scale = 0.9f;
		s.draw(this.x + (this.collisionWidth - s.getWidth() * scale) / 2 * Gameplay.GLOBAL_GUI_SCALE, this.y
				+ (this.collisionHeight - s.getHeight() * scale) / 2 * Gameplay.GLOBAL_GUI_SCALE, scale * Gameplay.GLOBAL_GUI_SCALE,
				batch);
		// temporary
		// s.draw(0, 0, scale * Gameplay.GLOBAL_GUI_SCALE, batch);
	}*/
	/*
	@Override
	public void onClick() {
		super.onClick();
		this.game.setCurrentTower(this.tower);
		this.game.getCurrentTower().getSprite().setAlpha(0.5f);

	}

	@Override
	public void onRelease() {
		super.onRelease();
		this.game.setCurrentTower(null);

	}

	public Tower getTower() {
		return this.tower;
	}*/

}
