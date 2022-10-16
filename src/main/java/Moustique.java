import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class Moustique extends Rectangle implements Deplacable {

    SoundHandler soundHandler = new SoundHandler("src/main/resources/");
    public Moustique(int hauteurEcran, int largeurEcran) {
        super(0, 0, 0, 0);
        reinitialiser(largeurEcran, hauteurEcran);

    }


    public void dessiner(Graphics2D dessin) {
        dessin.setColor(couleur);
        dessin.fillRect(x, y, largeur, hauteur);
    }

    @Override
    public void dessiner(Graphics2D dessin, ImageObserver imageObserver) {
        dessin.setColor(couleur);
        Toolkit t = Toolkit.getDefaultToolkit();

        Image img = t.getImage(this.getClass().getClassLoader().getResource("mostique.png"));

        dessin.drawImage(img, x, y, largeur, hauteur, imageObserver);
    }

    @Override
    public void deplacer(int largeurEcran, int hauteurEcran) {
        x -= 1;
        if (x < -largeur) {
            soundHandler.playSound("mosquiteSound.wav" , false );
            reinitialiser(largeurEcran, hauteurEcran);
        }

    }

    public void deplacer(int largeurEcran) {
        x--;

    }

    public void reinitialiser(int largeurEcran, int hauteurEcran) {
        int moustiqueLargeur = largeurEcran;
        int i = (int) (Math.random() * 7);
        moustiqueLargeur = (moustiqueLargeur == largeurEcran + i * Flappy.generateRandomNumber(100, 200)) ? (largeurEcran + i * Flappy.generateRandomNumber(100, 200)) : (largeurEcran + i * Flappy.generateRandomNumber(100, 200));


        x = (int) (Math.random() + moustiqueLargeur);
        y = (int) (Math.random() * (hauteurEcran / 2));


        largeur = (int) (Math.random() * 10 + 30);
        hauteur = (int) (Math.random() * 10 + 20);
    }

}
