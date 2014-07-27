package towerDefense.towers;

import towerDefense.Gameplay;
import engine.Enemy;
import engine.graphics.Sprite;
import engine.projectiles.Rocket;

public class RocketTower extends Tower {
	protected int delta;
	protected float shootingInterval;
	protected int bombRadius;

	public RocketTower(float x, float y, Sprite sprite, Gameplay game, float shootingInterval, float damage, int bombRadius) {
		super(x, y, 100, 50, damage, game, shootingInterval);
		this.sprite = sprite;
		this.shootingInterval = shootingInterval;
		this.delta = (int) this.shootingInterval;
		this.bombRadius = bombRadius;
		this.name = "T-Lymphozyt";
	}

	@Override
	public void draw() {
		this.sprite.draw(this.x * Gameplay.SIZE - Gameplay.getCameraX(), this.y * Gameplay.SIZE - Gameplay.getCameraY(),
				Gameplay.CURRENT_GAME_SCALE);

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
					Rocket r = new Rocket(this.x * Gameplay.DEFAULT_SIZE + Gameplay.DEFAULT_SIZE / 2, this.y * Gameplay.DEFAULT_SIZE
							+ Gameplay.DEFAULT_SIZE / 2, this.bombRadius, this.damage, this.game, enemy);
					this.game.getProjectiles().add(r);

					done = true;
					this.game.getSoundHandler().play("shotT2");

				}
			}
		}

	}

	@Override
	public void update(int delta) {
		this.delta -= delta;
		if (this.delta <= 0) {
			this.delta = (int) this.shootingInterval;
			this.shoot();
		}
	}

	@Override
	public Tower clone() {
		return new RocketTower(this.x, this.y, this.sprite.clone(), this.game, this.shootingInterval, this.damage, this.bombRadius);
	}

}
