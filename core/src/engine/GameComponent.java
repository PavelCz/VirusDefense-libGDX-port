package engine;

import java.util.ArrayList;
import java.util.List;

import towerDefense.Gameplay;
import towerDefense.TowerDefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import engine.graphics.Background;
import engine.gui.Clickable;
import engine.gui.GUI;

public abstract class GameComponent {
	protected Background background;
	protected List<GUI> guiElements;
	protected List<Clickable> clickables;
	protected boolean mouseWasClicked;

	protected Stage stage;

	protected TowerDefense game;
	private Clickable wasClicked;

	public GameComponent(TowerDefense game) {
		this.game = game;
		this.background = new Background(1.1f, "defaultBackground.jpg", this.game.getGameplay());

		this.guiElements = new ArrayList<GUI>();
		this.clickables = new ArrayList<Clickable>();
	}

	protected void renderGUI(SpriteBatch batch) {
		for (GUI guiElement : this.guiElements) {
			guiElement.draw(batch);
		}

	}

	public void init() {
	}

	public void update(int delta) {
		this.updateClickables(delta);
	}

	public void render(SpriteBatch batch) {
		if (this.background != null) {
			this.background.draw(batch);
		}
		// this.renderGUI(batch); for some reason if I include this the backgound isn't shown
	}

	private void updateClickables(int delta) {
		float x = TowerDefense.getMouseX();
		float y = TowerDefense.getMouseY();
		this.updateHovering(x, y);
		// GDX if left mouse button is down: Gdx.input.isButtonPressed(com.badlogic.gdx.Input.Buttons.LEFT)
		// GDX if left mouse button is pressed and released in short succession: Gdx.input.justTouched()
		if (Gdx.input.justTouched()) {

			if (Gdx.input.isButtonPressed(com.badlogic.gdx.Input.Buttons.LEFT)) {
				this.mouseWasClicked = true;
				for (Clickable clickable : this.clickables) {
					clickable.update(x, y);
				}
			}

		} else if (this.mouseWasClicked && !Gdx.input.isButtonPressed(com.badlogic.gdx.Input.Buttons.LEFT)) {
			this.mouseWasClicked = false;
			for (Clickable clickable : this.clickables) {
				if (!clickable.isStayClicked() && clickable.isActive()) {
					if (clickable.isClicked() && clickable.collides((int) x, (int) y, Gameplay.GLOBAL_GUI_SCALE)) {
						clickable.onRelease();
					} else if (clickable.isClicked()) {
						clickable.setClicked(false);
					}
				}
			}
		}
	}

	public void updateHovering(float x, float y) {
		for (Clickable clickable : this.clickables) {
			if (clickable.collides((int) x, (int) y, Gameplay.GLOBAL_GUI_SCALE) && !clickable.isClicked() && clickable.isActive()) {
				clickable.onHover();
			} else if (!clickable.isClicked() && clickable.isActive()) {
				clickable.onUnHover();
			}
		}
	}

	public SoundHandler getSoundHandler() {
		return this.game.getSoundHandler();
	}

	public void releaseAllClickablesExcept(Clickable excluded) {
		for (Clickable clickable : this.clickables) {
			if (clickable != excluded) {
				clickable.onRelease();
			}

		}
	}

}
