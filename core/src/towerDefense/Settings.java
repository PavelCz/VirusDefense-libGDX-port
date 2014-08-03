package towerDefense;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import engine.GameComponent;
import engine.gui.Clickable;
import engine.gui.ClickableText;
import engine.gui.SetGameModeButton;
import engine.gui.StaticText;

public class Settings extends GameComponent {

	private TextField widthField;
	private TextField heightField;

	private Stage stage;
	private ClickableText apply;
	private StaticText warning;
	private SetGameModeButton back;
	private ClickableText fullscreen;
	private StaticText supportedResolutionsText;
	private ClickableText[] resolutionClickables;
	private Integer[][] resolutions;
	private final int minWidth = 600, minHeight = 480;

	public Settings(TowerDefense game) {
		super(game);

		this.back = new SetGameModeButton(0, 0, "Back", this.game, TowerDefense.MODE_MENU);
		this.clickables.add(this.back);
		this.guiElements.add(this.back);

		this.back.setX(0);
		this.back.setY(TowerDefense.getHeight() - this.back.getTextHeight() * 2);

		int fieldsX = 0;
		int fieldsY = 100;
		int fieldWidth = 50;

		this.widthField = new TextField(TowerDefense.getWidth() + "", new Skin(Gdx.files.internal("uiskin.json")));
		this.widthField.setSize(fieldWidth, 25);
		float x = TowerDefense.getWidth() / 2 - this.widthField.getWidth() / 2;
		this.widthField.setPosition(fieldsX, TowerDefense.getHeight() - fieldsY - this.widthField.getHeight());
		this.widthField.setCursorPosition(this.widthField.getText().getBytes().length);
		this.widthField.setMaxLength(4);

		fieldsX += fieldWidth;

		this.heightField = new TextField(TowerDefense.getHeight() + "", new Skin(Gdx.files.internal("uiskin.json")));
		this.heightField.setSize(fieldWidth, 25);
		x = TowerDefense.getWidth() / 2 - this.widthField.getWidth() / 2;
		this.heightField.setPosition(fieldsX, TowerDefense.getHeight() - fieldsY - this.heightField.getHeight());
		this.heightField.setCursorPosition(this.heightField.getText().getBytes().length);
		this.heightField.setMaxLength(4);

		fieldsX += fieldWidth + 5;

		this.apply = new ClickableText(fieldsX, fieldsY, "Apply", Gameplay.GLOBAL_GUI_SCALE, game.getGameplay(), false);
		this.apply.setColor(Color.BLACK);
		this.clickables.add(this.apply);
		this.guiElements.add(this.apply);

		fieldsX += this.apply.getWidth() + 10;

		this.warning = new StaticText(fieldsX, fieldsY, Color.RED, "Please enter a number.");
		this.warning.setVisible(false);
		this.guiElements.add(this.warning);
		fieldsX = 0;
		fieldsY += this.widthField.getHeight();
		this.fullscreen = new ClickableText(fieldsX, fieldsY, "Toggle fullscreen", Gameplay.GLOBAL_GUI_SCALE, game.getGameplay(),
				false);
		this.fullscreen.setColor(Color.BLACK);
		this.clickables.add(this.fullscreen);
		this.guiElements.add(this.fullscreen);

		Integer[][] supportedResolutions = new Integer[0][0];
		supportedResolutions = this.getSupportedDisplayModes();
		this.supportedResolutionsText = new StaticText(0, 0, (int) 15, Color.BLACK, "Supported Fullscreen\nResolutions:");

		this.guiElements.add(this.supportedResolutionsText);

		this.resolutionClickables = new ClickableText[supportedResolutions.length];
		this.resolutions = supportedResolutions;
		for (int i = 0; i < this.resolutionClickables.length; ++i) {
			this.resolutionClickables[i] = new ClickableText(0, 0, supportedResolutions[i][0] + " x " + supportedResolutions[i][1],
					Gameplay.GLOBAL_GUI_SCALE, game.getGameplay(), false);
			this.resolutionClickables[i].setColor(Color.BLACK);
			this.clickables.add(this.resolutionClickables[i]);
			this.guiElements.add(this.resolutionClickables[i]);

		}

		this.updateResolutionsPosition();

	}

	private void updateResolutionsPosition() {
		float lines = this.resolutionClickables.length + 2;
		int inbetween = 6;
		float textHeight = Gameplay.STANDARD_TEXT_SCALE;
		textHeight = Math.min(textHeight, (TowerDefense.getHeight() - ((lines - 1) * inbetween)) / lines);
		float textWidth = this.supportedResolutionsText.getWidth();
		float x = TowerDefense.getWidth() - textWidth;
		float y = 0;
		this.supportedResolutionsText.setPosition(x, y);
		this.supportedResolutionsText.setHeight((int) textHeight);
		y += textHeight + inbetween;
		for (ClickableText clickable : this.resolutionClickables) {
			y += textHeight + inbetween;
			clickable.setX((int) x);
			clickable.setY((int) y);
			clickable.setHeight((int) textHeight);

		}
	}

	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);

		super.renderGUI(batch);
		this.widthField.draw(batch, 1f);
		this.heightField.draw(batch, 1f);
		this.warning.draw(batch);
		this.fullscreen.draw(batch);
		// this.supportedResolutions.draw(batch);
	}

	// @Override
	@Override
	public void update(int delta) {
		float x = Gdx.input.getX();
		float y = Gdx.input.getY();
		super.updateHovering(x, y);
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
				if (!clickable.isStayClicked()) {
					if (clickable.isClicked() && clickable.collides((int) x, (int) y, Gameplay.GLOBAL_GUI_SCALE)) {
						clickable.onRelease();
						if (clickable == this.apply) {
							this.updateApplyButton(delta);
						} else if (clickable == this.fullscreen) {
							this.updateFullscreenButton(delta);
						}
						for (int i = 0; i < this.resolutions.length; ++i) {
							if (this.resolutionClickables[i] == clickable) {
								// this.widthField.setText(this.resolutions[i][0].toString());
								// this.heightField.setText(this.resolutions[i][1].toString());
							}
						}

					} else if (clickable.isClicked()) {
						clickable.setClicked(false);
					}
				}

			}
		}
		// if (input.isKeyPressed(Input.KEY_TAB)) {
		// // if (this.widthField.hasFocus()) {
		// // this.widthField.setFocus(false);
		// // this.heightField.setFocus(true);
		// // }
		// }

	}

	public void updateFullscreenButton(int delta) {
		// this.widthField.setFocus(false);
		// this.heightField.setFocus(false);
		if (TowerDefense.isFULLSCREEN()) {
			// container.setFullscreen(false);
			TowerDefense.setFULLSCREEN(false);

		} else {
			// container.setFullscreen(true);
			TowerDefense.setFULLSCREEN(true);
			this.warning.setVisible(false);
			this.warning.setText("Not a supported fullscreen resolution.");
			this.warning.setVisible(true);

		}

		TowerDefense.writeSettingsToFile();
	}

	public void updateApplyButton(int delta) {
		// this.widthField.setFocus(false);
		// this.heightField.setFocus(false);
		String newWidthString = this.widthField.getText();
		String newHeightString = this.heightField.getText();
		try {
			int newWidth = Integer.parseInt(newWidthString);
			int newHeight = Integer.parseInt(newHeightString);
			this.warning.setVisible(false);
			if (newWidth >= this.minWidth && newHeight >= this.minHeight) {

				// try {
				Gdx.graphics.setDisplayMode(newWidth, newHeight, TowerDefense.isFULLSCREEN());
				// AppGameContainer gameContainer = (AppGameContainer) container;
				// gameContainer.setDisplayMode(newWidth, newHeight, TowerDefense.isFULLSCREEN());
				TowerDefense.updateDimensions();
				// TowerDefense.writeSettingsToFile();
				this.back.setX(0);
				this.back.setY(TowerDefense.getHeight() - this.back.getTextHeight() * 2);
				this.game.reinitMenu();
				this.game.reinitChooseLevel();
				this.game.reinitComponents();
				this.updateResolutionsPosition();
				// } catch (SlickException e) {
				// this.warning.setText("Not a supported fullscreen resolution.");
				// this.warning.setVisible(true);
				// }

			} else {
				this.warning.setText("Minimum is " + this.minWidth + " x " + this.minHeight);
				this.warning.setVisible(true);
			}

		} catch (NumberFormatException nfe) {
			this.warning.setText("Please enter a number.");
			this.warning.setVisible(true);
		}
	}

	public void deactivate() {
		// this.widthField.deactivate();
		// this.heightField.deactivate();
	}

	public void activate() {
		// this.widthField = new TextField(container, new TrueTypeFont(new Font("Verdana", Font.PLAIN, 15), true), 0, 100, 50, 25);
		// this.widthField.setText(TowerDefense.getWidth() + "");
		// this.widthField.setBorderColor(Color.gray);
		// this.widthField.setBackgroundColor(Color.lightGray);
		// this.widthField.setMaxLength(5);
		//
		// this.heightField = new TextField(container, new TrueTypeFont(new Font("Verdana", Font.PLAIN, 15), true), 50, 100, 50, 25);
		// this.heightField.setText(TowerDefense.getHeight() + "");
		// this.heightField.setBorderColor(Color.gray);
		// this.heightField.setBackgroundColor(Color.lightGray);
		// this.heightField.setMaxLength(5);
	}

	private Integer[][] getSupportedDisplayModes() {

		// DisplayMode[] modes = Display.getAvailableDisplayModes();
		DisplayMode[] modes = Gdx.graphics.getDisplayModes();
		List<int[]> resolutionsList = new ArrayList<int[]>();
		// copies the resolutions int a List of arrays
		// TODO: this is the reason the game takes so long to start up
		int[] resolution = new int[2];
		for (Graphics.DisplayMode displayMode : modes) {

			resolution[0] = displayMode.width;
			resolution[1] = displayMode.height;
			boolean contained = false;
			for (int[] addedResolution : resolutionsList) {

				if (resolution[0] == addedResolution[0] && resolution[1] == addedResolution[1]) {
					contained = true;

				}
			}
			if (!contained) {
				resolutionsList.add(resolution);
			}
		}
		// creates an Array from the List
		Integer[][] resolutionsArray = new Integer[resolutionsList.size()][2];
		for (int i = 0; i < resolutionsArray.length; ++i) {
			resolutionsArray[i][0] = resolutionsList.get(i)[0];
			resolutionsArray[i][1] = resolutionsList.get(i)[1];
		}
		// sorts by the screen area
		Arrays.sort(resolutionsArray, new Comparator<Integer[]>() {
			@Override
			public int compare(final Integer[] entry1, final Integer[] entry2) {
				final Integer compare1 = entry1[1] * entry1[0];
				final Integer compare2 = entry2[1] * entry2[0];
				return compare1.compareTo(compare2) * -1;
			}
		});
		return resolutionsArray;

	}

	public Stage getStage() {
		return this.stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;

		this.getStage().addActor(this.heightField);

		this.getStage().addActor(this.widthField);
	}

}
