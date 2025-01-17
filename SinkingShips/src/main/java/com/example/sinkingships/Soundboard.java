package com.example.sinkingships;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class Soundboard {
    private float volumeEffects;
    private float volumeMusic;
    private AudioInputStream audioInputStream;

    public Soundboard() {
        volumeEffects = 1.0f;
        volumeMusic = 1.0f;
    }
    public Soundboard(float volumeEffectsToSet, float volumeMusicToSet) {
        this.volumeEffects = volumeEffectsToSet;
        this.volumeMusic = volumeMusicToSet;
    }

    /**
     * Sets the Volume of the soundboard
     * @param volume The volume to set it to. Must be a Value between 0.0f and 1.0f
     * @param setEffects If true, the volume of the effects is set. If false, the volume of the music is set
     */
    public void setVolume(float volume, boolean setEffects){
        if(setEffects){
            volumeEffects = volume;
        } else {
            volumeMusic = volume;
        }
    }

    public void playKrizelshiff(){
        playRndSoundsInRange("/audio/Kritzlschiff", 10, ".wav");
    }
    public void playCueGo(){
        playRndSoundsInRange("/audio/Go", 3, ".wav");
    }
    public void playCueWon(){
        playRndSoundsInRange("/audio/Won", 3, ".wav");
    }
    public void playCueLost(){
        playRndSoundsInRange("/audio/Lost", 3, ".wav");
    }
    public void playCanonShot(){
        playRndSoundsInRange("/audio/Peng", 15, ".wav");
    }
    public void playImpactBoom(){
        playRndSoundsInRange("/audio/Boom", 8, ".wav");
    }
    public void playScribble(){
        playRndSoundsInRange("/audio/Scribble", 2, ".wav");
    }
    public void playClick(){
        playRndSoundsInRange("/audio/ClickBig", 1, ".wav");
    }
    public void playPaper(){
        playRndSoundsInRange("/audio/Paper", 3, ".wav");
    }
    public void playCatNoise(){
        playRndSoundsInRange("/audio/Cat", 4, ".wav");
    }

    /**
     * Plays a sound from a random set of sounds
     * @param fileLinkStart Start of the link for the random sounds
     * @param range The inclusive range of file numbers. The file numbers start at 1
     * @param fileLinkEnd End of the link for the random sounds after the number
     */
    private void playRndSoundsInRange(String fileLinkStart, int range, String fileLinkEnd){
        URL audioFile;
        Random random = new Random();
        int fileNumber = random.nextInt(range) + 1;
        if(fileNumber >= 10){
            audioFile = getClass().getResource(fileLinkStart + String.valueOf(fileNumber) + fileLinkEnd);
        } else {
            audioFile = getClass().getResource(fileLinkStart + "0" + String.valueOf(fileNumber) + fileLinkEnd);
        }
        this.playSpecificSoundFile(audioFile);
    }

    /**
     * Plays an audio file from the given URL in the ressources folder
     * @param audioFile The URL to the file inside the ressources folder
     */
    private void playSpecificSoundFile(URL audioFile){
        try {
            audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            Clip audioClip = AudioSystem.getClip();
            audioClip.open(audioInputStream);

            FloatControl gainControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
            float gainRange = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (gainRange * this.volumeEffects) + gainControl.getMinimum();

            gainControl.setValue(gain);
            audioClip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
