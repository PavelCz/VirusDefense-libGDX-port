package engine.graphics;

import towerDefense.Gameplay;

public class PathTiler {
	private Sprite corner1, corner2, corner3, corner4, horizontal, vertical, top, bottom, left, right;
	private Sprite[][] tiles;

	public PathTiler(int[][] path) {
		float scale = 0.5f;
		this.corner1 = new Sprite("veins/ur.png", scale);
		this.corner2 = new Sprite("veins/br.png", scale);
		this.corner3 = new Sprite("veins/lb.png", scale);
		this.corner4 = new Sprite("veins/ul.png", scale);
		this.horizontal = new Sprite("veins/hori.png", scale);
		this.vertical = new Sprite("veins/verti.png", scale);
		this.top = new Sprite("veins/verti.png", scale);
		this.bottom = new Sprite("veins/verti.png", scale);
		this.left = new Sprite("veins/hori.png", scale);
		this.right = new Sprite("veins/hori.png", scale);
		this.tiles = new Sprite[path.length][path[0].length];

		for (int y = 0; y < path.length; ++y) {
			for (int x = 0; x < path[0].length; ++x) {
				if (path[y][x] == 5) {
					boolean above = false, below = false, left = false, right = false;
					if (y - 1 >= 0 && path[y - 1][x] == 5) {
						above = true;
					}
					if (y + 1 < path.length && path[y + 1][x] == 5) {
						below = true;
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

	public void render() {
		for (int y = 0; y < this.tiles.length; ++y) {
			for (int x = 0; x < this.tiles[0].length; ++x) {
				if (this.tiles[y][x] != null) {
					this.tiles[y][x].draw(x * Gameplay.SIZE - Gameplay.getCameraX(), y * Gameplay.SIZE - Gameplay.getCameraY(),
							Gameplay.CURRENT_GAME_SCALE);
				}
			}
		}
	}
}
