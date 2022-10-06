import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class Tuyau extends Rectangle implements Deplacable {

    public Tuyau(int hauteur, int hauteurEcran, int largeurEcran) {
        super(largeurEcran - 100, hauteurEcran - hauteur, 100, hauteur);
    }


    public void dessiner(Graphics2D dessin) {
        dessin.setColor(couleur);
        dessin.fillRect(x, y, largeur, hauteur);
    }

    @Override
    public void dessiner(Graphics2D dessin, ImageObserver imageObserver) {
        dessin.setColor(couleur);
        Toolkit t = Toolkit.getDefaultToolkit();

        Image img = t.getImage(System.getProperty("user.home") + "\\IdeaProjects\\Flappy_eesc\\src\\main\\resources\\tuyau" + 3 + ".png");
        //dessin.fillRect( x, y, largeur, largeur);
       /* Image img;
        try {
            img = ImageIO.read(new File("src/main/java/tuyau.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        */
        dessin.drawImage(img, x, y, largeur, hauteur, null);
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

        x = largeurEcran;
    }
}
