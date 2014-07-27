package engine;

import java.util.ArrayList;
import java.util.List;

import towerDefense.Gameplay;

public class LevelHandler {
	private List<Level> levels;

	public LevelHandler() {
		this.levels = new ArrayList<Level>();
	}

	public void add(String path, Gameplay game) {
		this.levels.add(new Level("levels/" + path, game));
	}

	public Level get(int i) {
		return this.levels.get(i);
	}

	public int getLength() {
		return this.levels.size();
	}
}
