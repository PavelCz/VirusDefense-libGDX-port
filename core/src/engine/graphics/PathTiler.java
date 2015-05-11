package engine.graphics;

import towerDefense.Gameplay;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PathTiler {
	private OwnSprite corner1, corner2, corner3, corner4, horizontal, vertical, top, bottom, left, right;
	private OwnSprite[][] tiles;

	public PathTiler(int[][] path) {
		float scale = 0.5f;
		this.corner1 = new OwnSprite("veins/ur.png", scale);
		this.corner2 = new OwnSprite("veins/br.png", scale);
		this.corner3 = new OwnSprite("veins/lb.png", scale);
		this.corner4 = new OwnSprite("veins/ul.png", scale);
		this.horizontal = new OwnSprite("veins/hori.png", scale);
		this.vertical = new OwnSprite("veins/verti.png", scale);
		this.top = new OwnSprite("veins/verti.png", scale);
		this.bottom = new OwnSprite("veins/verti.png", scale);
		this.left = new OwnSprite("veins/hori.png", scale);
		this.right = new OwnSprite("veins/hori.png", scale);
		this.tiles = new OwnSprite[path.length][path[0].length];

		for (int y = 0; y < path.length; ++y) {
			for (int x = 0; x < path[0].length; ++x) {
				if (path[y][x] == 5) {
					boolean above = false, below = false, left = false, right = false;
					if (y - 1 >= 0 && path[y - 1][x] == 5) {
						below = true;
					}
					if (y + 1 < path.length && path[y + 1][x] == 5) { // flipped above and below, because origin is now in bottom left

						above = true;
					}
					if (x - 1 >= 0 && path[y][x - 1] == 5) {
						left = true;
					}
					if (x + 1 < path[0].length && path[y][x + 1] == 5) {
						right = true;
					}

					if (below && !above && !right && !left) {
						this.tiles[y][x] = this.top;
					} else if (!below && above && !right && !left) {
						this.tiles[y][x] = this.bottom;
					} else if (!below && !above && right && !left) {
						this.tiles[y][x] = this.left;
					} else if (!below && !above && !right && left) {
						this.tiles[y][x] = this.right;
					} else if (below && above && !right && !left) {
						this.tiles[y][x] = this.vertical;
					} else if (!below && !above && right && left) {
						this.tiles[y][x] = this.horizontal;
					} else if (!below && above && right && !left) {
						this.tiles[y][x] = this.corner1;
					} else if (below && !above && right && !left) {
						this.tiles[y][x] = this.corner2;
					} else if (below && !above && !right && left) {
						this.tiles[y][x] = this.corner3;
					} else if (!below && above && !right && left) {
						this.tiles[y][x] = this.corner4;
					}

				}
			}
		}

	}

	public void render(SpriteBatch batch) {
		for (int y = 0; y < this.tiles.length; ++y) {
			for (int x = 0; x < this.tiles[0].length; ++x) {
				if (this.tiles[y][x] != null) {
					this.tiles[y][x].draw(x * Gameplay.SIZE, y * Gameplay.SIZE, Gameplay.CURRENT_GAME_SCALE, batch);
				}
			}
		}
	}
}
