package engine.gui;

import org.newdawn.slick.GameContainer;

import engine.GameComponent;
import towerDefense.Gameplay;
import towerDefense.TowerDefense;

public abstract class Clickable extends GUI {
	protected float collisionWidth, collisionHeight;
	protected boolean clicked = false;
	protected GameComponent gameComponent;
	protected boolean stayClicked;
	protected boolean active = true;

	public Clickable(float x, float y, GameComponent gameComponent, boolean stayClicked) {
		super(x, y);
		this.gameComponent = gameComponent;
		this.stayClicked = stayClicked;
	}

	public void update(float mouseX, float mouseY, GameContainer container) {
		if (this.active) {
			if (this.collides((int) mouseX, (int) mouseY, Gameplay.GLOBAL_GUI_SCALE)) {
				if (this.clicked) {
					this.onRelease();
				} else {
					if (this.gameComponent != null) {
						this.gameComponent.releaseAllClickablesExcept(this);
					}

					this.onClick();

				}

				TowerDefense.getSoundHandler().play("press");
			}
		}

	}

	public boolean isActive() {
		return this.active;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	public void onClick() {
		this.clicked = true;
	}

	public void onRelease() {
		this.clicked = false;
	}

	public abstract void onHover();

	public abstract void onUnHover();

	public boolean collides(int x, int y, float globalScale) {
		return (x >= this.x && x <= this.x + this.collisionWidth * globalScale && y >= this.y && y <= this.y + this.collisionHeight
				* globalScale);
	}

	public boolean isStayClicked() {
		return this.stayClicked;
	}

	public boolean isClicked() {
		return this.clicked;
	}

}
