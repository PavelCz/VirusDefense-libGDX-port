package engine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import engine.graphics.OwnSprite;
import engine.graphics.PathTiler;

public class MapLayout {
	private int[][] path;
	private PathTiler pathTiler;
	private Waypoint waypoints;
	private OwnSprite picture;
	private TextureRegionDrawable previewPicture;
	private int tileLength;
	private int numberTilesWidth, numberTilesHeight;

	public MapLayout(String mapLayoutPath, int tileLength, String picture) {
		MapLayoutFromImage mapLayout = new MapLayoutFromImage("data/files/maps/" + mapLayoutPath);
		this.picture = new OwnSprite(picture, 1.2f);
		this.previewPicture = new TextureRegionDrawable(new TextureRegion(new Texture("data/graphics/" + picture)));
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

	public OwnSprite getPicture() {
		return this.picture;
	}

	public Drawable getPreviewPictureDrawable() {
		return this.previewPicture;
	}

	public void renderPath(SpriteBatch batch) {
		this.pathTiler.render(batch);
	}

}
