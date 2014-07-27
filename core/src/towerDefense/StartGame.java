package towerDefense;

import java.util.List;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import engine.TextFileToString;

public class StartGame {
	public static void main(final String[] args) throws SlickException {
		List<String> settings = TextFileToString.getLines("settings.txt");
		int width = Integer.parseInt(settings.get(0));
		int height = Integer.parseInt(settings.get(1));
		boolean fullscreen;
		if (Integer.parseInt(settings.get(2)) == 0) {
			fullscreen = false;
		} else {
			fullscreen = true;
		}
		final TowerDefense game = new TowerDefense(false);
		AppGameContainer appGameContainer;
		appGameContainer = new AppGameContainer(game, width, height, fullscreen);
		appGameContainer.start();
	}
}
