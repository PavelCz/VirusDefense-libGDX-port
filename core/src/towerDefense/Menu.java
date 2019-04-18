package towerDefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import engine.GameComponent;
import engine.gui.SetGameModeAction;

public class Menu extends GameComponent {

	private TextField t;
	// TODO: convert to libGDX Label if wanted
	//private StaticText version = new StaticText(0, 0, 10, Color.WHITE, "v0.6");
	private Label lostWonMessage;
	private TextButton startButton;
	private TextButton resumeButton;
	private TextButton settingsButton;
	private TextButton highscoresButton;

	private Label pausedMessage;
	TextButton exitGameButton;

	public Menu(TowerDefense game) {
		super(game);
	}

	@Override
	public void init() {
		super.init();

		Image background = new Image(new Texture("data/graphics/viren.png"));
		this.addActor(background);

		this.pausedMessage = new Label("VIRUS DEFENSE", this.game.getLabelStyle());
		this.pausedMessage.setHeight(50);
		this.addActor(pausedMessage);

		TextButtonStyle textButtonStyle = this.game.getTextButtonStyle();

		this.pausedMessage
				.setPosition((TowerDefense.getWidth() - this.pausedMessage.getWidth()) / 2, TowerDefense.getHeight()
						* 0.75f);

		this.lostWonMessage = new Label("", this.game.getLabelStyle());
		this.lostWonMessage.setPosition(TowerDefense.getWidth() / 4, 0);
		this.lostWonMessage.setColor(Color.RED);
		this.lostWonMessage.setHeight(20);
		this.addActor(lostWonMessage);

		this.resumeButton = new TextButton("Resume game", textButtonStyle);
		int y = (int) (TowerDefense.getHeight() / 2 + TowerDefense.getHeight() / 8 + this.resumeButton.getHeight());
		this.resumeButton.setX(TowerDefense.getWidth() / 2 - this.resumeButton.getWidth() / 2);
		this.resumeButton.setY(y + 1);
		// this.resumeButton.setColor(Color.WHITE);
		this.resumeButton.setVisible(false);
		this.resumeButton.setDisabled(true);

		this.startButton = new TextButton("Start game", textButtonStyle);
		y -= this.startButton.getHeight();
		this.startButton.setX(TowerDefense.getWidth() / 2 - this.startButton.getWidth() / 2);
		this.startButton.setY(y);
		y -= this.startButton.getHeight() + 1;

		this.settingsButton = new TextButton("Settings", textButtonStyle);
		this.settingsButton.setX(TowerDefense.getWidth() / 2 - this.settingsButton.getWidth() / 2);
		this.settingsButton.setY(y);
		y -= this.startButton.getHeight() + 1;

		this.highscoresButton = new TextButton("Highscores", textButtonStyle);
		this.highscoresButton.setX(TowerDefense.getWidth() / 2 - this.highscoresButton.getWidth() / 2);
		this.highscoresButton.setY(y);
		y -= this.startButton.getHeight() + 1;

		this.exitGameButton = new TextButton("Exit game", textButtonStyle);
		this.exitGameButton.setX(TowerDefense.getWidth() / 2 - this.exitGameButton.getWidth() / 2);
		this.exitGameButton.setY(y);

		this.t = new TextField("Player", new Skin(Gdx.files.internal("uiskin.json")));
		this.t.setMessageText("Player");
		this.t.setSize(100, 25);
		float x = TowerDefense.getWidth() / 2 - this.t.getWidth() / 2;
		System.out.println(this.t.getWidth() / 2);
		this.t.setPosition(x, TowerDefense.getHeight() - y - this.t.getHeight());
		this.t.setCursorPosition(6);
		this.t.setDisabled(false);

		// set Listeners for button click functionality

		this.startButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setMode(TowerDefense.MODE_MAPS);
				// this.towerDefense.reinitChooseLevel(this.container);
				game.deactivateMenu();
			}
		});

		// sets Click event of button "Settings" to go to Settings
		this.settingsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setMode(TowerDefense.MODE_SETTINGS);
				game.deactivateMenu();
			}
		});

		this.highscoresButton.addListener(new SetGameModeAction(this.game, TowerDefense.MODE_SCORES));

		// sets Click event of button "Exit Game" to close game
		this.exitGameButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});

	}

	public String getPlayerName() {
		return this.t.getText();
	}

	public void setLost(int score, String name) {
		this.lostWonMessage.setText("You lost, " + name + "!\nYour Score was: " + score + " Points.");
		this.lostWonMessage.setPosition((TowerDefense.getWidth() - this.lostWonMessage.getWidth()) / 2, 0);
		this.lostWonMessage.setColor(Color.RED);
	}

	public void setPauseMenu() {
		this.resumeButton.setDisabled(false);
		this.resumeButton.setVisible(true);
		this.pausedMessage.setText("GAME PAUSED");
		this.pausedMessage.setHeight(30);
		this.pausedMessage.setPosition((TowerDefense.getWidth() - this.pausedMessage.getWidth()) / 2, TowerDefense
				.getHeight() / 4);
		this.settingsButton.setDisabled(true);
		this.settingsButton.setColor(Color.LIGHT_GRAY);
		this.lostWonMessage.setVisible(false);
	}

	public void setStartMenu() {
		this.resumeButton.setDisabled(true);
		this.resumeButton.setVisible(false);
		this.pausedMessage.setText("VIRUS DEFENSE");
		this.pausedMessage.setHeight(50);
		this.pausedMessage.setPosition((TowerDefense.getWidth() - this.pausedMessage.getWidth()) / 2, TowerDefense
				.getHeight() / 4);
		this.settingsButton.setDisabled(false);
		this.settingsButton.setColor(Color.WHITE);
		this.lostWonMessage.setVisible(true);
	}

	public void setDisableTextField(boolean disabled) {
		this.t.setDisabled(disabled);
	}

	public void setWon(int score, String name) {
		this.lostWonMessage.setText("You beat the level, " + name + "!\nYour Score was: " + score + " Points.");
		this.lostWonMessage.setPosition((TowerDefense.getWidth() - this.lostWonMessage.getWidth()) / 2, 0);
		this.lostWonMessage.setColor(Color.GREEN);

	}

	public void initStage() {

		this.getStage().addActor(this.t);
		this.getStage().addActor(this.exitGameButton);
		this.addActor(this.startButton);
		this.addActor(this.settingsButton);
		this.addActor(this.highscoresButton);
	}

}
