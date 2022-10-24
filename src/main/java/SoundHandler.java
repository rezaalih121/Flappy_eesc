
import javax.sound.sampled.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class SoundHandler {
    public SoundHandler(String filesPath) {
        this.filesPath = filesPath;
    }

    protected String filesPath;

    public void playSound(String soundFile, boolean playInLoop) {

        AudioInputStream audioIn = null;
//TODO https://stackoverflow.com/questions/941754/how-to-get-a-path-to-a-resource-in-a-java-jar-file

        try {


            File file = null;
            String resource = this.getClass().getClassLoader().getResource(soundFile).getPath();
            //System.out.println(resource);
            URL res = this.getClass().getClassLoader().getResource(soundFile);
            if (res.getProtocol().equals("jar")) {
                try {
                    InputStream input = getClass().getResourceAsStream(resource);
                    file = File.createTempFile("tempfile", ".tmp");
                    OutputStream out = new FileOutputStream(file);
                    int read;
                    byte[] bytes = new byte[1024];

                    while ((read = input.read(bytes)) != -1) {
                        out.write(bytes, 0, read);
                    }
                    out.close();
                    file.deleteOnExit();
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            } else {
                //this will probably work in your IDE, but not from a JAR
                file = new File(res.getFile());
            }

            if (file != null && !file.exists()) {
                throw new RuntimeException("Error: File " + file + " not found!");
            }


            File f = new File(filesPath + soundFile);
            //URL url = this.getClass().getClassLoader().getResource(""+soundFile);
            //System.out.println("Path: " + (this.getClass().getClassLoader().getResource(""+soundFile)).getPath());
            audioIn = AudioSystem.getAudioInputStream(file);

            Clip clip = null;

            clip = AudioSystem.getClip();
            clip.open(audioIn);
            if (playInLoop)
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            else
                clip.loop(1);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }
    }
}
