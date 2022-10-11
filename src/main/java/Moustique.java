import java.awt.*;
import java.awt.image.ImageObserver;

public class Moustique extends Rectangle implements Deplacable {

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

        Image img = t.getImage("src/main/resources/mostique.png");

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
        x = (int) (Math.random() * largeurEcran);
        y = (int) (Math.random() * (hauteurEcran / 2));

        largeur = (int) (Math.random() * 10 + 30);
        hauteur = (int) (Math.random() * 10 + 20);
    }
}
