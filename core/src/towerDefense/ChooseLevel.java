package towerDefense;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

import com.badlogic.gdx.Gdx;

import engine.GameComponent;
import engine.Level;
import engine.LevelHandler;
import engine.graphics.OwnSprite;
import engine.gui.Button;
import engine.gui.Clickable;
import engine.gui.SetGameModeButton;
import engine.gui.StaticText;

public class ChooseLevel extends GameComponent {

	private Button button, left, right;
	private int page, lastPage;
	private StaticText title = new StaticText(0, 0, 20, Color.black, "Choose a level");

	private Level currentLevel;

	private LevelHandler levelHandler = new LevelHandler();

	public ChooseLevel(TowerDefense game) {
		super(game);
		this.title.setPosition((TowerDefense.getWidth() - this.title.getWidth()) / 2, TowerDefense.getHeight() / 4);
		this.guiElements.add(this.title);
		this.page = 0;
		this.levelHandler.add("level1.txt", game.getGameplay());
		this.levelHandler.add("level4.txt", game.getGameplay());
		this.levelHandler.add("level2.txt", game.getGameplay());
		// this.levelHandler.add("level3.txt", game.getGameplay());

		this.currentLevel = this.levelHandler.get(this.page);
		OwnSprite currentPreviewPicture = this.currentLevel.getPreviewPicture();
		OwnSprite leftSprite = new OwnSprite("left.png", 0.07f);
		OwnSprite rightSprite = new OwnSprite("right.png", 0.07f);

		float leftX = TowerDefense.getWidth() / 4 - leftSprite.getWidth() / 2;
		float leftY = TowerDefense.getHeight() / 2 - leftSprite.getHeight() / 2;
		float rightX = TowerDefense.getWidth() - leftX;
		float rightY = leftY;
		float buttonX = TowerDefense.getWidth() / 2 - currentPreviewPicture.getWidth() / 2;
		float buttonY = TowerDefense.getHeight() / 2 - currentPreviewPicture.getHeight() / 2;

		this.button = new Button(buttonX, buttonY, currentPreviewPicture, currentPreviewPicture, game.getGameplay(), false);
		this.left = new Button(leftX, leftY, leftSprite, new OwnSprite("leftClicked.png", 0.065f), game.getGameplay(), false);
		this.right = new Button(rightX, rightY, rightSprite, new OwnSprite("rightClicked.png", 0.065f), game.getGameplay(), false);

		SetGameModeButton back = new SetGameModeButton(0, 0, "Back", this.game, TowerDefense.MODE_MENU);
		back.setX(0);
		back.setY(TowerDefense.getHeight() - back.getTextHeight() * 2);
		this.clickables.add(back);
		this.guiElements.add(back);

		this.clickables.add(this.button);
		this.clickables.add(this.left);
		this.clickables.add(this.right);
		this.guiElements.add(this.button);
		this.guiElements.add(this.left);
		this.guiElements.add(this.right);
		this.lastPage = this.levelHandler.getLength() - 1;

		this.lastPage = this.levelHandler.getLength() - 1;
	}

	@Override
	public void update(int delta) throws SlickException {

		this.mouseEvents(delta);
		this.button.setUnclickedButton(this.currentLevel.getPreviewPicture());
		this.button.setClickedButton(this.currentLevel.getPreviewPicture());
	}

	private void mouseEvents(int delta) {
		// Input input = container.getInput();
		// float x = input.getMouseX();
		// float y = input.getMouseY();
		float x = Gdx.input.getX();
		float y = Gdx.input.getY();
		super.updateHovering(x, y);
		if (Gdx.input.justTouched()) {
			this.mouseWasClicked = true;

			for (Clickable clickable : this.clickables) {
				clickable.update(x, y);
			}

		} else if (this.mouseWasClicked && !Gdx.input.isButtonPressed(com.badlogic.gdx.Input.Buttons.LEFT)) {
			this.mouseWasClicked = false;
			for (Clickable clickable : this.clickables) {
				if (!clickable.isStayClicked()) {
					if (clickable.isClicked() && clickable.collides((int) x, (int) y, Gameplay.GLOBAL_GUI_SCALE)) {
						clickable.onRelease();
						if (clickable == this.left) {
							--this.page;
							if (this.page < 0) {
								this.page = this.lastPage;
							}
							this.currentLevel = this.levelHandler.get(this.page);
							this.currentLevel = this.levelHandler.get(this.page);
						} else if (clickable == this.right) {
							this.page += 1;
							if (this.page > this.lastPage) {
								this.page = 0;
							}
							this.currentLevel = this.levelHandler.get(this.page);
						} else if (clickable == this.button) {

							this.game.initGameplay(this.currentLevel);
							// this.game.setLevel(this.currentLevel);
							this.game.getGameplay().setPlayerName(this.game.getPlayerName());
							this.game.setMode(TowerDefense.MODE_GAME);
						}
					} else if (clickable.isClicked()) {
						clickable.setClicked(false);
					}
				}
			}
		}
	}

}
