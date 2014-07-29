package engine.gui;

import engine.graphics.OwnSprite;
import towerDefense.Gameplay;

public class Button extends Clickable {
	private OwnSprite unclickedButton;
	private OwnSprite clickedButton;

	public Button(float x, float y, String unclickedButtonPath, String clickedButtonPath, Gameplay game, boolean stayClicked) {
		this(x, y, new OwnSprite(unclickedButtonPath), new OwnSprite(clickedButtonPath), game, stayClicked);
	}

	public Button(float x, float y, OwnSprite unclickedButton, OwnSprite clickedButton, Gameplay game, boolean stayClicked) {
		super(x, y, game, stayClicked);
		this.unclickedButton = unclickedButton;
		this.clickedButton = clickedButton;
		this.collisionWidth = this.unclickedButton.getWidth();
		this.collisionHeight = this.unclickedButton.getHeight();
	}

	@Override
	public void draw() {
		if (!this.clicked) {
			this.unclickedButton.draw(this.x, this.y, Gameplay.GLOBAL_GUI_SCALE);

		} else {
			this.clickedButton.draw(this.x, this.y, Gameplay.GLOBAL_GUI_SCALE);
		}

	}

	@Override
	public void onHover() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUnHover() {
		// TODO Auto-generated method stub

	}

	public void setUnclickedButton(OwnSprite picture) {
		this.unclickedButton = picture;
	}

	public void setClickedButton(OwnSprite picture) {
		this.clickedButton = picture;
	}

}
