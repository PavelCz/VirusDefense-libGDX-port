package engine;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import towerDefense.TowerDefense;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class GameComponent extends Stage {
	protected boolean mouseWasClicked;
	Image i = new Image(new Texture("data/graphics/defaultBackground.jpg"));
	//protected Stage stage;

	protected TowerDefense game;

	public GameComponent(TowerDefense game) {
		//this.stage = new Stage();
		this.game = game;
		//this.background = new Background(1.1f, "defaultBackground.jpg", this.game.getGameplay());
		//Image i = new Image(new Texture("data/graphics/defaultBackground.jpg"));
		//i.scaleBy(1.1f);
		//this.addActor(i);
	}

	public void init() {
	}

	public void update(int delta) {
		this.act(delta);

	}

	public void render(SpriteBatch batch) {
		//if (this.background != null) {
			//batch.draw(new Texture("data/graphics/defaultBackground.jpg"), 0f, 0f);
			//i.draw(batch);
		//}
		this.draw();
	}

	public SoundHandler getSoundHandler() {
		return this.game.getSoundHandler();
	}

	public Stage getStage() {
		return this;
	}
}
