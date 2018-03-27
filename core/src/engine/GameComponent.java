package engine;

import java.util.ArrayList;
import java.util.List;

import towerDefense.Gameplay;
import towerDefense.TowerDefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import engine.graphics.Background;
import engine.gui.GUI;

public abstract class GameComponent {
	protected Background background;
	protected List<GUI> guiElements;
	protected boolean mouseWasClicked;

	protected Stage stage;

	protected TowerDefense game;

	public GameComponent(TowerDefense game) {
		this.stage = new Stage();
		this.game = game;
		this.background = new Background(1.1f, "defaultBackground.jpg", this.game.getGameplay());

		this.guiElements = new ArrayList<GUI>();
	}

	protected void renderGUI(SpriteBatch batch) {
		for (GUI guiElement : this.guiElements) {
			guiElement.draw(batch);
		}

	}

	public void init() {
	}

	public void update(int delta) {
		this.stage.act(delta);

	}

	public void render(SpriteBatch batch) {
		if (this.background != null) {
			this.background.draw(batch);
		}
		this.stage.draw();
		// this.renderGUI(batch); for some reason if I include this the backgound isn't shown
	}

	public SoundHandler getSoundHandler() {
		return this.game.getSoundHandler();
	}

	public Stage getStage() {
		return this.stage;
	}
}
