package engine.projectiles;

import towerDefense.Gameplay;
import engine.Drawable;
import engine.Enemy;
import engine.MyVector2f;
import engine.graphics.Sprite;

public class RocketFast extends Projectile implements Drawable {
	private Enemy enemy;

	public RocketFast(float x, float y, int bombRadius, float damage, Gameplay game, Enemy enemy) {
		super(x, y);
		this.Radius = bombRadius;
		this.damage = damage;
		this.game = game;
		this.speed = 0.15f;
		this.velocity = new MyVector2f(enemy.getX() - x, enemy.getY() - y);
		this.velocity.setLength(this.speed);
		this.renderObject = new Sprite("shoot/Frame0010.png", 0.25f);
		this.enemy = enemy;
	}

	@Override
	public void update(int delta) {
		this.velocity = new MyVector2f(this.enemy.getX() - this.x, this.enemy.getY() - this.y);
		this.velocity.setLength(this.speed);
		this.x += this.velocity.getX() * delta;
		this.y += this.velocity.getY() * delta;

		if (this.velocity.getX() >= 0 && this.velocity.getY() >= 0) {
			if (this.x >= this.enemy.getX() || this.y >= this.enemy.getY()) {
				this.fire();
			}
		} else if (this.velocity.getX() <= 0 && this.velocity.getY() <= 0) {
			if (this.x <= this.enemy.getX() || this.y <= this.enemy.getY()) {
				this.fire();
			}
		} else if (this.velocity.getX() >= 0 && this.velocity.getY() <= 0) {
			if (this.x >= this.enemy.getX() || this.y <= this.enemy.getY()) {
				this.fire();
			}
		} else if (this.velocity.getX() <= 0 && this.velocity.getY() >= 0) {
			if (this.x <= this.enemy.getX() || this.y >= this.enemy.getY()) {
				this.fire();
			}
		}

	}

	public void fire() {

		this.enemy.setHealth(this.enemy.getHealth() - this.damage);
		if (this.enemy.getHealth() <= 0) {
			this.game.getPlayer().addMoney(this.enemy.getMoney());
			this.game.getPlayer().addScore(this.enemy.getMoney() * 5);
		}

		this.game.getSoundHandler().play("explode");
		this.game.getProjectiles().remove(this);

	}

}
