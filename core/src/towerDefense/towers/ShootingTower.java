package towerDefense.towers;

import towerDefense.Gameplay;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import engine.Enemy;
import engine.graphics.OwnSprite;

public class ShootingTower extends Tower {
	protected int delta;

	public ShootingTower(float x, float y, OwnSprite sprite, Gameplay game, float shootingInterval, float damage) {
		super(x, y, 100, 128, damage, game, shootingInterval);

		this.sprite = sprite;
		this.shootingInterval = shootingInterval;
		this.delta = (int) this.shootingInterval;
	}

	@Override
	public Tower clone() {
		return new ShootingTower(this.x, this.y, this.sprite.clone(), this.game, this.shootingInterval, this.damage);
	}

	@Override
	public void draw(SpriteBatch batch) {
		if (this.building) {
			float scale = (this.buildingTime - this.buildingTimer) / this.buildingTime;
			float size = (Gameplay.DEFAULT_SIZE - this.sprite.getWidth() * scale) / 2;
			//this.sprite.draw((this.x * Gameplay.DEFAULT_SIZE + size) ,
			//		(this.y * Gameplay.DEFAULT_SIZE + size), Gameplay.CURRENT_GAME_SCALE * scale, batch);
			this.sprite.draw((this.x + size) ,
					(this.y + size), batch);
		} else {
			this.sprite.draw(this.x * Gameplay.SIZE, this.y * Gameplay.SIZE, batch);
		}
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		this.delta -= delta;
		if (this.delta <= 0) {
			this.delta = (int) this.shootingInterval;
			this.shoot();
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
					enemy.setHealth(enemy.getHealth() - this.damage);
					if (enemy.getHealth() <= 0) {
						this.game.getPlayer().addMoney(enemy.getMoney());
						this.game.getPlayer().addScore(enemy.getMoney() * 5);
					}
					done = true;
					this.game.getSoundHandler().play("shotT1");
				}
			}
		}

	}

}
