import java.awt.*;

public abstract class Carre extends Sprite {

    protected int largeur;

    public int getLargeur() {
        return largeur;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public Carre(int x, int y, Color couleur, int largeur) {
        super(x, y, couleur);
        this.largeur = largeur;
    }

    public Carre(int x, int y, int largeur) {
        super(x, y);
        this.largeur = largeur;
    }
    public int getCentreX() {
        return largeur/2;
    }
    public int getCentreY() {
        return largeur/2;
    }
}
