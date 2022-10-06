import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class Oiseau extends Carre implements Deplacable {

    protected float vitesseVertical;
    protected final static int HAUTEUR_OISEAU = 40;

    public Oiseau(int hauteurEcran) {
        super(50, 0, HAUTEUR_OISEAU);
        reinitialiser(0, hauteurEcran);
        this.vitesseVertical = 0;
    }


    @Override
    public void reinitialiser(int largeurEcran, int hauteurEcran) {
        y = hauteurEcran / 2 - HAUTEUR_OISEAU / 2;
        vitesseVertical = 0;
    }


    public void setVitesseVertical(float vitesseVertical) {
        this.vitesseVertical = vitesseVertical;
    }

    public Oiseau(int x, int y) {
        super(x, y, HAUTEUR_OISEAU);
        this.vitesseVertical = 0;
    }


    public float getVitesseVertical() {
        return vitesseVertical;
    }

    public void setVitesseVertical(int vitesseVertical) {
        this.vitesseVertical = vitesseVertical;
    }

    @Override
    public void deplacer(int largeurEcran, int hauteurEcran) {

        //petite correction de la gravité, pour eviter un temps de flotement
        //si la vitesse est comprise entre -O,1 et -0,9
        // on augmente legerement la gravité
        if (vitesseVertical % 10 != 0 && vitesseVertical < 0) {
            y -= vitesseVertical - 0.5f;
        } else {
            y -= vitesseVertical;
        }

        vitesseVertical -= 0.05f;

        if (y < 0) {
            vitesseVertical = Math.abs(vitesseVertical) * -1;
        }
    }

    public void sauter() {
        vitesseVertical = 2;
    }

    public void dessiner(Graphics2D dessin) {
        dessin.setColor(couleur);
        Toolkit t = Toolkit.getDefaultToolkit();

        dessin.fillRect(x, y, largeur, largeur);
    }

    @Override
    public void dessiner(Graphics2D dessin, ImageObserver imageObserver) {
        dessin.setColor(couleur);
        Toolkit t = Toolkit.getDefaultToolkit();
        Image img = t.getImage(System.getProperty("user.home") + "\\IdeaProjects\\Flappy_eesc\\src\\main\\resources\\bird.png");
        /*Image img;
        try {
            img = ImageIO.read(new File("src/main/java/bird.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        */

        dessin.drawImage(img, x, y, largeur, largeur, null);
    }


}
