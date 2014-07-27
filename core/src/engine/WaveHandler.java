package engine;

import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.GameContainer;

import towerDefense.Gameplay;
import towerDefense.TowerDefense;

public class WaveHandler {
	private LinkedList<Wave> waves;
	private Gameplay game;
	private int index = 0;
	private Wave currentWave;
	private int delta;
	private int timeBetweenWaves;
	private int timeBetweenEnemies = 500;
	private boolean done = false;

	public WaveHandler(Gameplay game, int timeBetweenWaves, String path) {
		this.game = game;
		this.waves = new LinkedList<Wave>();
		this.timeBetweenWaves = timeBetweenWaves;
		this.delta = this.timeBetweenWaves;
		List<String> lines = TextFileToString.getLines("waves/" + path);
		this.initWaves(lines);

	}

	private void initWaves(List<String> lines) {
		int i = 0;
		int numberEnemies;

		String s = lines.get(i);

		String[] parts = s.split(", ");
		numberEnemies = parts.length - 1;
		int[] ints = new int[numberEnemies];
		// for (int j = 0; j < ints.length; ++j) {
		// ints[j] = Integer.parseInt(parts[j + 1]);
		// }
		// this.addWave(new Wave(Integer.parseInt(parts[0]), ints));
		// ++i;

		for (; i < lines.size(); ++i) {
			s = lines.get(i);
			parts = s.split(", ");
			ints = new int[numberEnemies];
			for (int j = 0; j < ints.length; ++j) {
				ints[j] = Integer.parseInt(parts[j + 1]);

			}
			this.addWave(new Wave(Integer.parseInt(parts[0]), ints));

		}
	}

	public void addWave(Wave wave) {
		this.waves.add(wave);
	}

	private int calculateEnemy(Wave wave) {
		int n = (int) (Math.random() * 100);
		int[] waves = wave.getPercentages();
		int p = 100;
		for (int i = 0; i < waves.length; i++) {
			p = p - waves[i];
			if (n >= p) {
				return i;
			}
		}
		return -1;
	}

	public void update(int delta, GameContainer container) {
		this.delta -= delta;
		// the player defeated all the waves
		if (this.waves.isEmpty() && this.done) {
			TowerDefense.writeScoreToFile(this.game.game.getGameplay().getPlayer().getName(), this.game.game.getGameplay().getPlayer()
					.getScore());
			this.game.game.resetScores();
			this.game.game.setMode(TowerDefense.MODE_MENU);
			this.game.game.setWon(this.game.getPlayer().getScore(), this.game.getPlayer().getName());
		}
		if (this.game != null && this.game.getEnemies().isEmpty() && this.index <= 0) {
			if (!this.waves.isEmpty()) {
				this.index = this.waves.peek().getNumber();
				this.currentWave = this.waves.poll();
				this.delta = this.timeBetweenWaves;
			} else {
				this.done = true;
			}
		}
		if (this.delta <= 0) {
			this.delta = this.timeBetweenEnemies;
			if (this.index > 0) {
				// calculates the next enemy type, creates a new object with
				// that type and adds the object to the enemies of the game
				this.game.getEnemies().add(this.game.getEnemyTypes().createEnemy(this.calculateEnemy(this.currentWave)));
				this.index--;
			}
		}
	}

	public void setGame(Gameplay game) {
		this.game = game;

	}
}
