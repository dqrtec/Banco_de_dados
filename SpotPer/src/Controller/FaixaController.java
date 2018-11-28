package Controller;

import Model.Faixa;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 *
 * @author Tibet Teixeira
 */
public class FaixaController {
    
    public Clip tocarFaixa(Faixa faixa) {
       Clip clip = null;
       
       try {
            File soundFile = new File(faixa.getDiretorio());
            AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
            DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(sound);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
       return clip;
    }
    
    public void pararFaixa(Clip clip) {
        clip.stop();
    }
}
