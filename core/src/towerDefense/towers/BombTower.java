package towerDefense.towers;

import towerDefense.Gameplay;
import engine.Enemy;
import engine.graphics.Sprite;
import engine.projectiles.Bomb;

public class BombTower extends Tower {
	protected int delta;
	protected final int shootingInterval;
	protected int bombRadius;
	protected float wobbleFactor;
	private boolean wobble = true;

	public BombTower(float x, float y, Sprite sprite, Gameplay game, int shootingInterval, float damage, int bombRadius) {
		super(x, y, 100, 150, damage, game, shootingInterval);
		this.sprite = sprite;
		this.shootingInterval = shootingInterval;
		this.delta = this.shootingInterval;
		this.bombRadius = bombRadius;
		this.name = "B-Lymphozyt";
	}

	@Override
	public void draw() {
		if (this.building) {
			float scale = (this.buildingTime - this.buildingTimer) / this.buildingTime;
			float size = (Gameplay.DEFAULT_SIZE - this.sprite.getWidth() * scale) / 2;
			this.sprite.draw((this.x * Gameplay.DEFAULT_SIZE + size - Gameplay.getCameraX()) * Gameplay.CURRENT_GAME_SCALE, (this.y
					* Gameplay.DEFAULT_SIZE + size - Gameplay.getCameraY())
					* Gameplay.CURRENT_GAME_SCALE, Gameplay.CURRENT_GAME_SCALE * scale);
		} else if (this.wobble) {
			float scale = this.wobbleFactor;
			float size = (Gameplay.DEFAULT_SIZE - this.sprite.getWidth() * scale) / 2;
			this.sprite.draw((this.x * Gameplay.DEFAULT_SIZE + size) * Gameplay.CURRENT_GAME_SCALE - Gameplay.getCameraX(), (this.y
					* Gameplay.DEFAULT_SIZE + size)
					* Gameplay.CURRENT_GAME_SCALE - Gameplay.getCameraY(), scale * Gameplay.CURRENT_GAME_SCALE);
		} else {
			this.sprite.draw(this.x * Gameplay.SIZE, this.y * Gameplay.SIZE, Gameplay.CURRENT_GAME_SCALE);
		}

	}

	@Override
	public void shoot() {
		boolean done = false;
		for (Enemy enemy : this.game.getEnemies()) {
			if (enemy != null && !done) {
				float enemyX = enemy.getX();
				float enemyY = enemy.getY();
				float deltaX = enemyX - (this.getX() * Gameplay.DEFAULT_SIZE + Gameplay.DEFAULT_SIZE / 2);
				float deltaY = enemyY - (this.getY() * Gameplay.DEFAULT_SIZE + Gameplay.DEFAULT_SIZE / 2);

				float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

				if (distance < this.radius + enemy.getRadius()) {
					Bomb b = new Bomb(this.x * Gameplay.DEFAULT_SIZE + Gameplay.DEFAULT_SIZE / 2, this.y * Gameplay.DEFAULT_SIZE
							+ Gameplay.DEFAULT_SIZE / 2, this.bombRadius, this.damage, this.game, enemyX, enemyY);
					this.game.getProjectiles().add(b);

					done = true;
					this.game.getSoundHandler().play("shotT2");

				}
			}
		}

	}

	@Override
	public void update(int delta) {
		if (this.building) {
			this.buildingTimer -= delta;
			if ((this.buildingTime - this.buildingTimer) / this.buildingTime >= this.wobbleFactor) {
				this.delta = this.buildingTimer;
				this.building = false;
			}
		}
		this.wobbleFactor = (float) ((Math.sin(this.delta / 155.0) + 5.5) / 8);
		this.delta -= delta;
		if (this.delta <= 0) {
			this.delta = this.shootingInterval;
			this.shoot();
		}
	}

	@Override
	public Tower clone() {
		return new BombTower(this.x, this.y, this.sprite.clone(), this.game, this.shootingInterval, this.damage, this.bombRadius);
	}

}
