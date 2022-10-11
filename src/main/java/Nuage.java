import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;

public class Nuage extends Rectangle implements Deplacable {
    public Nuage(int largeurEcran, int hauteurEcran) {
        super(0, 0, new Color(0, 0, 0, 0.05f), 0, 0);
        reinitialiser(largeurEcran, hauteurEcran);

    }

    @Override
    public void dessiner(Graphics2D dessin, ImageObserver imageObserver) {
        dessin.setColor(couleur);
        Toolkit t = Toolkit.getDefaultToolkit();
        Image img = t.getImage("src/main/resources/cloud.png");
        //dessin.fillRect( x, y, largeur, largeur);
        dessin.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.70f));
        dessin.drawImage(img, x, y, largeur, hauteur, imageObserver);
        dessin.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }


    public void deplacer(int largeurEcran, int hauteurEcran) {
        x--;
        if (x < -largeur)
            reinitialiser(largeurEcran, hauteurEcran);

        if (y > hauteurEcran / 2)
            y--;
        else if (y == 0)
            y++;
    }


    @Override
    public void reinitialiser(int largeurEcran, int hauteurEcran) {
        x = (int) (Math.random() * largeurEcran);
        y = (int) (Math.random() * (hauteurEcran / 2));

        largeur = (int) (Math.random() * 70 + 80);
        hauteur = (int) (Math.random() * 50 + 50);

    }
}
