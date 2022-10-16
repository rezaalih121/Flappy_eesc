import java.awt.*;
import java.awt.image.ImageObserver;

public class OiseauMechant extends Rectangle implements Deplacable {
    public OiseauMechant(int largeurEcran, int hauteurEcran) {
        super(0, 0, new Color(0, 0, 0, 0.05f), 0, 0);
        reinitialiser(largeurEcran, hauteurEcran);

    }

    @Override
    public void dessiner(Graphics2D dessin, ImageObserver imageObserver) {
        dessin.setColor(couleur);
        Toolkit t = Toolkit.getDefaultToolkit();
        Image img = t.getImage(this.getClass().getClassLoader().getResource("oiseauMechant.png"));
        //dessin.fillRect( x, y, largeur, largeur);
        dessin.drawImage(img, x, y, largeur, hauteur, imageObserver);
    }


    public void deplacer(int largeurEcran, int hauteurEcran) {
        x--;
        if (x < -largeur)
            reinitialiser(largeurEcran, hauteurEcran);

       /* if(y > hauteurEcran / 2)
            y = y-(int) (Math.random() * hauteurEcran / 2);
        else if(y == 0)
            y = y + (int) (Math.random() * hauteurEcran / 2);
        else
            y = y+(int) (Math.random() * hauteurEcran / 2);*/
    }


    @Override
    public void reinitialiser(int largeurEcran, int hauteurEcran) {
        int oiseauLargeur = largeurEcran;
        int i = (int) (Math.random() * 7);
        oiseauLargeur = (oiseauLargeur == largeurEcran + i * Flappy.generateRandomNumber(100, 200)) ? (largeurEcran + i * Flappy.generateRandomNumber(100, 200)) : (largeurEcran + i * Flappy.generateRandomNumber(100, 200));

        x = (int) (Math.random() + oiseauLargeur);
        y = (int) (Math.random() * (hauteurEcran / 2));

        largeur = 40;
        hauteur = 40;

    }
}

