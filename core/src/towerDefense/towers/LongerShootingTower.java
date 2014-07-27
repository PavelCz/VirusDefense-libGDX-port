package towerDefense.towers;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import towerDefense.Gameplay;
import engine.Enemy;
import engine.graphics.Sprite;

public class LongerShootingTower extends ShootingTower {
	private final int shootingDuration;
	private Enemy currentlyAttacking = null;
	private Graphics graphics;
	private boolean attacking;

	public LongerShootingTower(float x, float y, Sprite sprite, Gameplay game, float shootingInterval, float damage,
			int shootingDuration, Graphics graphics) {
		super(x, y, sprite, game, shootingInterval, damage);
		this.attacking = false;
		this.shootingDuration = shootingDuration;
		this.delta = 0;
		this.name = "Erythrozyt";
		this.graphics = graphics;
	}

	@Override
	public void update(int delta) {
		if (this.building) {
			this.buildingTimer -= delta;
			if (this.buildingTimer <= 0) {
				this.building = false;
			}
		}

		this.delta -= delta;
		if (this.currentlyAttacking != null) {
			this.shootAt(this.currentlyAttacking);
			if (this.currentlyAttacking.getHealth() <= 0) {
				this.currentlyAttacking = null;
				this.delta = (int) this.shootingInterval;
			}
		}

		if (this.delta <= 0) {
			Enemy enemy = this.getEnemyInRange();
			if (enemy != null) {
				if (this.currentlyAttacking == null) {
					this.currentlyAttacking = enemy;
					this.delta = this.shootingDuration;
					this.game.getSoundHandler().play("shotT1");
				} else {
					this.currentlyAttacking = null;
					this.delta = (int) this.shootingInterval;
				}
			}
		}

	}

	@Override
	public Tower clone() {
		return new LongerShootingTower(this.x, this.y, this.sprite.clone(), this.game, this.shootingInterval, this.damage,
				this.shootingDuration, this.graphics);
	}

	protected void shootAt(Enemy enemy) {
		if (this.inRange(enemy)) {
			this.attacking = true;
			enemy.setHealth(enemy.getHealth() - this.damage);
			if (enemy.getHealth() <= 0) {
				if (!enemy.isDead()) {
					this.game.getPlayer().addMoney(enemy.getMoney());
					this.game.getPlayer().addScore(enemy.getMoney() * 5);
					enemy.setDead();
				}
			}

		} else {
			this.attacking = false;
		}
	}

	@Override
	public void draw() {
		super.draw();
		if (this.attacking && this.currentlyAttacking != null) {
			this.graphics.setColor(Color.green);
			this.graphics.drawLine((this.x * Gameplay.DEFAULT_SIZE + Gameplay.DEFAULT_SIZE / 2) * Gameplay.CURRENT_GAME_SCALE
					- Gameplay.getCameraX(), (this.y * Gameplay.DEFAULT_SIZE + Gameplay.DEFAULT_SIZE / 2)
					* Gameplay.CURRENT_GAME_SCALE - Gameplay.getCameraY(), (this.currentlyAttacking.getX())
					* Gameplay.CURRENT_GAME_SCALE - Gameplay.getCameraX(), (this.currentlyAttacking.getY())
					* Gameplay.CURRENT_GAME_SCALE - Gameplay.getCameraY());
			this.graphics.setColor(Color.white);
		}

	}
}
