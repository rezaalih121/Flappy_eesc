import java.awt.*;

public abstract class Rectangle extends Carre {

    protected int hauteur;

    public Rectangle(int x, int y, Color couleur, int largeur, int hauteur) {
        super(x, y, couleur, largeur);
        this.hauteur = hauteur;
    }

    public Rectangle(int x, int y, int largeur, int hauteur) {
        super(x, y, Color.ORANGE, largeur);
        this.hauteur = hauteur;
    }

    public int getCentreY() {
        return hauteur / 2;
    }

    public int getHauteur() {
        return hauteur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }

    @Override
    public boolean collision(int x, int y) {
        return x >= this.x
                && x <= this.x + largeur
                && y >= this.y
                && y <= this.y + hauteur;
    }
}
