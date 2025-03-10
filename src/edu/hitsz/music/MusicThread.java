package edu.hitsz.music;

import javax.sound.sampled.*;
import java.io.*;

/**
 * Thread for playing music.
 */
public class MusicThread extends Thread {

    // Audio file name
    private String filename;
    private AudioFormat audioFormat;
    private byte[] samples;
    private volatile boolean stopped = false;
    private volatile boolean isOver = false;

    public MusicThread(String filename) {
        // Initialize filename
        this.filename = filename;
        reverseMusic();
    }

    public void reverseMusic() {
        try (AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filename))) {
            audioFormat = stream.getFormat();
            samples = getSamples(stream);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getSamples(AudioInputStream stream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = stream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public void play(InputStream source) {
        isOver = false;
        try {
            SourceDataLine dataLine = AudioSystem.getSourceDataLine(audioFormat);
            dataLine.open(audioFormat);
            dataLine.start();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = source.read(buffer)) != -1 && !stopped) {
                dataLine.write(buffer, 0, bytesRead);
            }
            if (!stopped) {
                dataLine.drain();
            }
            dataLine.close();
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try (InputStream stream = new ByteArrayInputStream(samples)) {
            play(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stop playing the music.
     */
    public void stopMusic() {
        this.stopped = true;
    }
}
