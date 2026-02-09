package com.securephone.client.audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class AudioPlayer {

	private SourceDataLine line;
	private boolean started;

	public void start(AudioFormat format) throws Exception {
		if (started) {
			return;
		}
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		line = (SourceDataLine) AudioSystem.getLine(info);
		line.open(format);
		line.start();
		started = true;
	}

	public void play(byte[] data, int length) {
		if (!started || line == null || data == null || length <= 0) {
			return;
		}
		line.write(data, 0, length);
	}

	public void stop() {
		if (line != null) {
			line.drain();
			line.stop();
			line.close();
			line = null;
		}
		started = false;
	}

	public boolean isStarted() {
		return started;
	}
}
