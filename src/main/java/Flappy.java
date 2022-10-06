import javax.management.monitor.MonitorSettingException;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Flappy extends Canvas implements KeyListener, MouseListener, MouseMotionListener {

    protected static int largeurEcran = 600;
    protected static int hauteurEcran = 600;

    protected Oiseau oiseau;
    protected Tuyau tuyau;
    protected Moustique moustique;

    protected int tuyauLargeur;
    protected long point = 0;
    protected int tuyauHauteur;
    protected int nombreDeMoustique;
    protected int textWidth = 0;
    protected ArrayList<Deplacable> listDeplacable = new ArrayList<>();
    protected ArrayList<Sprite> listSprite = new ArrayList<>();
    protected ArrayList<Moustique> listMoustique = new ArrayList<>();

    private static final Random RANDOMISER = new Random();

    protected Etat etat = Etat.EN_COURS;


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
        etat = Etat.EN_COURS;

        if (oiseau == null) {
            oiseau = new Oiseau(hauteurEcran);
            listDeplacable = new ArrayList<>();
            listMoustique = new ArrayList<>();
            for (int i = 0; i < 7; i++) {

                tuyauLargeur = (tuyauLargeur == largeurEcran + i * generateRandomNumber(100, 200)) ? (largeurEcran + i * generateRandomNumber(100, 200)) : (largeurEcran + i * generateRandomNumber(100, 200));
                tuyauHauteur = (tuyauLargeur == hauteurEcran - i * generateRandomNumber(5, 50)) ? hauteurEcran - i * generateRandomNumber(5, 50) : hauteurEcran - i * generateRandomNumber(5, 50);

                tuyau = new Tuyau(121 + generateRandomNumber(70, 200), hauteurEcran, tuyauLargeur);
                listDeplacable.add(tuyau);
                listSprite.add(tuyau);
            }


            listDeplacable.add(oiseau);

            listSprite.add(oiseau);

            //tuyau = new Tuyau(300, hauteurEcran, largeurEcran + 50);
            nombreDeMoustique = generateRandomNumber(20, 40);
            for (int i = 0; i < nombreDeMoustique; i++) {
                moustique = new Moustique(largeurEcran, hauteurEcran);
                listDeplacable.add(moustique);
                listMoustique.add(moustique);
                point++;
            }
            for (int i = 0; i < 20; i++) {
                Nuage nuage = new Nuage(largeurEcran, hauteurEcran);
                listDeplacable.add(nuage);
                listSprite.add(nuage);

            }
        } else {
            nombreDeMoustique = generateRandomNumber(20, 40);
            listMoustique = new ArrayList<>();
            point = 0;
            for (int i = 0; i < nombreDeMoustique; i++) {
                moustique = new Moustique(largeurEcran, hauteurEcran);
                listMoustique.add(moustique);
                listDeplacable.add(moustique);
                point++;
            }
            for (Deplacable deplacable : listDeplacable) {
                deplacable.reinitialiser(largeurEcran, hauteurEcran);
            }

        }

    }

    public void demarrer() throws InterruptedException {

        long indexFrame = 0;


        initialiser();

        Font police = new Font("Calibri", Font.BOLD, 24);
        Font alert = new Font("Calibri", Font.BOLD, 50);


        Graphics2D dessin = (Graphics2D) getBufferStrategy().getDrawGraphics();
        dessin.setFont(alert);
        textWidth = dessin.getFontMetrics().stringWidth("PAUSE");

        while (true) {
            indexFrame++;
            dessin = (Graphics2D) getBufferStrategy().getDrawGraphics();

            //-----------------------------
            //reset dessin
            dessin.setColor(Color.WHITE);
            //dessin.fillRect(0, 0, largeurEcran, hauteurEcran);
            Toolkit t = Toolkit.getDefaultToolkit();
            Image img = t.getImage(System.getProperty("user.home") + "\\IdeaProjects\\Flappy_eesc\\src\\main\\resources\\background.jpg");
            dessin.drawImage(img, 0, 0, largeurEcran, hauteurEcran, this);

            //oiseau.dessiner(dessin, this);
            for (Sprite sprite : listSprite) {
                sprite.dessiner(dessin, this);
            }
            for (Moustique moustique1 : listMoustique) {
                moustique1.dessiner(dessin, this);
            }
            //affichage HUD
            dessin.setColor(Color.BLACK);
            dessin.setFont(police);
            dessin.drawString("Rest " + String.valueOf(point) + " de Moustique !", largeurEcran - 221, 50);


            if (etat == Etat.EN_COURS) {
                if (oiseau.getY() > hauteurEcran - oiseau.getLargeur()) {
                    // si jamais l oiseau est tombe par terre
                    //System.out.println("perdu");
                    etat = Etat.PERDU;

                } else {

                    // sinon si le jeu continu
                    //oiseau.deplacer(largeurEcran, hauteurEcran);
                    tuyau.deplacer(largeurEcran, hauteurEcran);
                    moustique.deplacer(largeurEcran, hauteurEcran);
                    for (Deplacable deplacable : listDeplacable) {
                        deplacable.deplacer(largeurEcran, hauteurEcran);
                    }
                    for (Deplacable deplacable : listDeplacable) {
                        if(listDeplacable.indexOf(deplacable) % 2== 0)
                        deplacable.deplacer(largeurEcran, hauteurEcran);
                    }

                    for (int i = 0; i < listSprite.size(); i++) {
                        if (listSprite.get(i) instanceof Tuyau) {
                            if (Sprite.testCollision(oiseau, listSprite.get(i))) {
                                etat = Etat.PERDU;
                            }
                        }
                    }

                    for (int i = 0; i < listMoustique.size(); i++) {

                        if (Sprite.testCollision(oiseau, listMoustique.get(i))) {
                            point--;
                            listMoustique.remove(i);
                            if (point == 0) {
                                etat = Etat.GAGNE;
                            }
                        }

                    }
                }
            } else {

                dessin.setColor(new Color(0, 0, 0, 0.5f));
                dessin.fillRect(0, 0, largeurEcran, hauteurEcran);
                if (etat == Etat.PAUSE) {
                    dessin.setColor(Color.yellow);
                    dessin.setFont(alert);

                    dessin.drawString("PAUSE", largeurEcran / 2 - textWidth / 2, hauteurEcran / 2);
                } else if (etat == Etat.PERDU) {
                    dessin.setColor(Color.yellow);
                    dessin.setFont(alert);
                    dessin.drawString("PERDU", largeurEcran / 2 - textWidth / 2, hauteurEcran / 2);
                } else if (etat == Etat.GAGNE) {
                    nombreDeMoustique = generateRandomNumber(20, 70);
                    point = nombreDeMoustique;
                    dessin.setColor(Color.yellow);
                    dessin.setFont(alert);
                    dessin.drawString("GAGNE", largeurEcran / 2 - textWidth / 2, hauteurEcran / 2);
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
            etat = etat == Etat.PAUSE ? Etat.EN_COURS : Etat.PAUSE;
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
