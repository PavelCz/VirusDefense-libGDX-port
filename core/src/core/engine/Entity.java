package core.engine;

public abstract class Entity {
	protected float x;
	protected float y;

	public Entity(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

}
