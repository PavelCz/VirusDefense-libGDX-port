package engine;

public class Waypoint extends Entity {
	final static int RIGHT = 0;
	final static int UP = 1;
	final static int LEFT = 2;
	final static int DOWN = 3;
	private int nextDirection;
	private Waypoint nextWaypoint;

	public Waypoint(float x, float y, int nextDirection) {
		super(x, y);
		this.nextDirection = nextDirection;
		this.nextWaypoint = null;
	}

	/**
	 * a constructor with the default value RIGHT for direction
	 * 
	 * @param x
	 * @param y
	 */
	public Waypoint(float x, float y) {
		this(x, y, Waypoint.RIGHT);
	}

	public int getDirection() {
		return this.nextDirection;
	}

	public Waypoint getNextWaypoint() {
		return this.nextWaypoint;
	}

	public void add(Waypoint nextWaypoint) {
		if (this.nextWaypoint == null) {
			this.nextWaypoint = nextWaypoint;
		} else {
			this.nextWaypoint.add(nextWaypoint);
		}
	}

	public void setDirection(int direction) {
		this.nextDirection = direction;
	}

}
