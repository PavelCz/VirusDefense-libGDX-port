package engine;

import engine.graphics.OwnSprite;

public class TestEntity extends RotatableEntity implements Drawable {

	private OwnSprite sprite;

	public TestEntity(float x, float y, float rotation, String spritePath) {
		super(x, y, rotation);

		this.sprite = new OwnSprite(spritePath);
	}

	@Override
	public void draw() {
		this.sprite.draw(this.x, this.y, 1);

	}

	@Override
	public void rotate(float degrees) {
		this.rotation += this.rotation;

	}

}
