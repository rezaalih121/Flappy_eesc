import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;

public class Nuage extends Rectangle implements Deplacable {
    public Nuage(int largeurEcran, int hauteurEcran) {
        super(0, 0, new Color(0, 0, 0, 0.05f), 0, 0);
        reinitialiser(largeurEcran, hauteurEcran);
        x -= (int) (Math.random() * largeurEcran);
    }

    @Override
    public void dessiner(Graphics2D dessin, ImageObserver imageObserver) {
        dessin.setColor(couleur);
        Toolkit t = Toolkit.getDefaultToolkit();
        Image img = t.getImage(System.getProperty("user.home") + "\\IdeaProjects\\Flappy_eesc\\src\\main\\java" + File.separator + "cloud1.png");
        //dessin.fillRect( x, y, largeur, largeur);
        dessin.drawImage(img, x, y, largeur, hauteur, imageObserver);
    }


    public void deplacer(int largeurEcran, int hauteurEcran) {
        x--;
        if (x < 0)
            reinitialiser(largeurEcran, hauteurEcran);
    }


    @Override
    public void reinitialiser(int largeurEcran, int hauteurEcran) {
        x = (int) (Math.random() * largeurEcran * 2);
        y = (int) (Math.random() * (hauteurEcran / 2));

        largeur = (int) (Math.random() * 70 + 80);
        hauteur = (int) (Math.random() * 50 + 50);

    }
}
