import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundHandler {
    public SoundHandler(String filesPath) {
        this.filesPath = filesPath;
    }

    protected String filesPath ;
    public void playSound(String soundFile ,boolean playInLoop) {

        AudioInputStream audioIn = null;

        try {
            File f = new File(filesPath + soundFile);
            //URL url = this.getClass().getClassLoader().getResource(""+soundFile);
            //System.out.println("Path: " + (this.getClass().getClassLoader().getResource(""+soundFile)).getPath());
            audioIn = AudioSystem.getAudioInputStream(f);

            Clip clip = null;

            clip = AudioSystem.getClip();
            clip.open(audioIn);
            if(playInLoop)
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            else
                clip.loop(0);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }
    }
}
