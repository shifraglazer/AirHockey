package audio;

/** Singleton Class */

public class Sound extends Noise {
	// know for sure will have a Sound so can create it here
	private static final Sound SOUND = new Sound();

	// private constructor so new Sound can never be constructed
	private Sound() {
	}

	// allow other classes access to the singleton instance
	public static Sound getInstance() {
		return SOUND;
	}
}