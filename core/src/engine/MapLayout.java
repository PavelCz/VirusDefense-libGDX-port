package engine;

import engine.graphics.PathTiler;
import engine.graphics.Sprite;

public class MapLayout {
	private int[][] path;
	private PathTiler pathTiler;
	private Waypoint waypoints;
	private Sprite picture;
	private int tileLength;
	private int numberTilesWidth, numberTilesHeight;

	public MapLayout(String mapLayoutPath,  int tileLength, String picture) {
		MapLayoutFromImage mapLayout = new MapLayoutFromImage("data/files/maps/" +mapLayoutPath);
		this.picture = new Sprite(picture, 1.2f);
		this.path = mapLayout.getPath();
		this.waypoints = mapLayout.getStartingPoint();
		this.numberTilesWidth = this.path[0].length;
		this.numberTilesHeight = this.path.length;
		this.tileLength = tileLength;
		this.pathTiler = new PathTiler(this.path);
	}

	public int[][] getPath() {
		return this.path;
	}

	public Waypoint getWaypoints() {
		return this.waypoints;
	}

	public int getTileLength() {
		return this.tileLength;
	}

	public int getNumberTilesWidth() {
		return this.numberTilesWidth;
	}

	public int getNumberTilesHeight() {
		return this.numberTilesHeight;
	}

	public Sprite getPicture() {
		return picture;
	}
	
	public void renderPath() {
		this.pathTiler.render();
	}
	
	

}
