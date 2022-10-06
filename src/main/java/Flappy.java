import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Flappy extends Canvas implements KeyListener, MouseListener, MouseMotionListener {

    protected static int largeurEcran = 600;
    protected static int hauteurEcran = 600;
    protected boolean pause;
    protected boolean perdu;

    protected Oiseau oiseau;
    protected Tuyau tuyau;

    protected ArrayList<Deplacable> listDeplacable = new ArrayList<>();
    protected ArrayList<Sprite> listSprite = new ArrayList<>();

    private static final Random RANDOMISER = new Random();

    public static int generateRandomNumber(int from, int to) {
        return RANDOMISER.nextInt((to + 1) - from) + from;
    }

    public Flappy() throws InterruptedException {
        JFrame fenetre = new JFrame("Flappy");
        //On récupère le panneau de la fenetre principale
        JPanel panneau = (JPanel) fenetre.getContentPane();
        //On définie la hauteur / largeur de l'écran
        panneau.setPreferredSize(new Dimension(largeurEcran, hauteurEcran));
        setBounds(0, 0, largeurEcran, hauteurEcran);
        //On ajoute cette classe (qui hérite de Canvas) comme composant du panneau principal
        panneau.add(this);

        fenetre.pack();
        fenetre.setResizable(false);
        fenetre.setLocationRelativeTo(null);
        fenetre.setVisible(true);
        fenetre.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fenetre.requestFocus();
        fenetre.addKeyListener(this);
        fenetre.addMouseListener(this);
        fenetre.addMouseMotionListener(this);


        //On indique que le raffraichissement de l'ecran doit être fait manuellement.
        createBufferStrategy(2);
        setIgnoreRepaint(true);
        this.setFocusable(false);


        demarrer();
    }

    public void initialiser() {
        // Si cest la premiere initialisation
        pause = false;
        if (oiseau == null) {
            oiseau = new Oiseau(hauteurEcran);


            listDeplacable = new ArrayList<>();

/*
            for (int i = 0; i < 0; i++) {

                tuyau = new Tuyau(200 * (int) (Math.random() * 2), hauteurEcran + (int) (Math.random() * 30), largeurEcran + (int) (Math.random() * 50));
                listDeplacable.add(tuyau);
                listSprite.add(tuyau);
            }
*/
            //tuyau = new Tuyau(200, hauteurEcran, largeurEcran);

            for (int i = 0; i < 10; i++) {
                tuyau = new Tuyau(50 + generateRandomNumber(70, 200), hauteurEcran - generateRandomNumber(0, 400), largeurEcran + generateRandomNumber(600, 700));
                listDeplacable.add(tuyau);
                listSprite.add(tuyau);
            }


            listDeplacable.add(oiseau);

            listSprite.add(oiseau);

            tuyau = new Tuyau(300, hauteurEcran, largeurEcran + 50);


            for (int i = 0; i < 20; i++) {
                Nuage nuage = new Nuage(largeurEcran, hauteurEcran);
                listDeplacable.add(nuage);
                listSprite.add(nuage);
            }
        } else {

            for (Deplacable deplacable : listDeplacable) {
                deplacable.reinitialiser(largeurEcran, hauteurEcran);
            }

        }

    }

    public void demarrer() throws InterruptedException {

        long indexFrame = 0;

        long point = 0;

        initialiser();

        Font police = new Font("Calibri", Font.BOLD, 24);
        Font alert = new Font("Calibri", Font.BOLD, 50);

        while (true) {
            indexFrame++;
            Graphics2D dessin = (Graphics2D) getBufferStrategy().getDrawGraphics();

            //-----------------------------
            //reset dessin
            dessin.setColor(Color.WHITE);
            //dessin.fillRect(0, 0, largeurEcran, hauteurEcran);
            Toolkit t = Toolkit.getDefaultToolkit();
            Image img = t.getImage(System.getProperty("user.home") + "\\IdeaProjects\\Flappy_eesc\\src\\main\\resources\\backgrond1.png");
            dessin.drawImage(img, 0, 0, largeurEcran, hauteurEcran, this);

            //oiseau.dessiner(dessin, this);
            for (Sprite sprite : listSprite) {
                sprite.dessiner(dessin, this);
            }
            tuyau.dessiner(dessin, this);

            //affichage HUD
            dessin.setColor(Color.BLACK);
            dessin.setFont(police);
            dessin.drawString(
                    String.valueOf(point),
                    largeurEcran - 100,
                    50);


            if (!pause || !perdu) {
                if (oiseau.getY() > hauteurEcran - oiseau.getLargeur()) {
                    // si jamais l oiseau est tombe par terre
                    System.out.println("perdu");
                    perdu = true;
                    pause = true;
                } else {
                    point++;
                    // sinon si le jeu continu
                    oiseau.deplacer(largeurEcran, hauteurEcran);
                    tuyau.deplacer(largeurEcran, hauteurEcran);
                    for (Deplacable deplacable : listDeplacable) {
                        deplacable.deplacer(largeurEcran, hauteurEcran);
                    }
                    for (Sprite sprite : listSprite) {

                        if (sprite instanceof Oiseau)
                            oiseau = (Oiseau) sprite;
                        if (sprite instanceof Tuyau)
                            tuyau = (Tuyau) sprite;

                        if (Sprite.testCollision(oiseau, tuyau)) {
                            perdu = true;

                            pause = true;
                        }
                    }


                }
            } else {
                dessin.setColor(new Color(0, 0, 0, 0.1f));
                dessin.fillRect(0, 0, largeurEcran, hauteurEcran);


                if (perdu) {
                    dessin.setColor(Color.yellow);
                    dessin.setFont(alert);
                    dessin.drawString(
                            "<<<<<<<<PRDU>>>>>>>>>",
                            largeurEcran / 2 - 270,
                            hauteurEcran / 2);
                }


            }

            //-----------------------------
            dessin.dispose();
            getBufferStrategy().show();
            Thread.sleep(1000 / 60);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Flappy();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        if (e.getKeyCode() == 39) {
            // inverser un boolean
            oiseau.setX(oiseau.getX() + 10);
            System.out.println("go right");
        }
        if (e.getKeyCode() == 37) {
            // inverser un boolean
            oiseau.setX(oiseau.getX() - 10);
            System.out.println("go left");
        }
        if (e.getKeyCode() == 38) {
            // inverser un boolean

            oiseau.setY(oiseau.getY() - 30);
            System.out.println("go up");
        }
        if (e.getKeyCode() == 40) {
            // inverser un boolean
            oiseau.setY(oiseau.getY() + 10);
            System.out.println("go down");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            oiseau.setVitesseVertical(2);
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            initialiser();
        }
        if (e.getKeyCode() == KeyEvent.VK_P) {
            // inverser un boolean
            pause = !pause;
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("X: " + e.getX());
        System.out.println("X: " + e.getXOnScreen());
        System.out.println("Y: " + e.getY());
        System.out.println("Y: " + e.getXOnScreen());
        System.out.println(e.getClickCount());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("X: " + e.getX());
        System.out.println("X: " + e.getXOnScreen());
        System.out.println("Y: " + e.getY());
        System.out.println("Y: " + e.getXOnScreen());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Mouse entered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Mouse Exited");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("Mouse Dragged X: " + e.getX() + " Y : " + e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println("Mouse Moveed X: " + e.getX() + " Y : " + e.getY());
    }
}
