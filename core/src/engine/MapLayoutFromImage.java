package engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

// @formatter:off
/**
 * @author Pavel
 * 
 *         Color:	Black		Red			White			Green
 *         RGB:		(0,0,0)		(1,0,0)		(1,1,1)			(0,1,0)
 *         Short:	Path		Start		T-Area			NoBuild
 *         Rule:	Only for 	starting	Build Towers	Neither Towers
 *         			Enemies		point		here			nor enemies
 *         Number:	0			(0)			1				2
 *         (in path Array)
 *         
 *         the starting point can only be connected to one path
 *
 */
// @formatter:on
public class MapLayoutFromImage {
	private Color[][] colors;
	private int[][] path; // 0 = Pfad, 1 = TowerYes, 2 = TowerNo
	private Waypoint startingPoint;

	public MapLayoutFromImage(String imagePath) {

		Pixmap pm = new Pixmap(Gdx.files.internal(imagePath));
		this.setColorArray(pm);
		pm.dispose();
		this.setPath();
		this.setWaypoints();

	}

	private void setColorArray(Pixmap pm) {
		this.colors = new Color[pm.getHeight()][pm.getWidth()];
		for (int y = 0; y < this.colors.length; ++y) {
			for (int x = 0; x < this.colors[0].length; ++x) {
				// this.colors[y][x] = this.image.getColor(x, y);
				Color c = new Color();
				// Color.rgba8888ToColor(c, pm.getPixel(x, y)); this also works
				c.set(pm.getPixel(x, y));
				// this.colors[y][x] = c;
				this.colors[this.colors.length - y - 1][x] = c; // this flips the array, so origin is in bottom left
			}
		}
	}

	/**
	 * generates the path from the image and gets the coordinates of the starting point for enemies
	 */
	private void setPath() {
		this.path = new int[this.colors.length][this.colors[0].length];
		for (int y = 0; y < this.colors.length; ++y) {
			for (int x = 0; x < this.colors[0].length; ++x) {
				Color currentColor = this.colors[y][x];
				if (this.isGreen(currentColor)) {
					this.path[y][x] = 2;

				} else if (this.isWhite(currentColor)) {
					this.path[y][x] = 1;
				} else {
					this.path[y][x] = 0;
					if (this.isRed(currentColor)) {
						this.startingPoint = new Waypoint(x, y);
					} else { // currentColor has no blue, no green, no red value => Path
						//
					}
				}
			}
		}

	}

	private void setWaypoints() {
		// Waypoint flippedStartingPoint = new Waypoint(this.startingPoint.getX(), this.path.length - this.startingPoint.getY());
		// this.startingPoint = flippedStartingPoint;
		int currentX = (int) this.startingPoint.getX();
		int currentY = (int) this.startingPoint.getY();
		int[][] path = this.getPath().clone();

		int relativePositionOfNextPath = this.relativePositionOfNextCoordinate(path, currentX, currentY);
		this.startingPoint.setDirection(relativePositionOfNextPath);

		int relativePositionOfPreviousPath;
		relativePositionOfPreviousPath = relativePositionOfNextPath;

		while (currentX >= 0 && currentY >= 0 && currentX < this.path[0].length && currentY < this.path.length
				&& this.path[currentY][currentX] < 1) {
			path[currentY][currentX] = 5;
			relativePositionOfNextPath = this.relativePositionOfNextCoordinate(path, currentX, currentY);
			if (relativePositionOfNextPath != relativePositionOfPreviousPath) {
				this.startingPoint.add(new Waypoint(currentX, currentY, relativePositionOfNextPath));
			}
			if (relativePositionOfNextPath == Waypoint.RIGHT) {
				++currentX;
			} else if (relativePositionOfNextPath == Waypoint.DOWN) {
				++currentY;
			} else if (relativePositionOfNextPath == Waypoint.LEFT) {
				--currentX;
			} else if (relativePositionOfNextPath == Waypoint.UP) {
				--currentY;
			} else {// == 6
				currentX = -1; // break
			}

			relativePositionOfPreviousPath = relativePositionOfNextPath;

		}

	}

	private int relativePositionOfNextCoordinate(int[][] path, int currentX, int currentY) {
		if (currentX + 1 < path[0].length && path[currentY][currentX + 1] < 1) {
			return Waypoint.RIGHT;
		} else if (currentY + 1 < path.length && path[currentY + 1][currentX] < 1) {
			return Waypoint.DOWN;
		} else if (currentX - 1 >= 0 && path[currentY][currentX - 1] < 1) {
			return Waypoint.LEFT;
		} else if (currentY - 1 >= 0 && path[currentY - 1][currentX] < 1) {
			return Waypoint.UP;
		} else {

			return 5;
		}
	}

	public int[][] getPath() {
		// int[][] flippedPath = new int[this.path.length][this.path[0].length];
		// for (int i = 0; i < this.path.length; ++i) {
		// flippedPath[i] = this.path[this.path.length - i - 1];
		// }
		return this.path;
	}

	public Waypoint getStartingPoint() {
		return this.startingPoint;
	}

	public Color getColors(int x, int y) {
		return this.colors[y][x];
	}

	private boolean isRed(Color color) {
		if (color.r >= 0.8f && color.b <= 0.2f && color.g <= 0.2f) {
			return true;
		}
		return false;
	}

	private boolean isGreen(Color color) {
		if (color.r <= 0.2f && color.b <= 0.2f && color.g >= 0.8f) {
			return true;
		}
		return false;

	}

	private boolean isWhite(Color color) {
		if (color.r >= 0.8f && color.b >= 0.8f && color.g >= 0.8f) {
			return true;
		}
		return false;
	}

}
