package engine.projectiles;

import towerDefense.Gameplay;
import engine.Entity;
import engine.MyVector2f;
import engine.graphics.RenderObject;

public abstract class Projectile extends Entity {
	protected int Radius;
	protected float damage;
	protected Gameplay game;
	protected float targetX;
	protected float targetY;
	protected float speed;
	protected MyVector2f velocity;
	protected RenderObject renderObject;

	public Projectile(float x, float y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	public abstract void update(int delta);

	public void draw() {
		this.renderObject.draw((this.x - Gameplay.DEFAULT_SIZE / 2) * Gameplay.CURRENT_GAME_SCALE - Gameplay.getCameraX(),
				(this.y - Gameplay.DEFAULT_SIZE / 2) * Gameplay.CURRENT_GAME_SCALE - Gameplay.getCameraY(),
				Gameplay.CURRENT_GAME_SCALE);
	}

}
