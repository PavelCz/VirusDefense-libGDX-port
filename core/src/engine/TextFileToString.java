package engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import towerDefense.TowerDefense;

public class TextFileToString {

	public static List<String> getLines(String path) {
		List<String> list = new ArrayList<String>();
		try {
			File f;
			if (TowerDefense.isApplet()) {
				f = new File("./data/files/" + path);
			} else {
				f = new File("./data/files/" + path);
			}

			BufferedReader reader = new BufferedReader(new FileReader(f));

			String line = null;
			while ((line = reader.readLine()) != null) {
				list.add(line);
			}
			reader.close();
		} catch (IOException x) {
			System.err.println(x);
		}
		return list;
	}

}
