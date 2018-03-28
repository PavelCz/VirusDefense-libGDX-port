package engine.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class RenderObject {

	public abstract void draw(float x, float y, SpriteBatch batch);

	public void draw(float x, float y, float scale, SpriteBatch batch) {
		this.draw(x,y,batch);
	}

}
