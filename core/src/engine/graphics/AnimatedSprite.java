package engine.graphics;

import java.util.ArrayList;
import java.util.List;

public class AnimatedSprite extends RenderObject {
	private List<OwnSprite> sprites;
	private int startSpeed;
	private int speed;
	private int index;
	private OwnSprite currentSprite;

	public AnimatedSprite(String[] images, float scale, int speed) {
		this.sprites = new ArrayList<OwnSprite>();
		for (String string : images) {
			this.sprites.add(new OwnSprite(string, scale));

		}
		this.startSpeed = speed;
		this.speed = speed;
		this.index = 0;
		this.currentSprite = this.sprites.get(0);
	}

	public void updateAnimation(int delta) {
		this.speed -= delta;
		if (this.speed <= 0) {
			this.index += 1;
			this.index %= this.sprites.size();
			this.currentSprite = this.sprites.get(this.index);
			this.speed = this.startSpeed;
		}
	}

	@Override
	public void draw(float x, float y, float globalScale) {
		this.currentSprite.draw(x, y, globalScale);

	}

	public OwnSprite getCurrentSprite() {
		return this.currentSprite;
	}

}
