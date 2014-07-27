package engine.gui;

public abstract class GUI {
	protected float x, y;

	public GUI(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public abstract void draw();
}
