package towerDefense;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;

import engine.GameComponent;
import engine.gui.Clickable;
import engine.gui.ClickableText;
import engine.gui.SetGameModeButton;
import engine.gui.StaticText;

public class Settings extends GameComponent {

	private TextField widthField;
	private TextField heightField;
	private ClickableText apply;
	private StaticText warning;
	private SetGameModeButton back;
	private ClickableText fullscreen;
	private StaticText supportedResolutionsText;
	private ClickableText[] resolutionClickables;
	private Integer[][] resolutions;
	private final int minWidth = 600, minHeight = 480;

	public Settings(TowerDefense game, GameContainer container) {
		super(game);

		this.back = new SetGameModeButton(0, 0, "Back", this.game, TowerDefense.MODE_MENU);
		this.clickables.add(this.back);
		this.guiElements.add(this.back);

		this.back.setX(0);
		this.back.setY(TowerDefense.getHeight() - this.back.getTextHeight() * 2);

		TrueTypeFont ttt = new TrueTypeFont(new Font("Verdana", Font.PLAIN, 15), true);
		int fieldsX = 0;
		int fieldsY = 100;
		int fieldWidth = 50;
		this.widthField = new TextField(container, ttt, fieldsX, fieldsY, fieldWidth, 25);
		this.widthField.setText(TowerDefense.getWidth() + "");
		this.widthField.setBorderColor(Color.gray);
		this.widthField.setBackgroundColor(Color.lightGray);
		this.widthField.setMaxLength(4);
		this.widthField.setCursorPos(this.widthField.getWidth());
		fieldsX += fieldWidth;

		this.heightField = new TextField(container, ttt, fieldsX, fieldsY, fieldWidth, 25);
		this.heightField.setText(TowerDefense.getHeight() + "");
		this.heightField.setBorderColor(Color.gray);
		this.heightField.setBackgroundColor(Color.lightGray);
		this.heightField.setMaxLength(4);
		this.heightField.setCursorPos(this.heightField.getWidth());
		fieldsX += fieldWidth + 5;

		this.apply = new ClickableText(fieldsX, fieldsY, "Apply", Gameplay.GLOBAL_GUI_SCALE, game.getGameplay(), false);
		this.apply.setColor(Color.black);
		this.clickables.add(this.apply);
		this.guiElements.add(this.apply);

		fieldsX += this.apply.getWidth() + 10;

		this.warning = new StaticText(fieldsX, fieldsY, Color.red, "Please enter a number.");
		this.warning.setVisible(false);
		this.guiElements.add(this.warning);
		fieldsX = 0;
		fieldsY += this.widthField.getHeight();
		this.fullscreen = new ClickableText(fieldsX, fieldsY, "Toggle fullscreen", Gameplay.GLOBAL_GUI_SCALE, game.getGameplay(),
				false);
		this.fullscreen.setColor(Color.black);
		this.clickables.add(this.fullscreen);
		this.guiElements.add(this.fullscreen);

		Integer[][] supportedResolutions = new Integer[0][0];
		try {

			supportedResolutions = this.getSupportedDisplayModes();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.supportedResolutionsText = new StaticText(0, 0, (int) 15, Color.black, "Supported Fullscreen\nResolutions:");

		this.guiElements.add(this.supportedResolutionsText);

		this.resolutionClickables = new ClickableText[supportedResolutions.length];
		this.resolutions = supportedResolutions;
		for (int i = 0; i < this.resolutionClickables.length; ++i) {
			this.resolutionClickables[i] = new ClickableText(0, 0, supportedResolutions[i][0] + " x " + supportedResolutions[i][1],
					Gameplay.GLOBAL_GUI_SCALE, game.getGameplay(), false);
			this.resolutionClickables[i].setColor(Color.black);
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
	public void render(GameContainer container, Graphics graphics) throws SlickException {
		super.render(container, graphics);
		this.widthField.render(container, graphics);
		this.heightField.render(container, graphics);
		// this.warning.draw();
		// this.fullscreen.draw();
		// this.supportedResolutions.draw();
	}

	@Override
	public void update(GameContainer container, int delta) {
		Input input = container.getInput();
		float x = input.getMouseX();
		float y = input.getMouseY();
		super.updateHovering(x, y);
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {

			this.mouseWasClicked = true;

			for (Clickable clickable : this.clickables) {
				clickable.update(x, y, container);
			}

		} else if (this.mouseWasClicked && !input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			this.mouseWasClicked = false;
			for (Clickable clickable : this.clickables) {
				if (!clickable.isStayClicked()) {
					if (clickable.isClicked() && clickable.collides((int) x, (int) y, Gameplay.GLOBAL_GUI_SCALE)) {
						clickable.onRelease();
						if (clickable == this.apply) {
							this.updateApplyButton(container, delta);
						} else if (clickable == this.fullscreen) {
							this.updateFullscreenButton(container, delta);
						}
						for (int i = 0; i < this.resolutions.length; ++i) {
							if (this.resolutionClickables[i] == clickable) {
								this.widthField.setText(this.resolutions[i][0].toString());
								this.heightField.setText(this.resolutions[i][1].toString());
							}
						}

					} else if (clickable.isClicked()) {
						clickable.setClicked(false);
					}
				}

			}
		}
		if (input.isKeyPressed(Input.KEY_TAB)) {
			if (this.widthField.hasFocus()) {
				this.widthField.setFocus(false);
				this.heightField.setFocus(true);
			}
		}

	}

	public void updateFullscreenButton(GameContainer container, int delta) {
		this.widthField.setFocus(false);
		this.heightField.setFocus(false);
		if (TowerDefense.isFULLSCREEN()) {
			try {
				container.setFullscreen(false);
				TowerDefense.setFULLSCREEN(false);

			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			try {
				container.setFullscreen(true);
				TowerDefense.setFULLSCREEN(true);
				this.warning.setVisible(false);
			} catch (SlickException e) {
				this.warning.setText("Not a supported fullscreen resolution.");
				this.warning.setVisible(true);
			}
		}

		TowerDefense.writeSettingsToFile();
	}

	public void updateApplyButton(GameContainer container, int delta) {
		this.widthField.setFocus(false);
		this.heightField.setFocus(false);
		String newWidthString = this.widthField.getText();
		String newHeightString = this.heightField.getText();
		try {
			int newWidth = Integer.parseInt(newWidthString);
			int newHeight = Integer.parseInt(newHeightString);
			this.warning.setVisible(false);
			if (newWidth >= this.minWidth && newHeight >= this.minHeight) {

				try {
					AppGameContainer gameContainer = (AppGameContainer) container;
					gameContainer.setDisplayMode(newWidth, newHeight, TowerDefense.isFULLSCREEN());
					TowerDefense.updateDimensions(container);
					TowerDefense.writeSettingsToFile();
					this.back.setX(0);
					this.back.setY(TowerDefense.getHeight() - this.back.getTextHeight() * 2);
					this.game.reinitMenu(container);
					this.game.reinitChooseLevel(container);
					this.game.reinitComponents(gameContainer);
					this.updateResolutionsPosition();
				} catch (SlickException e) {
					this.warning.setText("Not a supported fullscreen resolution.");
					this.warning.setVisible(true);
				}

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

	public void activate(GameContainer container) {
		// this.widthField = new TextField(container, new TrueTypeFont(new Font("Verdana", Font.PLAIN, 15), true), 0, 100, 50, 25);
		this.widthField.setText(TowerDefense.getWidth() + "");
		// this.widthField.setBorderColor(Color.gray);
		// this.widthField.setBackgroundColor(Color.lightGray);
		// this.widthField.setMaxLength(5);
		//
		// this.heightField = new TextField(container, new TrueTypeFont(new Font("Verdana", Font.PLAIN, 15), true), 50, 100, 50, 25);
		this.heightField.setText(TowerDefense.getHeight() + "");
		// this.heightField.setBorderColor(Color.gray);
		// this.heightField.setBackgroundColor(Color.lightGray);
		// this.heightField.setMaxLength(5);
	}

	private Integer[][] getSupportedDisplayModes() throws LWJGLException {
		DisplayMode[] modes = Display.getAvailableDisplayModes();

		List<int[]> resolutionsList = new ArrayList<int[]>();
		// copies the resolutions int a List of arrays
		// TODO: this is the reason the game takes so long to start up
		int[] resolution = new int[2];
		for (DisplayMode displayMode : modes) {

			resolution[0] = displayMode.getWidth();
			resolution[1] = displayMode.getHeight();
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

}
