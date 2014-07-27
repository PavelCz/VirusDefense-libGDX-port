package towerDefense;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.lwjgl.openal.AL;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.MusicListener;
import org.newdawn.slick.SlickException;

import engine.GameComponent;
import engine.Level;
import engine.SoundHandler;
import engine.TextFileToString;

public class TowerDefense extends BasicGame implements MusicListener {

	protected static SoundHandler soundHandler = new SoundHandler();
	public static final int MODE_MENU = 0;
	public static final int MODE_GAME = 1;
	public static final int MODE_MAPS = 2;
	public static final int MODE_SETTINGS = 3;
	public static final int MODE_SCORES = 4;
	public static boolean FULLSCREEN = false;
	private static int HEIGHT;
	private static int WIDTH;

	private Gameplay gameplay;
	private Menu menu;
	private ChooseLevel maps;
	private Settings settings;
	private GameComponent currentGameComponent;
	private Scores scores;

	private boolean quitGame = false;
	private static boolean applet;

	private int mode;

	public TowerDefense(boolean applet) {
		super("Virus Defense");
		TowerDefense.applet = applet;
	}

	@Override
	public void init(GameContainer container) {
		container.setShowFPS(false);
		long time = System.nanoTime();
		if (!container.isFullscreen()) {/* "./data/graphics/icons/icon24.png", (this may be necessary for other platforms(mac)) */
			String[] icons = { "./data/graphics/icons/icon16.png", "./data/graphics/icons/icon32.png" };
			try {
				container.setIcons(icons);
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.initSounds();
		TowerDefense.updateDimensions(container);
		this.reinitMenu(container);
		this.reinitChooseLevel(container);
		this.mode = TowerDefense.MODE_MENU;
		this.currentGameComponent = this.menu;
		long passedTime = System.nanoTime() - time;
		// System.out.println(passedTime / 1000000000.0);
	}

	private void initSounds() {
		TowerDefense.soundHandler.addWav("press");
		TowerDefense.soundHandler.add("place", "place.wav");
		TowerDefense.soundHandler.addWav("bad");
		TowerDefense.soundHandler.addWav("death");
		TowerDefense.soundHandler.addWav("spawn");

		TowerDefense.soundHandler.addWav("explode");
		TowerDefense.soundHandler.addWav("shotT1");
		TowerDefense.soundHandler.addWav("shotT2");
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		if (this.quitGame) {
			container.exit();
			AL.destroy();
		}
		if (this.mode == TowerDefense.MODE_GAME) {
			this.currentGameComponent = this.gameplay;
		} else if (this.mode == TowerDefense.MODE_MAPS) {
			this.currentGameComponent = this.maps;

		} else if (this.mode == TowerDefense.MODE_SETTINGS) {
			if (this.currentGameComponent != this.settings) {
				// this.settings.activate(container);
				this.settings = new Settings(this, container);
			}
			this.currentGameComponent = this.settings;
		} else if (this.mode == TowerDefense.MODE_MENU) {
			if (this.currentGameComponent != this.menu) {
				// this.menu.activate(container);
			}
			this.currentGameComponent = this.menu;
		} else if (this.mode == TowerDefense.MODE_SCORES) {
			if (this.currentGameComponent != this.scores) {
				this.scores = new Scores(this);
			}
			this.currentGameComponent = this.scores;
		}
		this.currentGameComponent.update(container, delta);

	}

	@Override
	public void render(GameContainer container, Graphics graphics) throws SlickException {
		this.currentGameComponent.render(container, graphics);

	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public void quitGame() {
		this.quitGame = true;
	}

	public static SoundHandler getSoundHandler() {
		return TowerDefense.soundHandler;
	}

	public static int getHeight() {
		return TowerDefense.HEIGHT;
	}

	public static int getWidth() {
		return TowerDefense.WIDTH;
	}

	public static void updateDimensions(GameContainer container) {

		TowerDefense.HEIGHT = container.getHeight();
		TowerDefense.WIDTH = container.getWidth();
	}

	public void setLevel(Level level) {
		this.gameplay.setLevel(level);
	}

	public void initGameplay(GameContainer container, Level level) {
		this.gameplay = new Gameplay(this, level, container);
		// try {
		// this.gameplay.init(container);
		// } catch (SlickException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		this.currentGameComponent = this.gameplay;
	}

	public Gameplay getGameplay() {
		return this.gameplay;
	}

	public void deactivateSettings() {
		this.settings.deactivate();
	}

	public static boolean isFULLSCREEN() {
		return FULLSCREEN;
	}

	public static void setFULLSCREEN(boolean fULLSCREEN) {
		FULLSCREEN = fULLSCREEN;
	}

	public void reinitMenu(GameContainer container) {

		this.menu = new Menu(this);
		try {
			this.menu.init(container);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void reinitChooseLevel(GameContainer container) {

		this.maps = new ChooseLevel(this, container);

	}

	public void deactivateMenu() {
		// this.menu.deactivate();
	}

	public static void writeSettingsToFile() {
		PrintWriter writer;
		try {
			writer = new PrintWriter("./data/files/settings.txt", "UTF-8");
			writer.println(TowerDefense.getWidth());
			writer.println(TowerDefense.getHeight());
			if (TowerDefense.isFULLSCREEN()) {
				writer.println(1);
			} else {
				writer.println(0);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void writeScoreToFile(String name, int score) {
		List<String> savedScores = TextFileToString.getLines("score.txt");
		String[][] scores = new String[savedScores.size() + 1][2];
		for (int i = 0; i < savedScores.size(); ++i) {
			String[] separateStrings = savedScores.get(i).split(", ");
			scores[i][0] = separateStrings[0];
			scores[i][1] = separateStrings[1];
		}

		scores[savedScores.size()][0] = name;
		scores[savedScores.size()][1] = new Integer(score).toString();

		// converts the String in the second column to ints and then sorts them by that int value
		Arrays.sort(scores, new Comparator<String[]>() {
			@Override
			public int compare(final String[] entry1, final String[] entry2) {
				final Integer compare1 = Integer.parseInt(entry1[1]);
				final Integer compare2 = Integer.parseInt(entry2[1]);
				return compare1.compareTo(compare2) * -1;
			}
		});

		PrintWriter writer;
		try {
			writer = new PrintWriter("data/files/score.txt", "UTF-8");
			for (int i = 0; i < scores.length; ++i) {
				writer.println(scores[i][0] + ", " + scores[i][1]);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void resetScores() {
		this.scores = new Scores(this);
	}

	public void reinitComponents(GameContainer container) {
		TowerDefense.updateDimensions(container);
		// this.gameplay = new Gameplay(this);
		this.reinitMenu(container);
		this.reinitChooseLevel(container);
		// this.settings = new Settings(this, container);
		this.currentGameComponent = this.menu;
		this.scores = new Scores(this);
	}

	public String getPlayerName() {
		return this.menu.getPlayerName();
	}

	public void setLost(int score, String name) {
		this.menu.setLost(score, name);
	}

	public void setWon(int score, String name) {
		this.menu.setWon(score, name);
	}

	public Menu getMenu() {
		return this.menu;
	}

	@Override
	public void musicEnded(Music arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void musicSwapped(Music arg0, Music arg1) {
		// TODO Auto-generated method stub

	}

	public static boolean isApplet() {
		return applet;
	}

}
