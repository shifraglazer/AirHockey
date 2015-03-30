package musicTrimmer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/** Used to trim size of wave files
 * Takes in wav file > trims > saves new trimmed file */

public class TrimWAV extends AudioInputStream {

	private final AudioInputStream stream;
	private final long startByte, endByte;
	private long t_bytesRead = 0;

	public TrimWAV(AudioFormat audioFormat, AudioInputStream audioInputStream, long startMilli, long endMilli) {
		super(new ByteArrayInputStream(new byte[0]), audioFormat, AudioSystem.NOT_SPECIFIED);
		stream = audioInputStream;
		// calculate where to start and where to end
		startByte = (long) ((startMilli / 1000) * stream.getFormat().getFrameRate() * stream.getFormat().getFrameSize());
		endByte = (long) (20 * stream.getFormat().getFrameRate() * stream.getFormat().getFrameSize());
	}

	@Override
	public int available() throws IOException {
		return (int) (endByte - startByte - t_bytesRead);
	}

	public int read(byte[] abData, int nOffset, int nLength) throws IOException {
		int bytesRead = 0;
		if (t_bytesRead < startByte) {
			do {
				bytesRead = (int) skip(startByte - t_bytesRead);
				t_bytesRead += bytesRead;
			} while (t_bytesRead < startByte);
		}
		if (t_bytesRead >= endByte)// end reached. signal EOF
			return -1;

		bytesRead = stream.read(abData, 0, nLength);
		if (bytesRead == -1)
			return -1;
		else if (bytesRead == 0)
			return 0;

		t_bytesRead += bytesRead;
		if (t_bytesRead >= endByte)// "trim" the extra by altering the number of bytes read
			bytesRead = (int) (bytesRead - (t_bytesRead - endByte));

		return bytesRead;
	}

	public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
		AudioInputStream music = null;
		// copy file to project before trimming
		music = AudioSystem.getAudioInputStream(new File("sailorPiccolo.wav"));
		music = new TrimWAV(music.getFormat(), music, 0, 15000);
		AudioSystem.write(music, AudioFileFormat.Type.WAVE, new File("out.wav"));

		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File("out.wav")));
			clip.start();
			clip.loop(2);
			while (clip.isOpen()) {
				// busy loop to test if clip is right length and loops at right spot
			}
		}
		catch (LineUnavailableException e) {
			e.printStackTrace();

		}

	}
}
