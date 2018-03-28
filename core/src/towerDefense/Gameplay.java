package towerDefense;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import engine.graphics.*;
import towerDefense.towers.BombTower;
import towerDefense.towers.LongerShootingTower;
import towerDefense.towers.RocketFastTower;
import towerDefense.towers.RocketTower;
import towerDefense.towers.Tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import engine.Enemy;
import engine.EnemyTypeHandler;
import engine.GameComponent;
import engine.Level;
import engine.MyVector2f;
import engine.Player;
import engine.Waypoint;
import engine.gui.Healthbar;
import engine.gui.TowerButton;
import engine.projectiles.Projectile;

/**
 * @author Pavel
 */
public class Gameplay extends GameComponent {
    private OrthographicCamera gameCamera;
    private float cameraWidth, cameraHeight;
    private Healthbar h;
    // private static Camera camera;
    private float height, width;
    private ConcurrentLinkedQueue<Enemy> enemies;
    private boolean debugMode;
    private int passedMilliseconds;
    private int mode;
    private Level currentLevel;
    private int currentTileLength;
    private Tower[][] towers;
    private ButtonGroup<ImageButton> towerButtonGroup;
    private Tower currentTower;
    private Player player;
    private Label playerName;
    private Label numberLives;
    private Label moneyAmount;
    private Label score;
    private Label towerInfo;
    private Label towerName;
    private boolean currentTowerPlaceable;
    private float towerShadowX, towerShadowY;
    protected ConcurrentLinkedQueue<Projectile> projectiles;

    public static float MAX_GAME_SCALE;

    private Label passedTime;
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
        //Gdx.input.setInputProcessor(this);
        this.currentLevel = level;
        this.init();
    }

    @Override
    public void init() {
        super.init();

        // this.gameCamera.translate(1024 / 2, 768 / 2);
        // this.gameCamera.zoom = 2;
        this.h = new Healthbar(0, 0, 0, 30, 7);
        this.currentLevel.setGame(this);
        this.initDefaults();
        // Gameplay.camera = new Camera(0, 0, this);
        this.currentTileLength = Gameplay.DEFAULT_SIZE;
        this.height = Gameplay.DEFAULT_SIZE * this.getVerticalTiles();
        this.width = Gameplay.DEFAULT_SIZE * this.getHorizontalTiles();

        // Set Constants:

        Gameplay.INTERFACE_START_X = TowerDefense.getWidth() - 3 * 64;
        this.cameraWidth = Gameplay.INTERFACE_START_X; // (832)
        this.cameraHeight = 768;

        this.gameCamera = new OrthographicCamera(this.cameraWidth, this.cameraHeight);
        // this.game.viewport = new ScreenViewport(this.gameCamera);
        this.game.viewport.setCamera(this.gameCamera);
        // this.game.viewport.setWorldWidth(Gameplay.INTERFACE_START_X);
        float scale1 = Gameplay.INTERFACE_START_X / this.width;
        float scale2 = TowerDefense.getHeight() / this.height;
        Gameplay.MAX_GAME_SCALE = Math.max(scale1, scale2);
        Gameplay.SIZE = (int) (64);

        // The grey HUD field on the right side of the screen containing the buttons
        Image interfaceBackground = new Image(new Texture("data/graphics/Interface1.png"));
        interfaceBackground.setPosition(Gameplay.INTERFACE_START_X, 0);
        this.addActor(interfaceBackground);
        this.towers = new Tower[this.getVerticalTiles()][this.getHorizontalTiles()];

        // add all objects that need to be drawn to the respectable arrays
        // entities

        this.projectiles = new ConcurrentLinkedQueue<Projectile>();

        this.initButtons();

        //
        this.initGUI();

    }

    private void initButtons() {
        int offset = 20;
        String imagePath = "data/graphics/";

        ImageButton buyTowerButton0 = createNewTowerButton(new LongerShootingTower(0, 0, new OwnSprite
                ("tower/Tower2.png", 0.5f), this, 400, 0.16f, 400));
        buyTowerButton0.setX(Gameplay.INTERFACE_START_X);
        buyTowerButton0.setY(TowerDefense.getHeight() - 4 * 64  + offset);
        this.addActor(buyTowerButton0);

        ImageButton buyTowerButton1 = createNewTowerButton(new BombTower(0, 0, new OwnSprite("tower/t1n.png", 0.5f),
                this, 1500, 15f, 50));
        buyTowerButton1.setX(Gameplay.INTERFACE_START_X);
        buyTowerButton1.setY(TowerDefense.getHeight() - 5 * 64 + offset);
        this.addActor(buyTowerButton1);

        ImageButton buyTowerButton2 = createNewTowerButton(new RocketTower(0, 0, new OwnSprite("tower/t1.png", 0.5f),
                this, 200, 15f, 50));
        buyTowerButton2.setX(Gameplay.INTERFACE_START_X);
        buyTowerButton2.setY(TowerDefense.getHeight() - 6 * 64  + offset);
        this.addActor(buyTowerButton2);

        ImageButton buyTowerButton3 = createNewTowerButton(new RocketFastTower(0, 0, new OwnSprite
                ("tower/roteBlutk_klein.png", 0.5f), this, 1000, 20f));
        buyTowerButton3.setX(Gameplay.INTERFACE_START_X + 64);
        buyTowerButton3.setY(TowerDefense.getHeight() - 4 * 64  + offset);
        this.addActor(buyTowerButton3);

        // Put all tower buying buttons in one button group
        this.towerButtonGroup = new ButtonGroup<ImageButton>();
        // radio button- like functionality, pressing a tower button releases all others
        this.towerButtonGroup.setMaxCheckCount(1);
        this.towerButtonGroup.setMinCheckCount(0);
        this.towerButtonGroup.add(buyTowerButton0);
        this.towerButtonGroup.add(buyTowerButton1);
        this.towerButtonGroup.add(buyTowerButton2);
        this.towerButtonGroup.add(buyTowerButton3);
    }

    private ImageButton createNewTowerButton(Tower tower) {
        // Get the default skin for a "toggle" ImageButton
        ImageButton.ImageButtonStyle defaultStyle = this.game.getSkin().get("toggle", ImageButton.ImageButtonStyle
                .class);
        // The way libGDX Image buttons work, they have either a style or an image, with a few changes in the uiskin
        // .json
        // they can have both, now however, the image is shared by all instances of a button with the same style
        // That is why I create a new style that copies the attributes from the default style except for the image
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.checked = defaultStyle.down;
        buttonStyle.up = defaultStyle.up;
        buttonStyle.down = defaultStyle.down;
        buttonStyle.imageUp = new SpriteDrawable(tower.getSprite().getGDXSprite());
        buttonStyle.imageDown = buttonStyle.imageUp;

        ImageButton newTowerButton = new TowerButton(tower, this, buttonStyle);
        newTowerButton.setWidth(64);
        newTowerButton.setHeight(64);
        return newTowerButton;
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
        float guiTileSize = 64;
        float textHeight = 20;
        float guiX = 3 ;

        float cursorXStart = Gameplay.INTERFACE_START_X + guiX;
        float cursorYStart = 3 * guiTileSize;
        float cursorX = cursorXStart;
        float cursorY = cursorYStart;
        Label.LabelStyle ls = this.game.getLabelStyle();
        this.playerName = new Label("Player: " + this.player.getName(), ls);
        this.playerName.setPosition(cursorX, cursorY);
        this.addActor(this.playerName);
        cursorY -= textHeight;
        Label livesText = new Label("Lives: ", ls);
        livesText.setPosition(cursorX, cursorY);
        this.addActor(livesText);
        cursorX += livesText.getWidth();
        this.numberLives = new Label("" + this.player.getLives(), ls);
        this.numberLives.setPosition(cursorX, cursorY);
        this.addActor(numberLives);
        cursorX = cursorXStart;
        cursorY -= textHeight;
        Label moneyText = new Label("Money: ",ls);
        moneyText.setPosition(cursorX,cursorY);
        this.addActor(moneyText);
        cursorX += moneyText.getWidth();
        this.moneyAmount = new Label("" + this.player.getMoney(), ls);
        this.moneyAmount.setPosition(cursorX, cursorY);
        this.addActor(this.moneyAmount);
        cursorX = cursorXStart;
        cursorY -= textHeight;
        Label scoreText = new Label ("Score: ", ls);
        scoreText.setPosition(cursorX, cursorY);
        this.addActor(scoreText);
        cursorX += scoreText.getWidth();
        this.score = new Label("" + this.player.getScore(), ls);
        this.score.setPosition(cursorX, cursorY);
        this.addActor(this.score);
        this.towerName = new Label("", ls);
        this.towerName.setPosition(Gameplay.INTERFACE_START_X + guiTileSize, TowerDefense.getHeight() - 64);
        this.addActor(this.towerName);
        this.towerInfo = new Label("", ls);
        this.towerInfo.setPosition(Gameplay.INTERFACE_START_X, guiTileSize);
        this.addActor(this.towerInfo);
        this.passedTime = new Label(this.passedTimeToString(), ls);
        this.passedTime.setPosition(Gameplay.INTERFACE_START_X + guiX, 0);
        this.addActor(this.passedTime);
    }

    @Override
    public void render(SpriteBatch batch) {
        this.gameCamera.update();
        Matrix4 projectionBuffer = batch.getProjectionMatrix().cpy();
        Matrix4 transformBuffer = batch.getTransformMatrix().cpy();
        // super.render(batch);
        // Gdx.gl.glViewport(0, 0, (int) this.cameraWidth, (int) this.cameraHeight);
        batch.setProjectionMatrix(this.gameCamera.combined);

        this.drawBackground(batch);
        this.currentLevel.renderPath(batch);

        this.renderEnemies(batch);
        this.renderTowers(batch);

        this.renderTowerShadow(batch);

        //batch.setProjectionMatrix(projectionBuffer);
        //batch.setTransformMatrix(transformBuffer);

        // Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), (int) this.cameraHeight);
        this.renderGUI(batch);

        for (Projectile projectiles : this.projectiles) {
            projectiles.draw(batch);
        }
        if (this.currentTower != null) {
            this.currentTower.getSprite().draw(INTERFACE_START_X, TowerDefense.getHeight() - 80,
                    batch);
            this.towerName.setText(this.currentTower.getName());
            this.towerInfo.setText("Radius: " + this.currentTower.getRadius() + "\nKosten: " + this.currentTower
                    .getCost()
                    + "\nSchaden: " + this.currentTower.getDamage());
        } else {
            this.towerInfo.setText("");
            this.towerName.setText("");
        }

        this.draw();

    }

    protected void renderGUI(SpriteBatch batch) {
        this.renderHealthBars(batch);
        this.renderDebug(batch);

        if (this.mode == 1) {
            new OwnSprite("You Win.png").draw(0, 0,  batch);
        } else if (this.mode == -1) {
            new OwnSprite("Game Over.png").draw(0, 0,  batch);
        }
        // TODO: re-implement showing tower radius
        // for (int i = 0; i < this.towers.length; ++i) {
        // for (int j = 0; j < this.towers[0].length; ++j) {
        // if (this.towers[i][j] != null) {
        // Tower currentTower = this.towers[i][j];
        // new SlickUnfilledEllipse(graphics, currentTower.getRadius() * 2, currentTower.getRadius() * 2, Color
        // .white).draw(
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
            float barX = (enemy.getX() - barLength / 2);
            float barY = (enemy.getY() - Gameplay.DEFAULT_SIZE / 2);
            this.h.setX((enemy.getX() - barLength / 2));
            this.h.setY((enemy.getY() - Gameplay.DEFAULT_SIZE / 2) );
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
                new LibGDXRectangle(SIZE , SIZE ,
                        Color.GREEN, ShapeRenderer.ShapeType.Line).draw(
                        this.towerShadowX, this.towerShadowY, batch);
            } else {
                new LibGDXRectangle(SIZE , SIZE ,
                        Color.RED, ShapeRenderer.ShapeType.Line).draw(
                        this.towerShadowX, this.towerShadowY, batch);
                sprite.setAlpha(0.1f);
                sprite.setColor(1f, 0, 0);

            }

            sprite.draw(this.towerShadowX, this.towerShadowY,  batch);
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
                new LibGDXEllipse(enemy.getRadius() * 2, enemy.getRadius() * 2, Color.BLUE, ShapeRenderer
                        .ShapeType.Line).draw((enemy
                                .getX())
                                , (enemy.getY()) ,
                        batch);
            }

            // create a black box that the FPS are visible
            new LibGDXRectangle(100, 20, Color.BLACK, ShapeRenderer.ShapeType.Filled).draw(5, 10, batch);
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
        super.update(originalDelta);
        // }
    }

    private void updateTowerShadow() {
        if (this.currentTower != null && this.getMode() == 0) {

            float x = this.getMouseX();
            float y = this.getMouseY();
            // old version of shadow Coordinates, with pixel accurate
            // coordinates
            // this.towerShadowX = (int) (input.getMouseX() -
            // this.currentTower.getSprite().getWidth() / 2);
            // this.towerShadowY = (int) (input.getMouseY() -
            // this.currentTower.getSprite().getHeight() / 2);

            // this.towerShadowX = this.gameCamera.position.x - this.width / 2 * this.gameCamera.zoom + TowerDefense
            // .getMouseX()
            // * this.gameCamera.zoom;
            // this.towerShadowY = TowerDefense.getMouseY() * this.gameCamera.zoom;
            int newX = (int) (x / Gameplay.SIZE);
            int newY = (int) (y / Gameplay.SIZE);
            this.towerShadowX = newX * Gameplay.SIZE;
            this.towerShadowY = newY * Gameplay.SIZE;
            // this.towerShadowX = newX * Gameplay.SIZE - Gameplay.getCameraX();
            // this.towerShadowY = newY * Gameplay.SIZE - Gameplay.getCameraY();
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

        // else if ((Gameplay.getCameraX() + cameraWidth) / Gameplay.CURRENT_GAME_SCALE > this.getHorizontalTiles()
        // * Gameplay.DEFAULT_SIZE) {
        // Gameplay.camera.setX((this.getHorizontalTiles() * Gameplay.DEFAULT_SIZE) * Gameplay.CURRENT_GAME_SCALE -
        // cameraWidth);
        //
        // } else if ((Gameplay.getCameraY() + cameraHeight) / Gameplay.CURRENT_GAME_SCALE > this.getVerticalTiles()
        // * Gameplay.DEFAULT_SIZE) {
        // Gameplay.camera.setY((this.getVerticalTiles() * Gameplay.DEFAULT_SIZE) * Gameplay.CURRENT_GAME_SCALE -
        // cameraHeight);
        //
        // }
        float xOrigin = Gdx.graphics.getWidth() / 2;
        float yOrigin = Gdx.graphics.getHeight() / 2;
        float rightBoundary = INTERFACE_START_X;
        float topBoundary = TowerDefense.getHeight();

        float interfaceWidth = TowerDefense.getWidth() - INTERFACE_START_X;
        float zoomedInterfaceWidth = interfaceWidth * this.gameCamera.zoom;

        float effectiveCameraWidth = (this.gameCamera.viewportWidth) * this.gameCamera.zoom;
        float effectiveCameraHeight = this.gameCamera.viewportHeight * this.gameCamera.zoom;

        float cameraWidth = effectiveCameraWidth / 2 - zoomedInterfaceWidth;
        float cameraHeight = effectiveCameraHeight / 2;

        float scrollSpeed = 0.5f;
        float scrollDistance = scrollSpeed * delta;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.gameCamera.translate(-scrollDistance, 0);

        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.gameCamera.translate(+scrollDistance, 0);

        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.gameCamera.translate(0, +scrollDistance);

        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.gameCamera.translate(0, -scrollDistance);

        }

        if (this.gameCamera.position.x - (cameraWidth + zoomedInterfaceWidth) < 0) { // limit camera left
            this.gameCamera.position.x = cameraWidth + zoomedInterfaceWidth;
        }
        if (this.gameCamera.position.x + cameraWidth >= rightBoundary) { // limit camera right
            this.gameCamera.position.x = rightBoundary - cameraWidth;
        }
        if (this.gameCamera.position.y + cameraHeight > topBoundary) { // limit camera bottom
            this.gameCamera.position.y = topBoundary - cameraHeight;
        }
        if (this.gameCamera.position.y - cameraHeight < 0) { // limit camera top
            this.gameCamera.position.y = cameraHeight;
        }

        if (this.debugMode) {
            this.debugKeyboardEvents(delta);
        }
        // this.gameCamera.position.x = MathUtils.clamp(this.gameCamera.position.x, effectiveCameraWidth / 2f,
        // this.gameCamera.viewportWidth - effectiveCameraWidth / 2f);
        // this.gameCamera.position.y = MathUtils.clamp(this.gameCamera.position.y, effectiveCameraHeight / 2f,
        // this.gameCamera.viewportHeight - effectiveCameraHeight / 2f);

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

            float x = this.getMouseX();
            float y = this.getMouseY();
            if (Gdx.input.justTouched()) { // just touched also true for right mouse button...
                if (Gdx.input.isButtonPressed(com.badlogic.gdx.Input.Buttons.LEFT)) { // ...therefore this if is needed
                    this.placeTower();
                }

            } else if (Gdx.input.isButtonPressed(com.badlogic.gdx.Input.Buttons.RIGHT)) {
                this.towerButtonGroup.uncheckAll();

            }
        }
    }

    private void placeTower() {
        // Vector2 vec = new Vector2(TowerDefense.getMouseX(), TowerDefense.getMouseY());
        // vec.mul(this.gameCamera.)
        // Vector3 vec = this.gameCamera.unproject(new Vector3(TowerDefense.getMouseX(), TowerDefense.getMouseY(), 0)
        // , 0, 0,
        // this.height,
        // this.width);
        // float x = vec.x;
        // float y = vec.y;
        float x = this.getMouseX();
        float y = this.getMouseY();

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
                this.towerButtonGroup.uncheckAll();

            } else {
                boolean mouseCollidesButton = false;
                if (!mouseCollidesButton) {
                    this.game.getSoundHandler().play("bad");
                }
            }

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

    public BackgroundTiles getMapBackground() {
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

    /*public StaticText getScore() {
        return this.score;
    }*/

    /*public void setScore(StaticText score) {
        this.score = score;
    }*/

    public void setPlayerName(String playerName) {
        this.player.setName(playerName);
        this.playerName.setText("Player: " + this.player.getName());
    }

    @Override
    public boolean scrolled(int amount) {
        super.scrolled(amount);
        // int mouseWheel = -amount;
        // if (mouseWheel > 0) { // mouse wheel up
        // Gameplay.CURRENT_GAME_SCALE *= 1.1f;
        // if (Gameplay.CURRENT_GAME_SCALE > 6) {
        // Gameplay.CURRENT_GAME_SCALE = 6f;
        // }
        // Gameplay.SIZE = (int) (Gameplay.DEFAULT_SIZE * Gameplay.CURRENT_GAME_SCALE);
        // } else if (mouseWheel < 0) {// mouse wheel down
        // Gameplay.CURRENT_GAME_SCALE *= 0.9f;
        // if (Gameplay.CURRENT_GAME_SCALE < Gameplay.MAX_GAME_SCALE) {
        // Gameplay.CURRENT_GAME_SCALE = Gameplay.MAX_GAME_SCALE;
        // }
        // Gameplay.SIZE = (int) (Gameplay.DEFAULT_SIZE * Gameplay.CURRENT_GAME_SCALE);
        // }

        this.gameCamera.zoom += amount / 8.0;
        if (this.gameCamera.zoom < 0.2f) {
            this.gameCamera.zoom = 0.2f;
        } else if (this.gameCamera.zoom > 1f) {
            this.gameCamera.zoom = 1f;
        }

        return true;
    }

    public void gameEndActions() {
        TowerDefense.writeScoreToFile(this.game.getGameplay().getPlayer().getName(), this.game.getGameplay()
                .getPlayer().getScore());
        this.game.resetScores();
        this.game.getMenu().setDisableTextField(false);

        this.game.setMode(TowerDefense.MODE_MENU);

        Gdx.input.setInputProcessor(this.game.getMenu().getStage());
    }

    public float getMouseX() {
        Vector3 vec = new Vector3(Gdx.input.getX(), Gdx.input.getX(), 0);
        this.gameCamera.unproject(vec);
        return vec.x;
    }

    public float getMouseY() {
        Vector3 vec = new Vector3(Gdx.input.getY(), Gdx.input.getY(), 0);
        this.gameCamera.unproject(vec);
        return vec.y;
    }
}
