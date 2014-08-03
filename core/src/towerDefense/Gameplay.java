package towerDefense;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import towerDefense.towers.BombTower;
import towerDefense.towers.LongerShootingTower;
import towerDefense.towers.RocketFastTower;
import towerDefense.towers.RocketTower;
import towerDefense.towers.Tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import engine.Camera;
import engine.Enemy;
import engine.EnemyTypeHandler;
import engine.GameComponent;
import engine.Level;
import engine.MyVector2f;
import engine.Player;
import engine.Waypoint;
import engine.graphics.Background;
import engine.graphics.LibGDXRectangle;
import engine.graphics.LibGDXUnfilledEllipse;
import engine.graphics.LibGDXUnfilledRectangle;
import engine.graphics.OwnSprite;
import engine.gui.Clickable;
import engine.gui.InterfaceBackground;
import engine.gui.SlickHealthbar;
import engine.gui.StaticText;
import engine.gui.TowerButton;
import engine.projectiles.Projectile;

/**
 * @author Pavel
 */
public class Gameplay extends GameComponent implements InputProcessor {
	private SlickHealthbar h;
	private static Camera camera;
	private float height, width;
	private ConcurrentLinkedQueue<Enemy> enemies;
	private boolean debugMode;
	private int passedMilliseconds;
	private int mode;
	private Level currentLevel;
	private int currentTileLength;
	private Tower[][] towers;
	private TowerButton towerButton1;
	private TowerButton towerButton2;
	private TowerButton towerButton3;
	private TowerButton towerButton4;
	private Tower currentTower;
	private Player player;
	private StaticText playerName;
	private StaticText numberLives;
	private StaticText moneyAmount;
	private StaticText score;
	private StaticText towerInfo;
	private StaticText towerName;
	private boolean currentTowerPlaceable;
	private int towerShadowX, towerShadowY;
	protected ConcurrentLinkedQueue<Projectile> projectiles;

	public static float CURRENT_GAME_SCALE;
	public static float MAX_GAME_SCALE;
	public static float GLOBAL_GUI_SCALE = 1f;

	private StaticText passedTime;
	private InterfaceBackground interfaceBackground;
	// Constants:
	public static float INTERFACE_START_X;
	public static int STANDARD_TEXT_SCALE = 15;
	public static int SIZE;
	public static int DEFAULT_SIZE = 64;
	private float speed;

	// Tests:

	//
	public Gameplay(TowerDefense game, Level level) {
		super(game);
		Gdx.input.setInputProcessor(this);
		this.currentLevel = level;
		this.init();
	}

	@Override
	public void init() {
		super.init();
		this.h = new SlickHealthbar(0, 0, 0, 30, 7);
		this.currentLevel.setGame(this);
		this.initDefaults();
		Gameplay.camera = new Camera(0, 0, this);
		this.currentTileLength = Gameplay.DEFAULT_SIZE;
		this.height = Gameplay.DEFAULT_SIZE * this.getVerticalTiles();
		this.width = Gameplay.DEFAULT_SIZE * this.getHorizontalTiles();

		// Set Constants:

		Gameplay.INTERFACE_START_X = TowerDefense.getWidth() - 3 * 64 * Gameplay.GLOBAL_GUI_SCALE;
		float scale1 = Gameplay.INTERFACE_START_X / this.width;
		float scale2 = TowerDefense.getHeight() / this.height;
		Gameplay.CURRENT_GAME_SCALE = Math.max(scale1, scale2);
		Gameplay.MAX_GAME_SCALE = Gameplay.CURRENT_GAME_SCALE;
		Gameplay.SIZE = (int) (64 * Gameplay.CURRENT_GAME_SCALE);

		//
		this.interfaceBackground = new InterfaceBackground("Interface1.png");

		this.towers = new Tower[this.getVerticalTiles()][this.getHorizontalTiles()];

		// add all objects that need to be drawn to the respectable arrays
		// entities

		this.projectiles = new ConcurrentLinkedQueue<Projectile>();
		int offset = 20;
		// Buttons; this has nothing to do with the draw sequence
		this.towerButton1 = new TowerButton(Gameplay.INTERFACE_START_X, 4 * 64 * Gameplay.GLOBAL_GUI_SCALE + offset,
				"buttons/PSButton1.png", "buttons/PSButton1_click.png", new LongerShootingTower(0, 0, new OwnSprite(
						"tower/Tower2.png", 0.5f), this, 400, 0.16f, 400/* , container.getGraphics() */), this);
		this.towerButton2 = new TowerButton(Gameplay.INTERFACE_START_X, 5 * 64 * Gameplay.GLOBAL_GUI_SCALE + offset,
				"buttons/PSButton1.png", "buttons/PSButton1_click.png", new BombTower(0, 0, new OwnSprite("tower/t1n.png", 0.5f),
						this, 1500, 15f, 50), this);
		this.towerButton3 = new TowerButton(Gameplay.INTERFACE_START_X, 6 * 64 * Gameplay.GLOBAL_GUI_SCALE + offset,
				"buttons/PSButton1.png", "buttons/PSButton1_click.png", new RocketTower(0, 0, new OwnSprite("tower/t1.png", 0.5f),
						this, 200, 15f, 50), this);
		this.towerButton4 = new TowerButton(Gameplay.INTERFACE_START_X + 64 + 32, 4 * 64 * Gameplay.GLOBAL_GUI_SCALE + offset,
				"buttons/PSButton1.png", "buttons/PSButton1_click.png", new RocketFastTower(0, 0, new OwnSprite(
						"tower/roteBlutk_klein.png", 0.5f), this, 1000, 20f), this);
		this.clickables.add(this.towerButton1);
		this.clickables.add(this.towerButton2);
		this.clickables.add(this.towerButton3);
		this.clickables.add(this.towerButton4);

		//
		this.initGUI();

	}

	private void initDefaults() {
		this.enemies = new ConcurrentLinkedQueue<Enemy>();
		this.debugMode = false;
		this.passedMilliseconds = 0;
		this.mode = 0;
		this.player = new Player("Player1", 10, 200, 0);
		this.speed = 1f;
		this.currentTowerPlaceable = true;

	}

	private void initGUI() {
		Color defaultTextColor = Color.WHITE;
		float guiTileSize = 64 * Gameplay.GLOBAL_GUI_SCALE;
		float textHeight = 20 * Gameplay.GLOBAL_GUI_SCALE;
		float guiX = 3 * Gameplay.GLOBAL_GUI_SCALE;

		float cursorXStart = Gameplay.INTERFACE_START_X + guiX;
		float cursorYStart = 3 * guiTileSize;
		float cursorX = cursorXStart;
		float cursorY = cursorYStart;

		this.playerName = new StaticText(cursorX, cursorY, defaultTextColor, "Player: " + this.player.getName());
		cursorY += textHeight;

		StaticText livesText = new StaticText(cursorX, cursorY, defaultTextColor, "Lives: ");
		cursorX += livesText.getWidth();
		this.numberLives = new StaticText(cursorX, cursorY, defaultTextColor, "" + this.player.getLives());
		cursorX = cursorXStart;
		cursorY += textHeight;

		StaticText moneyText = new StaticText(cursorX, cursorY, defaultTextColor, "Money: ");
		cursorX += moneyText.getWidth();
		this.moneyAmount = new StaticText(cursorX, cursorY, defaultTextColor, "" + this.player.getMoney());
		cursorX = cursorXStart;
		cursorY += textHeight;

		StaticText scoreText = new StaticText(cursorX, cursorY, defaultTextColor, "Score: ");
		cursorX += scoreText.getWidth();
		this.score = new StaticText(cursorX, cursorY, defaultTextColor, "" + this.player.getScore());

		this.towerName = new StaticText(Gameplay.INTERFACE_START_X + guiTileSize, 10, defaultTextColor, "");
		this.towerInfo = new StaticText(Gameplay.INTERFACE_START_X, guiTileSize, defaultTextColor, "");

		this.passedTime = new StaticText(Gameplay.INTERFACE_START_X + guiX, TowerDefense.getHeight() - textHeight, defaultTextColor,
				this.passedTimeToString());

		this.guiElements.add(this.interfaceBackground);

		this.guiElements.add(scoreText);

		this.guiElements.add(livesText);
		this.guiElements.add(this.moneyAmount);
		this.guiElements.add(this.playerName);
		this.guiElements.add(this.numberLives);
		this.guiElements.add(moneyText);
		this.guiElements.add(this.score);
		this.guiElements.add(this.towerButton1);
		this.guiElements.add(this.towerButton2);
		this.guiElements.add(this.towerButton3);
		this.guiElements.add(this.towerButton4);

		this.guiElements.add(this.passedTime);

		this.guiElements.add(this.towerName);
		this.guiElements.add(this.towerInfo);
	}

	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		this.drawBackground(batch);
		this.currentLevel.renderPath(batch);

		this.renderEnemies(batch);
		this.renderTowers(batch);

		this.renderTowerShadow(batch);

		this.renderGUI(batch);

		for (Projectile projectiles : this.projectiles) {
			projectiles.draw(batch);
		}
		if (this.currentTower != null) {
			this.currentTower.getSprite().draw(INTERFACE_START_X, 0, GLOBAL_GUI_SCALE, batch);
			this.towerName.setText(this.currentTower.getName());
			this.towerInfo.setText("Radius: " + this.currentTower.getRadius() + "\nKosten: " + this.currentTower.getCost()
					+ "\nSchaden: " + this.currentTower.getDamage());
		} else {
			this.towerInfo.setText("");
			this.towerName.setText("");
		}

	}

	@Override
	protected void renderGUI(SpriteBatch batch) {

		super.renderGUI(batch);
		this.renderHealthBars(batch);
		this.renderDebug(batch);

		if (this.mode == 1) {
			new OwnSprite("You Win.png").draw(0, 0, Gameplay.CURRENT_GAME_SCALE, batch);
		} else if (this.mode == -1) {
			new OwnSprite("Game Over.png").draw(0, 0, Gameplay.CURRENT_GAME_SCALE, batch);
		}
		// for (int i = 0; i < this.towers.length; ++i) {
		// for (int j = 0; j < this.towers[0].length; ++j) {
		// if (this.towers[i][j] != null) {
		// Tower currentTower = this.towers[i][j];
		// new SlickUnfilledEllipse(graphics, currentTower.getRadius() * 2, currentTower.getRadius() * 2, Color.white).draw(
		// (currentTower.getX() * this.currentTileLength + Gameplay.DEFAULT_SIZE / 2) * Gameplay.CURRENT_GAME_SCALE
		// - Gameplay.getCameraX(), (currentTower.getY() * this.currentTileLength + DEFAULT_SIZE / 2)
		// * Gameplay.CURRENT_GAME_SCALE - Gameplay.getCameraY(), Gameplay.CURRENT_GAME_SCALE);
		// }
		// }
		// }
	}

	private void renderHealthBars(SpriteBatch batch) {

		for (Enemy enemy : this.enemies) {
			int barLength = 30;
			int barHeight = 7;
			this.h.setX((enemy.getX() - barLength / 2) * Gameplay.CURRENT_GAME_SCALE - Gameplay.getCameraX());
			this.h.setY((enemy.getY() - Gameplay.DEFAULT_SIZE / 2) * Gameplay.CURRENT_GAME_SCALE - Gameplay.getCameraY());
			this.h.setMaxHealth(enemy.getMaxHealth());
			this.h.setHealth(enemy.getHealth());
			this.h.setBordered(true);
			this.h.draw(batch);
		}
	}

	private void renderTowers(SpriteBatch batch) {
		for (Tower[] towerArray : this.towers) {
			for (Tower tower : towerArray) {
				if (tower != null) {
					tower.draw(batch);
				}
			}
		}
	}

	private void renderEnemies(SpriteBatch batch) {
		for (Enemy enemy : this.enemies) {
			if (enemy != null)
				enemy.draw(batch);
		}
	}

	/**
	 * renders the transparent version of the tower's sprite when choosing a place
	 * 
	 * @param container
	 * @param graphics
	 */
	private void renderTowerShadow(SpriteBatch batch) {

		if (this.currentTower != null && this.getMode() == 0) {
			OwnSprite sprite = this.currentTower.getSprite().clone();

			if (this.currentTowerPlaceable) {
				new LibGDXUnfilledRectangle(SIZE / Gameplay.CURRENT_GAME_SCALE, SIZE / Gameplay.CURRENT_GAME_SCALE, Color.GREEN).draw(
						this.towerShadowX, this.towerShadowY, Gameplay.CURRENT_GAME_SCALE, batch);
			} else {
				new LibGDXUnfilledRectangle(SIZE / Gameplay.CURRENT_GAME_SCALE, SIZE / Gameplay.CURRENT_GAME_SCALE, Color.RED).draw(
						this.towerShadowX, this.towerShadowY, Gameplay.CURRENT_GAME_SCALE, batch);
				sprite.setAlpha(0.1f);
				sprite.setColor(1f, 0, 0);

			}

			sprite.draw(this.towerShadowX, this.towerShadowY, Gameplay.CURRENT_GAME_SCALE, batch);
		}
	}

	/**
	 * renders circles for tower radius and enemy radius and black box for FPS
	 * 
	 * @param container
	 * @param graphics
	 */
	private void renderDebug(SpriteBatch batch) {
		if (this.debugMode) {
			for (Enemy enemy : this.enemies) {
				new LibGDXUnfilledEllipse(enemy.getRadius() * 2, enemy.getRadius() * 2, Color.BLUE).draw((enemy.getX())
						* Gameplay.CURRENT_GAME_SCALE - Gameplay.getCameraX(),
						(enemy.getY()) * Gameplay.CURRENT_GAME_SCALE - Gameplay.getCameraY(), Gameplay.CURRENT_GAME_SCALE, batch);
			}

			// create a black box that the FPS are visible
			new LibGDXRectangle(100, 20, Color.BLACK).draw(5, 10, 1f, batch);
		}
	}

	@Override
	public void update(int originalDelta) {
		// System.out.println(originalDelta);
		// if (originalDelta < 100) {
		this.passedMilliseconds += originalDelta;
		this.passedTime.setText(this.passedTimeToString());
		this.moneyAmount.setText("" + this.player.getMoney());
		this.score.setText("" + this.player.getScore());
		int delta = (int) (originalDelta * this.speed);
		if (this.mode == 0) {
			for (Enemy enemy : this.enemies) {

				if (enemy != null)
					enemy.update(delta);
			}
			for (int i = 0; i < this.towers.length; ++i) {
				for (int j = 0; j < this.towers[0].length; ++j) {
					if (this.towers[i][j] != null) {
						this.towers[i][j].update(delta);
					}
				}
			}

			this.currentLevel.getWaveHandler().update(delta);
		}
		this.updateTowerShadow();
		this.mouseEvents(delta);
		this.keyboardEvents(delta);

		// player dead
		if (this.player.getLives() <= 0) {
			this.gameEndActions();
			this.game.setLost(this.player.getScore(), this.player.getName());
			this.game.getMenu().setStartMenu();
		}

		for (Projectile projectiles : this.projectiles) {
			projectiles.update(delta);
		}

		// }
	}

	private void updateTowerShadow() {
		if (this.currentTower != null && this.getMode() == 0) {
			// old version of shadow Coordinates, with pixel accurate
			// coordinates
			// this.towerShadowX = (int) (input.getMouseX() -
			// this.currentTower.getSprite().getWidth() / 2);
			// this.towerShadowY = (int) (input.getMouseY() -
			// this.currentTower.getSprite().getHeight() / 2);
			int x = Gdx.input.getX() + Gameplay.getCameraX();
			int y = Gdx.input.getY() + Gameplay.getCameraY();
			int newX = (x) / Gameplay.SIZE;
			int newY = (y) / Gameplay.SIZE;
			this.towerShadowX = (int) (newX * Gameplay.SIZE - Gameplay.getCameraX());
			this.towerShadowY = (int) (newY * Gameplay.SIZE - Gameplay.getCameraY());
			int[][] path = this.currentLevel.getPath();
			if (this.player.getMoney() < this.currentTower.getCost()) {
				this.currentTowerPlaceable = false;
			} else if (newX >= 0 && newY >= 0 && newY < this.getVerticalTiles() && newX < this.getHorizontalTiles()
					&& path[newY][newX] == 1 && this.towers[newY][newX] == null) {
				this.currentTowerPlaceable = true;
			} else {
				this.currentTowerPlaceable = false;
			}
		}
	}

	/**
	 * updates keyboard events i.e. button presses
	 * 
	 * @param container
	 * @param delta
	 */
	private void keyboardEvents(int delta) {

		if (Gdx.input.isKeyPressed(Input.Keys.I)) {
			this.debugMode = !this.debugMode;
			if (this.debugMode) {
				System.out.println("debug");
				this.player.setMoney(100000);
			} else {
				System.out.println("not debug");
				this.speed = 1f;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			this.game.getMenu().setPauseMenu();
			this.game.setMode(TowerDefense.MODE_MENU);
		}

		float cameraWidth = Gameplay.INTERFACE_START_X;
		float cameraHeight = TowerDefense.getHeight();
		if (Gameplay.getCameraX() < 0) {
			Gameplay.camera.setX(0);
		} else if ((Gameplay.getCameraX() + cameraWidth) / Gameplay.CURRENT_GAME_SCALE > this.getHorizontalTiles()
				* Gameplay.DEFAULT_SIZE) {
			Gameplay.camera.setX((this.getHorizontalTiles() * Gameplay.DEFAULT_SIZE) * Gameplay.CURRENT_GAME_SCALE - cameraWidth);

		} else if ((Gameplay.getCameraY() + cameraHeight) / Gameplay.CURRENT_GAME_SCALE > this.getVerticalTiles()
				* Gameplay.DEFAULT_SIZE) {
			Gameplay.camera.setY((this.getVerticalTiles() * Gameplay.DEFAULT_SIZE) * Gameplay.CURRENT_GAME_SCALE - cameraHeight);

		}

		float scrollSpeed = 0.5f;
		float scrollDistance = scrollSpeed * delta;
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			Gameplay.camera.addX(-scrollDistance);

		}

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			Gameplay.camera.addX(+scrollDistance);

		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			Gameplay.camera.addY(-scrollDistance);

		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			Gameplay.camera.addY(+scrollDistance);

		}

		if (this.debugMode) {
			this.debugKeyboardEvents(delta);
		}

	}

	/**
	 * updates the keyboard events(button presse) only occuring in debug mode
	 * 
	 * @param container
	 * @param delta
	 */
	private void debugKeyboardEvents(int delta) {
		if (Gdx.input.isKeyPressed(Input.Keys.PLUS)) {
			this.speed *= 2f;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
			this.speed /= 2f;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.L)) {
			this.enemies.add(this.getEnemyTypes().createEnemy(0));
		}
	}

	/**
	 * handles mouse events like clicks
	 * 
	 * @param container
	 * @param delta
	 */
	private void mouseEvents(int delta) {
		if (this.mode == 0) {

			float x = Gdx.input.getX();
			float y = Gdx.input.getY();
			if (Gdx.input.justTouched()) { // just touched also true for right mouse button...
				if (Gdx.input.isButtonPressed(com.badlogic.gdx.Input.Buttons.LEFT)) { // ...therefore this if is needed
					this.placeTower();
					for (Clickable clickable : this.clickables) {
						clickable.update(x, y);
					}
				}

			} else if (Gdx.input.isButtonPressed(com.badlogic.gdx.Input.Buttons.RIGHT)) {
				this.releaseAllClickables();

			}
		}
	}

	private void placeTower() {
		float x = Gdx.input.getX() + Gameplay.getCameraX();
		float y = Gdx.input.getY() + Gameplay.getCameraY();
		int newX = (int) x / Gameplay.SIZE;
		int newY = (int) y / Gameplay.SIZE;

		if (this.currentTower != null) {
			int cost = this.currentTower.getCost();
			if (this.currentTowerPlaceable) {
				Tower bufferTower = this.currentTower.clone();
				bufferTower.setX(newX);
				bufferTower.setY(newY);
				bufferTower.getSprite().setAlpha(1f);
				this.towers[newY][newX] = bufferTower;
				this.player.reduceMoney(cost);
				this.game.getSoundHandler().play("place");

				// if this is included buttons are unpressed after each tower placement
				this.currentTower = null;
				this.releaseAllClickables();

			} else {
				boolean mouseCollidesButton = false;
				for (Clickable clickable : this.clickables) {
					if (clickable.collides((int) x, (int) y, Gameplay.GLOBAL_GUI_SCALE)) {
						mouseCollidesButton = true;
					}
				}
				if (!mouseCollidesButton) {
					this.game.getSoundHandler().play("bad");
				}
			}

		}
	}

	private void releaseAllClickables() {
		for (Clickable clickable : this.clickables) {
			clickable.onRelease();
		}
	}

	public ConcurrentLinkedQueue<Enemy> getEnemies() {
		return this.enemies;
	}

	public Waypoint getWaypoints() {
		return this.currentLevel.getWaypoints();
	}

	/**
	 * @return returns a list of coordinates belonging to the waypoints
	 */
	public List<MyVector2f> getWaypointsGrid() {
		List<MyVector2f> coordinates = new ArrayList<MyVector2f>();
		for (Waypoint waypoint = this.getWaypoints(); waypoint != null; waypoint = waypoint.getNextWaypoint()) {
			coordinates.add(new MyVector2f(waypoint.getX(), waypoint.getY()));
		}
		return coordinates;

	}

	public void reduceLives() {
		this.player.reduceLives();
		this.numberLives.setText("" + this.player.getLives());
	}

	public EnemyTypeHandler getEnemyTypes() {
		return this.currentLevel.getEnemyTypes();
	}

	private String millisecondsToTimeString(int milliseconds) {
		int seconds = milliseconds / 1000;
		int minutes = seconds / 60;
		int hours = minutes / 60;
		seconds %= 60;
		minutes %= 60;
		String secondsString = this.makeTwoDecimals(seconds);
		String minutesString = this.makeTwoDecimals(minutes);
		String hoursString = this.makeTwoDecimals(hours);

		if (hours > 0) {
			return hoursString + ":" + minutesString + ":" + secondsString;
		} else {
			return minutesString + ":" + secondsString;
		}
	}

	private String makeTwoDecimals(int number) {
		if (number >= 10) {
			return "" + number;
		} else {
			return "0" + number;
		}
	}

	private String passedTimeToString() {
		return this.millisecondsToTimeString(this.passedMilliseconds);
	}

	public Player getPlayer() {
		return this.player;
	}

	public int getMode() {
		return this.mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public Tower getCurrentTower() {
		return this.currentTower;
	}

	public int getHorizontalTiles() {
		return this.currentLevel.getNumberTilesWidth();
	}

	public void drawBackground(SpriteBatch batch) {
		this.getMapBackground().draw(batch);
	}

	public Background getMapBackground() {
		return this.currentLevel.getMapBackground();
	}

	public int getVerticalTiles() {
		return this.currentLevel.getNumberTilesHeight();
	}

	public void setCurrentTower(Tower currentTower) {
		this.currentTower = currentTower;
	}

	public ConcurrentLinkedQueue<Projectile> getProjectiles() {
		return this.projectiles;
	}

	public void setLevel(Level level) {
		this.currentLevel = level;
	}

	public boolean currentTowerPlaceable() {
		return this.currentTowerPlaceable;
	}

	public static int getCameraX() {
		return (int) Gameplay.camera.getX();
	}

	public static int getCameraY() {
		return (int) Gameplay.camera.getY();
	}

	public StaticText getScore() {
		return this.score;
	}

	public void setScore(StaticText score) {
		this.score = score;
	}

	public void setPlayerName(String playerName) {
		this.player.setName(playerName);
		this.playerName.setText("Player: " + this.player.getName());
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		int mouseWheel = -amount;
		if (mouseWheel > 0) { // mouse wheel up
			Gameplay.CURRENT_GAME_SCALE *= 1.1f;
			if (Gameplay.CURRENT_GAME_SCALE > 6) {
				Gameplay.CURRENT_GAME_SCALE = 6f;
			}
			Gameplay.SIZE = (int) (Gameplay.DEFAULT_SIZE * Gameplay.CURRENT_GAME_SCALE);
		} else if (mouseWheel < 0) {// mouse wheel down
			Gameplay.CURRENT_GAME_SCALE *= 0.9f;
			if (Gameplay.CURRENT_GAME_SCALE < Gameplay.MAX_GAME_SCALE) {
				Gameplay.CURRENT_GAME_SCALE = Gameplay.MAX_GAME_SCALE;
			}
			Gameplay.SIZE = (int) (Gameplay.DEFAULT_SIZE * Gameplay.CURRENT_GAME_SCALE);
		}
		return true;
	}

	public void gameEndActions() {
		TowerDefense.writeScoreToFile(this.game.getGameplay().getPlayer().getName(), this.game.getGameplay().getPlayer().getScore());
		this.game.resetScores();
		this.game.getMenu().setDisableTextField(false);

		this.game.setMode(TowerDefense.MODE_MENU);
	}
}
