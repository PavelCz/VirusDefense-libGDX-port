package towerDefense.towers;

import towerDefense.Gameplay;
import engine.Drawable;
import engine.Enemy;
import engine.Entity;
import engine.graphics.Sprite;

public abstract class Tower extends Entity implements Drawable {
	private int cost;
	protected int radius;
	protected String name;
	protected float damage;
	protected Gameplay game;
	protected Sprite sprite;
	protected boolean building = true;
	protected int buildingTimer = 150;
	protected final float buildingTime = buildingTimer;
	protected float shootingInterval;

	public Tower(float x, float y, int cost, int radius, float damage, Gameplay game, float shootingInterval) {
		super(x, y);

		this.cost = cost;
		this.radius = radius;
		this.damage = damage;
		this.game = game;
		this.shootingInterval=shootingInterval;

	}

	public abstract void shoot();

	public void update(int delta) {
		if (this.building) {
			this.buildingTimer -= delta;
			if (this.buildingTimer <= 0) {
				this.building = false;
			}
		}

	}

	protected boolean inRange(Enemy enemy) {
		if (enemy != null) {
			float enemyX = enemy.getX();
			float enemyY = enemy.getY();
			float deltaX = enemyX - (this.getX() * Gameplay.DEFAULT_SIZE + Gameplay.DEFAULT_SIZE / 2);
			float deltaY = enemyY - (this.getY() * Gameplay.DEFAULT_SIZE + Gameplay.DEFAULT_SIZE / 2);

			float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

			if (distance < this.radius + enemy.getRadius()) {
				return true;
			}

		}
		return false;

	}

	protected boolean inRangeOfAnyEnemy() {
		if (this.getEnemyInRange() != null) {
			return true;
		} else {
			return false;
		}
	}

	protected Enemy getEnemyInRange() {
		boolean done = false;
		for (Enemy enemy : this.game.getEnemies()) {
			if (enemy != null && !done) {
				float enemyX = enemy.getX();
				float enemyY = enemy.getY();
				float deltaX = enemyX - (this.getX() * Gameplay.DEFAULT_SIZE + Gameplay.DEFAULT_SIZE / 2);
				float deltaY = enemyY - (this.getY() * Gameplay.DEFAULT_SIZE + Gameplay.DEFAULT_SIZE / 2);

				float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

				if (distance < this.radius + enemy.getRadius()) {
					return enemy;
				}
			}
		}
		return null;
	}

	@Override
	public float getX() {
		return this.x;
	}

	@Override
	public float getY() {
		return this.y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public String getName() {
		return name;
	}
	public float getDamage() {
		return damage;
	}

	@Override
	public abstract Tower clone();

	public Sprite getSprite() {
		return this.sprite;
	}

	public int getRadius() {
		return this.radius;
	}

	public int getCost() {
		return this.cost;
	}

}
