package engine.projectiles;

import towerDefense.Gameplay;
import engine.Drawable;
import engine.Enemy;
import engine.MyVector2f;
import engine.graphics.AnimatedSprite;

public class Bomb extends Projectile implements Drawable {
	private AnimatedSprite animatedSprite;

	public Bomb(float x, float y, int bombRadius, float damage, Gameplay game, float enemyX, float enemyY) {
		super(x, y);
		this.Radius = bombRadius;
		this.damage = damage;
		this.game = game;
		this.targetX = enemyX;
		this.targetY = enemyY;
		this.speed = 0.2f;
		this.velocity = new MyVector2f(enemyX - x, enemyY - y);
		float distance = this.velocity.getLength();
		this.velocity.setLength(this.speed);
		String[] animation = new String[20];
		for (int i = 0; i <= 8;) {
			animation[i] = "shoot/Frame000" + ++i + ".png";
		}
		for (int i = 9; i <= 19;) {
			animation[i] = "shoot/Frame00" + ++i + ".png";
		}

		float durationToTarget = distance / this.speed;
		float frames = durationToTarget / 20; // the number of milliseconds before the next frame of the animation is used
		this.animatedSprite = new AnimatedSprite(animation, 0.25f, (int) frames);
	}

	@Override
	public void update(int delta) {
		this.x += this.velocity.getX() * delta;
		this.y += this.velocity.getY() * delta;

		if (this.velocity.getX() >= 0 && this.velocity.getY() >= 0) {
			if (this.x >= this.targetX || this.y >= this.targetY) {
				this.fire();
			}
		} else if (this.velocity.getX() <= 0 && this.velocity.getY() <= 0) {
			if (this.x <= this.targetX || this.y <= this.targetY) {
				this.fire();
			}
		} else if (this.velocity.getX() >= 0 && this.velocity.getY() <= 0) {
			if (this.x >= this.targetX || this.y <= this.targetY) {
				this.fire();
			}
		} else if (this.velocity.getX() <= 0 && this.velocity.getY() >= 0) {
			if (this.x <= this.targetX || this.y >= this.targetY) {
				this.fire();
			}
		}
		this.animatedSprite.updateAnimation(delta);

	}

	@Override
	public void draw() {
		this.animatedSprite.draw((this.x - this.animatedSprite.getCurrentSprite().getWidth() / 2) * Gameplay.CURRENT_GAME_SCALE
				- Gameplay.getCameraX(), (this.y - this.animatedSprite.getCurrentSprite().getWidth() / 2)
				* Gameplay.CURRENT_GAME_SCALE - Gameplay.getCameraY(), Gameplay.CURRENT_GAME_SCALE);
	}

	public void fire() {

		for (Enemy bombedEnemy : this.game.getEnemies()) {
			float bombedEnemyX = bombedEnemy.getX();
			float bombedEnemyY = bombedEnemy.getY();
			float bombedDeltaX = bombedEnemyX - this.x;
			float bombedDeltaY = bombedEnemyY - this.y;

			float bombDistance = (float) Math.sqrt(bombedDeltaX * bombedDeltaX + bombedDeltaY * bombedDeltaY);
			if (bombDistance < this.Radius + bombedEnemy.getRadius()) {
				bombedEnemy.setHealth(bombedEnemy.getHealth() - this.damage);
				if (bombedEnemy.getHealth() <= 0) {
					this.game.getPlayer().addMoney(bombedEnemy.getMoney());
					this.game.getPlayer().addScore(bombedEnemy.getMoney() * 5);
				}
			}
		}
		this.game.getSoundHandler().play("explode");
		this.game.getProjectiles().remove(this);

	}

}
