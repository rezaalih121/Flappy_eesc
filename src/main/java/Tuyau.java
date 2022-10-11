import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class Tuyau extends Rectangle implements Deplacable {

    public Tuyau(int hauteur, int hauteurEcran, int largeurEcran) {
        super(largeurEcran - 100, hauteurEcran - hauteur, 100, hauteur);
        int colorNomero = (int) (Math.random() * 4);
        this.couleur = colorNomero == 1 ? Color.GREEN : colorNomero == 2 ? couleur.GRAY : colorNomero == 3 ? couleur.RED : couleur.BLUE;
    }


    public void dessiner(Graphics2D dessin) {
        dessin.setColor(couleur);
        dessin.fillRect(x, y, largeur, hauteur);
    }

    @Override
    public void dessiner(Graphics2D dessin, ImageObserver imageObserver) {
        dessin.setColor(couleur);
        Toolkit t = Toolkit.getDefaultToolkit();

        Image img = t.getImage("src/main/resources/tuyau" + (couleur == Color.GREEN ? 1 : couleur == couleur.GRAY ? 2 : couleur == couleur.RED ? 3 : 4) + ".png");
        //dessin.fillRect( x, y, largeur, largeur);
       /* Image img;
        try {
            img = ImageIO.read(new File("src/main/java/tuyau.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        */
        dessin.drawImage(img, x, y, largeur, hauteur, imageObserver);
    }

    @Override
    public void deplacer(int largeurEcran, int hauteurEcran) {
        x -= 1;
        if (x < -largeur)
            reinitialiser(largeurEcran, hauteurEcran);

    }

    public void deplacer(int largeurEcran) {
        x -= 1;

    }

    public void reinitialiser(int largeurEcran, int hauteurEcran) {
        int tuyauLargeur = largeurEcran;
        int i = (int) (Math.random() * 7);
        tuyauLargeur = (tuyauLargeur == largeurEcran + i * Flappy.generateRandomNumber(100, 200)) ? (largeurEcran + i * Flappy.generateRandomNumber(100, 200)) : (largeurEcran + i * Flappy.generateRandomNumber(100, 200));

        x = (int) (Math.random() + tuyauLargeur );
        ///hauteur = Flappy.generateRandomNumber(hauteurEcran,200);
    }
}
