package audio;

public class Sound extends Noise {
	private static final Sound SOUND = new Sound();

	private Sound() {

	}

	public static Sound getInstance() {
		return SOUND;
	}
}