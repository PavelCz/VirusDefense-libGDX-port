package towerDefense;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class StartGame extends ApplicationAdapter {
	private static final float PLANE_JUMP_IMPULSE = 350;
	private static final float GRAVITY = -20;
	private static final float PLANE_VELOCITY_X = 200;
	private static final float PLANE_START_Y = 240;
	private static final float PLANE_START_X = 50;
	ShapeRenderer shapeRenderer;
	SpriteBatch batch;
	OrthographicCamera camera;
	OrthographicCamera uiCamera;
	Texture background;
	TextureRegion ground;
	float groundOffsetX = 0;
	TextureRegion ceiling;
	TextureRegion rock;
	TextureRegion rockDown;
	Animation plane;
	TextureRegion ready;
	TextureRegion gameOver;
	BitmapFont font;

	Vector2 planePosition = new Vector2();
	Vector2 planeVelocity = new Vector2();
	float planeStateTime = 0;
	Vector2 gravity = new Vector2();
	Array<Rock> rocks = new Array<Rock>();

	GameState gameState = GameState.Start;
	int score = 0;
	Rectangle rect1 = new Rectangle();
	Rectangle rect2 = new Rectangle();

	Music music;
	Sound explode;

	@Override
	public void create() {
		this.shapeRenderer = new ShapeRenderer();
		this.batch = new SpriteBatch();
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, 800, 480);
		this.uiCamera = new OrthographicCamera();
		this.uiCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.uiCamera.update();

		this.font = new BitmapFont(Gdx.files.internal("arial.fnt"));

		this.background = new Texture("background.png");
		this.ground = new TextureRegion(new Texture("ground.png"));
		this.ceiling = new TextureRegion(this.ground);
		this.ceiling.flip(true, true);

		this.rock = new TextureRegion(new Texture("rock.png"));
		this.rockDown = new TextureRegion(this.rock);
		this.rockDown.flip(false, true);

		Texture frame1 = new Texture("plane1.png");
		frame1.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		Texture frame2 = new Texture("plane2.png");
		Texture frame3 = new Texture("plane3.png");

		this.ready = new TextureRegion(new Texture("ready.png"));
		this.gameOver = new TextureRegion(new Texture("gameover.png"));

		this.plane = new Animation(0.05f, new TextureRegion(frame1), new TextureRegion(frame2), new TextureRegion(frame3),
				new TextureRegion(frame2));
		this.plane.setPlayMode(PlayMode.LOOP);

		this.music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		this.music.setLooping(true);
		this.music.play();

		this.explode = Gdx.audio.newSound(Gdx.files.internal("explode.wav"));

		this.resetWorld();
	}

	private void resetWorld() {
		this.score = 0;
		this.groundOffsetX = 0;
		this.planePosition.set(PLANE_START_X, PLANE_START_Y);
		this.planeVelocity.set(0, 0);
		this.gravity.set(0, GRAVITY);
		this.camera.position.x = 400;

		this.rocks.clear();
		for (int i = 0; i < 5; i++) {
			boolean isDown = MathUtils.randomBoolean();
			this.rocks
					.add(new Rock(700 + i * 200, isDown ? 480 - this.rock.getRegionHeight() : 0, isDown ? this.rockDown : this.rock));
		}
	}

	private void updateWorld() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		this.planeStateTime += deltaTime;

		if (Gdx.input.justTouched()) {
			if (this.gameState == GameState.Start) {
				this.gameState = GameState.Running;
			}
			if (this.gameState == GameState.Running) {
				this.planeVelocity.set(PLANE_VELOCITY_X, PLANE_JUMP_IMPULSE);
			}
			if (this.gameState == GameState.GameOver) {
				this.gameState = GameState.Start;
				this.resetWorld();
			}
		}

		if (this.gameState != GameState.Start)
			this.planeVelocity.add(this.gravity);

		this.planePosition.mulAdd(this.planeVelocity, deltaTime);

		this.camera.position.x = this.planePosition.x + 350;
		if (this.camera.position.x - this.groundOffsetX > this.ground.getRegionWidth() + 400) {
			this.groundOffsetX += this.ground.getRegionWidth();
		}

		this.rect1.set(this.planePosition.x + 20, this.planePosition.y, this.plane.getKeyFrames()[0].getRegionWidth() - 20,
				this.plane.getKeyFrames()[0].getRegionHeight());
		for (Rock r : this.rocks) {
			if (this.camera.position.x - r.position.x > 400 + r.image.getRegionWidth()) {
				boolean isDown = MathUtils.randomBoolean();
				r.position.x += 5 * 200;
				r.position.y = isDown ? 480 - this.rock.getRegionHeight() : 0;
				r.image = isDown ? this.rockDown : this.rock;
				r.counted = false;
			}
			this.rect2.set(r.position.x + (r.image.getRegionWidth() - 30) / 2 + 20, r.position.y, 20, r.image.getRegionHeight() - 10);
			if (this.rect1.overlaps(this.rect2)) {
				if (this.gameState != GameState.GameOver)
					this.explode.play();
				this.gameState = GameState.GameOver;
				this.planeVelocity.x = 0;
			}
			if (r.position.x < this.planePosition.x && !r.counted) {
				this.score++;
				r.counted = true;
			}
		}

		if (this.planePosition.y < this.ground.getRegionHeight() - 20
				|| this.planePosition.y + this.plane.getKeyFrames()[0].getRegionHeight() > 480 - this.ground.getRegionHeight() + 20) {
			if (this.gameState != GameState.GameOver)
				this.explode.play();
			this.gameState = GameState.GameOver;
			this.planeVelocity.x = 0;
		}
	}

	private void drawWorld() {
		this.camera.update();
		this.batch.setProjectionMatrix(this.camera.combined);
		this.batch.begin();
		this.batch.draw(this.background, this.camera.position.x - this.background.getWidth() / 2, 0);
		for (Rock rock : this.rocks) {
			this.batch.draw(rock.image, rock.position.x, rock.position.y);
		}
		this.batch.draw(this.ground, this.groundOffsetX, 0);
		this.batch.draw(this.ground, this.groundOffsetX + this.ground.getRegionWidth(), 0);
		this.batch.draw(this.ceiling, this.groundOffsetX, 480 - this.ceiling.getRegionHeight());
		this.batch.draw(this.ceiling, this.groundOffsetX + this.ceiling.getRegionWidth(), 480 - this.ceiling.getRegionHeight());
		this.batch.draw(this.plane.getKeyFrame(this.planeStateTime), this.planePosition.x, this.planePosition.y);
		this.batch.end();

		this.batch.setProjectionMatrix(this.uiCamera.combined);
		this.batch.begin();
		if (this.gameState == GameState.Start) {
			this.batch.draw(this.ready, Gdx.graphics.getWidth() / 2 - this.ready.getRegionWidth() / 2, Gdx.graphics.getHeight() / 2
					- this.ready.getRegionHeight() / 2);
		}
		if (this.gameState == GameState.GameOver) {
			this.batch.draw(this.gameOver, Gdx.graphics.getWidth() / 2 - this.gameOver.getRegionWidth() / 2, Gdx.graphics.getHeight()
					/ 2 - this.gameOver.getRegionHeight() / 2);
		}
		if (this.gameState == GameState.GameOver || this.gameState == GameState.Running) {
			this.font.draw(this.batch, "" + this.score, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 60);
		}
		this.batch.end();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		this.updateWorld();
		this.drawWorld();
	}

	static class Rock {
		Vector2 position = new Vector2();
		TextureRegion image;
		boolean counted;

		public Rock(float x, float y, TextureRegion image) {
			this.position.x = x;
			this.position.y = y;
			this.image = image;
		}
	}

	static enum GameState {
		Start, Running, GameOver
	}
}