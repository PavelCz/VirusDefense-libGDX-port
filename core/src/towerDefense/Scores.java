package towerDefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import engine.GameComponent;
import engine.gui.SetGameModeButton;
import engine.gui.StaticText;

public class Scores extends GameComponent {

	public Scores(TowerDefense game) {
		super(game);

		SetGameModeButton back = new SetGameModeButton(0, TowerDefense.getHeight() - 20, "Back", this.game, TowerDefense.MODE_MENU);
		back.setColor(Color.BLACK);
		this.clickables.add(back);
		this.guiElements.add(back);

		String scoreString = "Highscores:\n";

		Preferences prefs = Gdx.app.getPreferences("VirusDefense");
		// System.out.println(prefs.getString("score"));
		String[] scoresList = prefs.getString("score").split("\n");
		// System.out.println(scoresList.length);
		for (int i = 0; i < 9; ++i) {
			if (scoresList.length > i) {
				String[] parts = scoresList[i].split(", ");
				if (parts.length == 2) {
					scoreString += "  " + (i + 1) + ": " + parts[0] + ", " + parts[1] + " Punkte\n";
				}
			}
		}
		int i = 9;
		if (scoresList.length > i) {
			String[] parts = scoresList[i].split(", ");
			scoreString += (i + 1) + ": " + parts[0] + ", " + parts[1] + " Punkte\n";
		}

		// List<String> scoresList = TextFileToString.getLines("score.txt");

		// for (int i = 0; i < 9; ++i) {
		// if (scoresList.size() > i) {
		// String[] parts = scoresList.get(i).split(", ");
		// scoreString += "  " + (i + 1) + ": " + parts[0] + ", " + parts[1] + " Punkte\n";
		// }
		// }
		// int i = 9;
		// if (scoresList.size() > i) {
		// String[] parts = scoresList.get(i).split(", ");
		// scoreString += (i + 1) + ": " + parts[0] + ", " + parts[1] + " Punkte\n";
		// }
		StaticText scores = new StaticText(0, 0, Color.WHITE, scoreString);
		scores.setPosition((TowerDefense.getWidth() - scores.getWidth()) / 2,
				(TowerDefense.getHeight() - scores.getActualHeight()) / 2);
		scores.setColor(Color.BLACK);
		this.guiElements.add(scores);

	}

	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		super.renderGUI(batch);
	}
}
