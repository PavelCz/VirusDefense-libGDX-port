package engine;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import towerDefense.Gameplay;
import towerDefense.TowerDefense;
import engine.graphics.Background;
import engine.gui.Clickable;
import engine.gui.GUI;

public abstract class GameComponent {
	protected Background background;
	protected List<GUI> guiElements;
	protected List<Clickable> clickables;
	protected boolean mouseWasClicked;

	protected TowerDefense game;
	private Clickable wasClicked;

	public GameComponent(TowerDefense game) {
		this.game = game;
		this.background = new Background(1.1f, "defaultBackground.jpg", this.game.getGameplay());

		this.guiElements = new ArrayList<GUI>();
		this.clickables = new ArrayList<Clickable>();
	}

	private void renderGUI() {
		for (GUI guiElement : this.guiElements) {
			guiElement.draw();
		}
	}

	public void init(GameContainer container) throws SlickException {
	}

	public void update(GameContainer container, int delta) throws SlickException {
		this.updateClickables(container, delta);
	}

	public void render(GameContainer container, Graphics graphics) throws SlickException {
		if (this.background != null) {
			this.background.draw();
		}
		this.renderGUI();
	}

	private void updateClickables(GameContainer container, int delta) {
		Input input = container.getInput();
		float x = input.getMouseX();
		float y = input.getMouseY();
		this.updateHovering(x, y);
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			this.mouseWasClicked = true;
			for (Clickable clickable : this.clickables) {
				clickable.update(x, y, container);
			}

		} else if (this.mouseWasClicked && !input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
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
