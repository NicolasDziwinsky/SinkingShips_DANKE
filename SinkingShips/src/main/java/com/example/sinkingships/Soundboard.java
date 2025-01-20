package com.example.sinkingships;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class Soundboard {
    /**
     * The volume of the played sound effects. 1.0f is the maximum. 0.0f is the minimum.
     */
    private static float volumeEffects = 1.0f;
    /**
     * This value tells you if the sound is muted. If true, sounds won't be played.
     */
    public static boolean effectsIsMuted = false;

    /**
     * Sets the Volume of the soundboard.
     * @param volume The volume to set it to. Must be a Value between 0.0f and 1.0f.
     */
    public static void setVolume(float volume){
        volumeEffects = volume;
    }

    /**
     * Gets the Volume of the soundboard.
     * @return The Volume.
     */
    public static float getVolume(){
        return volumeEffects;
    }

    /**
     * Plays one of the 'Krizelshiff' soundbites available at random.
     */
    public void playKrizelshiff(){
        playRndSoundsInRange("/audio/Kritzlschiff", 10);
    }
    /**
     * Plays one of the 'Jetzt krizeln wir sie uns' soundbites available at random.
     */
    public void playCueGo(){
        playRndSoundsInRange("/audio/Go", 3);
    }
    /**
     * Plays one of the 'Wir haben sie weggekrizelt' soundbites available at random.
     */
    public void playCueWon(){
        playRndSoundsInRange("/audio/Won", 3);
    }
    /**
     * Plays one of the 'Wir haben verloren' soundbites available at random.
     */
    public void playCueLost(){
        playRndSoundsInRange("/audio/Lost", 3);
    }
    /**
     * Plays one of the shooting canon soundbites available at random.
     */
    public void playCanonShot(){
        playRndSoundsInRange("/audio/Peng", 15);
    }
    /**
     * Plays one of the impacting projectile soundbites available at random.
     */
    public void playImpactBoom(){
        playRndSoundsInRange("/audio/Boom", 8);
    }
    /**
     * Plays one of the available soundbites for when an empty game cell is hit at random.
     */
    public void playMissed(){
        playRndSoundsInRange("/audio/Water", 6);
    }
    /**
     * Plays one of the available soundbites for when a page is turned at random.
     */
    public void playPaper(){
        playRndSoundsInRange("/audio/Paper", 3);
    }
    /**
     * Plays one of the available soundbites for cat noises at random.
     */
    public void playCatNoise(){
        playRndSoundsInRange("/audio/Cat", 4);
    }

    /**
     * Plays a sound from a random set of sounds
     *
     * @param fileLinkStart Start of the link for the random sounds
     * @param range         The inclusive range of file numbers. The file numbers start at 1
     */
    private void playRndSoundsInRange(String fileLinkStart, int range){
        URL audioFile;
        Random random = new Random();
        int fileNumber = random.nextInt(range) + 1;
        if(fileNumber >= 10){
            audioFile = getClass().getResource(fileLinkStart + String.valueOf(fileNumber) + ".wav");
        } else {
            audioFile = getClass().getResource(fileLinkStart + "0" + String.valueOf(fileNumber) + ".wav");
        }
        this.playSpecificSoundFile(audioFile);
    }

    /**
     * Plays an audio file from the given URL in the ressources folder
     * @param audioFile The URL to the file inside the ressources folder
     */
    private void playSpecificSoundFile(URL audioFile){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            Clip audioClip = AudioSystem.getClip();
            audioClip.open(audioInputStream);

            FloatControl gainControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(0);
            float gainRange = gainControl.getMaximum() + 25;
            float gain = (gainRange * Soundboard.volumeEffects) - gainControl.getMaximum() + gainControl.getMaximum() - 25;

            if(volumeEffects == 0 || effectsIsMuted){
                gainControl.setValue(gainControl.getMinimum());
            } else {
                gainControl.setValue(gain);
            }
            audioClip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
