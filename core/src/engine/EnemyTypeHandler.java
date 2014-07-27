package engine;

import java.util.ArrayList;
import java.util.List;

import towerDefense.Gameplay;

/**
 * Handles all enemy types. You can add a new enemy type. This Type will be saved at a specific index and later you can create an
 * Object with that number
 * 
 * @author Pavel
 */
public class EnemyTypeHandler {
	private List<EnemyType> enemyTypes;
	private Gameplay game;

	public EnemyTypeHandler(Gameplay game, String enemies) {
		this.enemyTypes = new ArrayList<EnemyType>();
		this.initEnemyTypes(TextFileToString.getLines("enemies/" + enemies));
		this.game = game;
	}

	private void initEnemyTypes(List<String> lines) {
		for (String line : lines) {
			String[] parts = line.split(", ");
			this.enemyTypes.add(new EnemyType(Integer.parseInt(parts[0]), Float.parseFloat(parts[1]), parts[2], Integer
					.parseInt(parts[3]), Integer.parseInt(parts[4]), Float.parseFloat(parts[5])));
		}

	}

	/**
	 * @param enemyType
	 *            adds a new EnemyType to your EnemyTypes
	 */
	public void add(EnemyType enemyType) {
		this.enemyTypes.add(enemyType);
	}

	/**
	 * @param type
	 *            the number of the EnemyType
	 * @return return a new Enemy with the properties that the specified EnemyType has
	 */
	public Enemy createEnemy(int type) {
		return this.enemyTypes.get(type).createEnemy(this.game);
	}

	public void setGame(Gameplay game) {
		this.game = game;
	}

}
