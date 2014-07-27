package engine;


public abstract class RotatableEntity extends Entity {
	protected float rotation;

	public RotatableEntity(float x, float y) {
		super(x, y);

		this.rotation = 0;
	}

	public RotatableEntity(float x, float y, float rotation) {
		super(x, y);

		this.rotation = rotation;
	}

	public float getRotation() {
		return this.rotation;
	}

	public abstract void rotate(float degrees);

}
