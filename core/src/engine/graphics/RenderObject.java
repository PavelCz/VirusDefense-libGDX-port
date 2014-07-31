package engine.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class RenderObject {

	public abstract void draw(float x, float y, float globalScale, SpriteBatch batch);

}
