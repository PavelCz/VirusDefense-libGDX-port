package engine;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundHandler {
	private Map<String, Sound> sounds = new HashMap<String, Sound>();

	public void add(String name, String location) {
		Sound s = Gdx.audio.newSound(Gdx.files.internal("data/sound/" + location));
		this.sounds.put(name, s);
	}

	public void addWav(String name) {
		this.add(name, name + ".wav");
	}

	public void play(String name) {
		if (this.sounds.containsKey(name)) {
			this.sounds.get(name).play();
		}
	}

}
