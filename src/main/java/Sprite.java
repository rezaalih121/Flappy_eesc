import java.awt.*;
import java.awt.image.ImageObserver;

public abstract class Sprite {
    protected int x;
    protected int y;

    public Sprite(int x, int y, Color couleur) {
        this.x = x;
        this.y = y;
        this.couleur = couleur;
    }

    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
        this.couleur = Color.RED;
    }

    protected Color couleur;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getCouleur() {
        return couleur;
    }

    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    public abstract void dessiner(Graphics2D dessin, ImageObserver imageObserver);

    public abstract boolean collision(int x, int y);

    public abstract boolean collision(Sprite sprite);

    public abstract int getCentreX();

    public abstract int getCentreY();

    public static boolean testCollision(Sprite sprite1, Sprite sprite2) {

        return sprite1.collision(sprite2) || sprite2.collision(sprite1);

    }
}
