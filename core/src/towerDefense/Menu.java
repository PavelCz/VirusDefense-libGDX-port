package towerDefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import engine.GameComponent;
import engine.graphics.Background;
import engine.gui.SetGameModeButton;
import engine.gui.StartAction;
import engine.gui.StaticText;

public class Menu extends GameComponent {

	private TextField t;
	private Stage stage;
	private StaticText version = new StaticText(0, 0, 10, Color.WHITE, "v0.6");
	private StaticText lostWonMessage;
	private TextButton startButton;
	private SetGameModeButton resumeButton;
	private TextButton settingsButton;
	private StaticText pausedMessage = new StaticText(0, 0, 50, Color.WHITE, "VIRUS DEFENSE");
	TextButton exitGameButton;

	public Menu(TowerDefense game) {
		super(game);
	}

	@Override
	public void init() {
		super.init();

		BitmapFont font = new BitmapFont(Gdx.files.internal("default.fnt"));
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = font;
		textButtonStyle.fontColor = Color.WHITE;
		textButtonStyle.downFontColor = Color.BLACK;
		textButtonStyle.overFontColor = Color.GRAY;
		textButtonStyle.checkedOverFontColor = Color.GRAY;

		this.background = new Background(1f, "viren.png", this.game.getGameplay());

		this.pausedMessage
				.setPosition((TowerDefense.getWidth() - this.pausedMessage.getWidth()) / 2, TowerDefense.getHeight() * 0.75f);
		this.guiElements.add(this.pausedMessage);

		this.lostWonMessage = new StaticText(TowerDefense.getWidth() / 4, 0, 20, Color.RED, "");
		this.guiElements.add(this.lostWonMessage);

		this.resumeButton = new SetGameModeButton(0, 0, "Resume game", this.game, TowerDefense.MODE_GAME);
		this.clickables.add(this.resumeButton);
		this.guiElements.add(this.resumeButton);
		int y = TowerDefense.getHeight() / 2 + TowerDefense.getHeight() / 8 + this.resumeButton.getTextHeight();
		this.resumeButton.setX(TowerDefense.getWidth() / 2 - this.resumeButton.getWidth() / 2);
		this.resumeButton.setY(y + 1);
		this.resumeButton.setColor(Color.WHITE);
		this.resumeButton.setVisible(false);
		this.resumeButton.deactivate();

		this.startButton = new TextButton("Start game", textButtonStyle);
		y -= this.startButton.getHeight();
		this.startButton.setX(TowerDefense.getWidth() / 2 - this.startButton.getWidth() / 2);
		this.startButton.setY(y);
		y -= this.startButton.getHeight() + 1;

		this.settingsButton = new TextButton("Settings", textButtonStyle);
		this.settingsButton.setX(TowerDefense.getWidth() / 2 - this.settingsButton.getWidth() / 2);
		this.settingsButton.setY(y);
		y -= this.startButton.getHeight() + 1;

		SetGameModeButton scores = new SetGameModeButton(0, 0, "Highscores", this.game, TowerDefense.MODE_SCORES);
		scores.setColor(Color.WHITE);
		this.clickables.add(scores);
		this.guiElements.add(scores);
		scores.setX(TowerDefense.getWidth() / 2 - scores.getWidth() / 2);
		scores.setY(y);
		y -= this.startButton.getHeight() + 1;

		this.exitGameButton = new TextButton("Exit Game", textButtonStyle);
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

		this.startButton.addListener(new StartAction(this.game));

		// sets Click event of button "Settings" to go to Settings
		this.settingsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Menu.this.game.setMode(TowerDefense.MODE_SETTINGS);
				Menu.this.game.deactivateMenu();
			}
		});

		// sets Click event of button "Exit Game" to close game
		this.exitGameButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});

	}

	@Override
	public void update(int delta) {
		super.update(delta);
		this.stage.act(delta);
	}

	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		super.renderGUI(batch);
		this.stage.draw();

		// this.version.draw();
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
		this.resumeButton.activate();
		this.resumeButton.setVisible(true);
		this.pausedMessage.setText("GAME PAUSED");
		this.pausedMessage.setHeight(30);
		this.pausedMessage.setPosition((TowerDefense.getWidth() - this.pausedMessage.getWidth()) / 2, TowerDefense.getHeight() / 4);
		this.settingsButton.setDisabled(true);
		this.settingsButton.setColor(Color.LIGHT_GRAY);
		this.lostWonMessage.setVisible(false);
	}

	public void setStartMenu() {
		this.resumeButton.deactivate();
		this.resumeButton.setVisible(false);
		this.pausedMessage.setText("VIRUS DEFENSE");
		this.pausedMessage.setHeight(50);
		this.pausedMessage.setPosition((TowerDefense.getWidth() - this.pausedMessage.getWidth()) / 2, TowerDefense.getHeight() / 4);
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

	public Stage getStage() {
		return this.stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;

		this.getStage().addActor(this.t);
		this.getStage().addActor(this.exitGameButton);
		this.stage.addActor(this.startButton);
		this.stage.addActor(this.settingsButton);
	}

}
