package engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;

public class TextFileToString {

	public static List<String> getLines(String path) {
		List<String> list = new ArrayList<String>();
		try {

			BufferedReader reader = new BufferedReader(Gdx.files.internal("data/files/" + path).reader());

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
