package engine;

import towerDefense.Gameplay;
import engine.graphics.Sprite;

public class Enemy extends Entity implements Drawable {
	private float radius;
	private float health;
	private int maxHealth;
	private float speed;
	private Gameplay game;
	protected Sprite sprite;
	protected MyVector2f velocity;
	protected Waypoint waypoint;
	protected int direction;
	private int worth;
	private boolean dead = false;
	private boolean wobble = true;
	private double wobbleFactor;
	private int wobbleTimer = 0;

	private Enemy(int maxHealth, float speed, Sprite sprite, Waypoint startingWaypoint, Gameplay game, float radius, int worth) {
		super(startingWaypoint.getX() * Gameplay.DEFAULT_SIZE, startingWaypoint.getY() * Gameplay.DEFAULT_SIZE);
		this.health = maxHealth;
		this.maxHealth = maxHealth;
		this.speed = speed;
		this.sprite = sprite;
		this.waypoint = startingWaypoint;
		this.direction = this.waypoint.getDirection();
		this.newDirection();
		this.radius = radius;
		this.game = game;
		this.worth = worth;
		this.game.getSoundHandler().play("spawn");
	}

	public Enemy(EnemyType enemyType, Gameplay game) {
		this(enemyType.getHealth(), enemyType.getSpeed(), enemyType.getSprite(), game.getWaypoints(), game, enemyType.getRadius(),
				enemyType.getWorth());

	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void update(int delta) {
		if (this.wobble) {
			this.wobbleTimer += delta;
			this.wobbleFactor = (Math.sin(this.wobbleTimer / 200.0) + 6) / 8;
		}
		if (this.health > 0) {
			this.x += this.velocity.getX() * delta;
			this.y += this.velocity.getY() * delta;

			Waypoint previousWaypoint = this.waypoint;
			if (this.waypoint == null) {
				this.health = 0;
				this.game.reduceLives();
			} else if (this.direction == Waypoint.DOWN && this.y >= this.waypoint.getY() * Gameplay.DEFAULT_SIZE) {
				this.newDirection();
				this.normalizeCoordinates(previousWaypoint);
			} else if (this.direction == Waypoint.RIGHT && this.x >= this.waypoint.getX() * Gameplay.DEFAULT_SIZE) {
				this.newDirection();
				this.normalizeCoordinates(previousWaypoint);
			} else if (this.direction == Waypoint.UP && this.y <= this.waypoint.getY() * Gameplay.DEFAULT_SIZE) {
				this.newDirection();
				this.normalizeCoordinates(previousWaypoint);
			} else if (this.direction == Waypoint.LEFT && this.x <= this.waypoint.getX() * Gameplay.DEFAULT_SIZE) {
				this.newDirection();
				this.normalizeCoordinates(previousWaypoint);
			}
		} else {
			this.game.getSoundHandler().play("death");
			this.game.getEnemies().remove(this);
		}
	}

	private void newDirection() {
		if (this.waypoint.getDirection() == Waypoint.RIGHT) {
			this.velocity = new MyVector2f(this.speed, 0);
			this.direction = Waypoint.RIGHT;
		} else if (this.waypoint.getDirection() == Waypoint.UP) {
			this.velocity = new MyVector2f(0, -this.speed);
			this.direction = Waypoint.UP;
		} else if (this.waypoint.getDirection() == Waypoint.LEFT) {
			this.velocity = new MyVector2f(-this.speed, 0);
			this.direction = Waypoint.LEFT;
		} else if (this.waypoint.getDirection() == Waypoint.DOWN) {
			this.velocity = new MyVector2f(0, this.speed);
			this.direction = Waypoint.DOWN;
		}

		this.waypoint = this.waypoint.getNextWaypoint();
	}

	@Override
	public void draw() {
		if (this.health > 0) {
			if (this.wobble) {
				float scale = (float) this.wobbleFactor;
				float size = (Gameplay.DEFAULT_SIZE - this.sprite.getWidth() * scale) / 2;
				this.sprite.draw((this.x + size) * Gameplay.CURRENT_GAME_SCALE - Gameplay.getCameraX(), (this.y + size)
						* Gameplay.CURRENT_GAME_SCALE - Gameplay.getCameraY(), scale * Gameplay.CURRENT_GAME_SCALE);
			} else {
				this.sprite.draw((this.x) * Gameplay.CURRENT_GAME_SCALE, (this.y) * Gameplay.CURRENT_GAME_SCALE,
						Gameplay.CURRENT_GAME_SCALE);
			}
		}
	}

	private void normalizeCoordinates(Waypoint waypoint) {

		this.x = waypoint.getX() * Gameplay.DEFAULT_SIZE;
		this.y = waypoint.getY() * Gameplay.DEFAULT_SIZE;
		// if (this.direction == Waypoint.UP || this.direction == Waypoint.DOWN) {
		// } else {
		// this.y = (int) (this.y / Gameplay.DEFAULT_SIZE) * Gameplay.DEFAULT_SIZE;
		// }
	}

	@Override
	public float getX() {
		return (this.x + Gameplay.DEFAULT_SIZE / 2);
	}

	@Override
	public float getY() {
		return (this.y + Gameplay.DEFAULT_SIZE / 2);
	}

	public float getRadius() {
		return this.radius;
	}

	public float getHealth() {
		return this.health;
	}

	public int getMoney() {
		return this.worth;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public int getMaxHealth() {

		return this.maxHealth;
	}

	public boolean isDead() {
		return this.dead;
	}

	public void setDead() {
		this.dead = true;
	}
}
