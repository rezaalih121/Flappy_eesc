import javax.management.monitor.MonitorSettingException;
import javax.print.attribute.standard.Media;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

public class Flappy extends Canvas implements KeyListener, EventListener, MouseListener, MouseMotionListener, MouseWheelListener, ComponentListener, WindowListener {

    protected static int largeurEcran = 600;
    protected static int hauteurEcran = 600;

    protected boolean windowsResized = false;

    protected Oiseau oiseau;
    protected Tuyau tuyau;
    protected Moustique moustique;

    protected int tuyauLargeur;
    protected long point = 0;
    protected int tuyauHauteur;
    protected int nombreDeMoustique;
    protected int textWidth = 0;
    protected Toolkit toolkit = Toolkit.getDefaultToolkit();
    JLabel jLabelBackground = null;

    protected ArrayList<Deplacable> listDeplacable = new ArrayList<>();
    protected ArrayList<Sprite> listSprite = new ArrayList<>();
    protected ArrayList<Moustique> listMoustique = new ArrayList<>();

    private static final Random RANDOMISER = new Random();

    protected Etat etat = Etat.EN_COURS;

    protected JDialog welcomeDialog;


    //loading locale language
    private static final int DEFAULT_LOCALE = 0;
    private ResourceBundle bundle;
    public static Locale locale;
    public static final Locale[] supportedLocales = {
            new Locale("en", "US"),
            new Locale("fr", "FR")
    };

    public static int generateRandomNumber(int from, int to) {
        return RANDOMISER.nextInt((to + 1) - from) + from;
    }

    public Flappy() throws InterruptedException {
        //loading locale language
        for (Locale locale1 : supportedLocales) {
            if (locale1.getLanguage().equals(Locale.getDefault().getLanguage())) {
                //System.out.println(locale1.toString());
                locale = locale1;
                break;
            } else {
                locale = supportedLocales[DEFAULT_LOCALE];
                //System.out.println(locale1.toString());
            }
        }

        bundle = ResourceBundle.getBundle("LanguageResource", locale);
        System.out.println(bundle.getString("userInfoMenu"));


        JFrame fenetre = new JFrame(bundle.getString("windowsTitle"));
        //On récupère le panneau de la fenetre principale
        JPanel panneau = (JPanel) fenetre.getContentPane();
        //On définie la hauteur / largeur de l'écran
        panneau.setPreferredSize(new Dimension(largeurEcran, hauteurEcran));
        setBounds(0, 0, largeurEcran, hauteurEcran);
        //On ajoute cette classe (qui hérite de Canvas) comme composant du panneau principal
        panneau.add(this);
        toolkit = Toolkit.getDefaultToolkit();
        JMenuBar jMenuBar = new JMenuBar();
        JMenu jMenuUserInfo = new JMenu(bundle.getString("userInfoMenu"));


        JMenu jMenuSetting = new JMenu(bundle.getString("settingLabel"));


        JMenu jMenuHelp = new JMenu(bundle.getString("helpMenu"));
        jMenuHelp.addActionListener(e -> {
            System.out.println("hgr");
            welcomeDialog.setVisible(true);
            welcomeDialog.setFocusableWindowState(true);
        });


        JMenu jMenuLanguage = new JMenu(bundle.getString("languageMenu") + " : " + locale.getDisplayLanguage());
        JMenuItem jMenuItemEn = new JMenuItem(supportedLocales[0].getDisplayLanguage());
        JMenuItem jMenuItemFr = new JMenuItem(supportedLocales[1].getDisplayLanguage());
        jMenuItemFr.addActionListener(e -> {
            //System.out.println(locale.getDisplayLanguage());
            locale = supportedLocales[1];
            bundle = ResourceBundle.getBundle("LanguageResource", locale);

            //System.out.println("FR : "+bundle.getLocale().getDisplayLanguage());

            jMenuLanguage.setText(bundle.getString("languageMenu") + " : " + locale.getDisplayLanguage());
            jMenuUserInfo.setText(bundle.getString("userInfoMenu"));
            jMenuSetting.setText(bundle.getString("settingLabel"));
            jMenuHelp.setText(bundle.getString("helpMenu"));
            fenetre.setTitle(bundle.getString("windowsTitle"));

        });
        jMenuItemEn.addActionListener(e -> {
            //System.out.println(locale.getDisplayLanguage());
            locale = supportedLocales[DEFAULT_LOCALE];
            bundle = ResourceBundle.getBundle("LanguageResource", locale);

            //System.out.println("EN : "+bundle.getLocale().getDisplayLanguage());

            jMenuLanguage.setText(bundle.getString("languageMenu") + " : " + locale.getDisplayLanguage());
            jMenuUserInfo.setText(bundle.getString("userInfoMenu"));
            jMenuSetting.setText(bundle.getString("settingLabel"));
            jMenuHelp.setText(bundle.getString("helpMenu"));
            fenetre.setTitle(bundle.getString("windowsTitle"));

        });


        jMenuLanguage.add(jMenuItemFr);
        jMenuLanguage.add(jMenuItemEn);

        jMenuBar.add(jMenuUserInfo);
        jMenuBar.add(jMenuSetting);
        jMenuBar.add(jMenuHelp);
        jMenuBar.add(Box.createHorizontalGlue());
        jMenuBar.add(jMenuLanguage);
        jMenuBar.setVisible(true);
        fenetre.setJMenuBar(jMenuBar);

        welcomeDialog = new JDialog(fenetre, bundle.getString("welcomeLabel"));
        JLabel welcomeLabel = new JLabel(bundle.getString("welcomeUserLabel"));
        JLabel loginLabel = new JLabel(bundle.getString("userNameLabel"));
        JLabel loginMessageLabel = new JLabel();
        loginMessageLabel.setBackground(Color.yellow);

        JTextField loginTextField = new JTextField();
        loginTextField.setSize(121, 20);
        loginTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                String value = loginTextField.getText();
                int l = value.length();

                if (e.getKeyChar() >= 'a' && e.getKeyChar() <= 'z' || e.getKeyChar() >= 'A' && e.getKeyChar() <= 'Z' || e.getKeyCode() == 8) {
                    loginTextField.setEditable(true);
                    loginMessageLabel.setText(bundle.getString("welcomeUserLabel") + "");

                } else {
                    loginTextField.setEditable(false);
                    loginMessageLabel.setText(bundle.getString("welcomeMessageLabel"));
                    loginTextField.setFocusable(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        loginLabel.setLabelFor(loginTextField);
        JButton loginButton = new JButton(bundle.getString("loginButton"));
        loginButton.addActionListener(e -> {
            System.out.println("loginTextField : " + loginTextField.getText());
        });

        GridLayout gridLayout = new GridLayout(3, 1);
        gridLayout.setHgap(40);
        gridLayout.setVgap(40);

        welcomeDialog.setLayout(gridLayout);
        welcomeDialog.add(welcomeLabel, JLabel.CENTER);
        loginLabel.setSize(400, 100);


        JPanel jPanelWelcomeDialog = new JPanel();
        jPanelWelcomeDialog.setLayout(new GridLayout(1, 3));


        jPanelWelcomeDialog.add(loginLabel);
        jPanelWelcomeDialog.add(loginTextField);
        jPanelWelcomeDialog.add(loginButton);


        welcomeDialog.add(jPanelWelcomeDialog);
        welcomeDialog.add(loginMessageLabel);
        welcomeDialog.setVisible(false);
        welcomeDialog.setFocusableWindowState(true);
        welcomeDialog.setSize(400, 200);
        welcomeDialog.setResizable(false);
        welcomeDialog.setLocationRelativeTo(fenetre);


        fenetre.pack();
        fenetre.setResizable(true);
        fenetre.setLocationRelativeTo(null);
        fenetre.setVisible(true);
        fenetre.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fenetre.requestFocus();
        fenetre.addKeyListener(this);
        fenetre.addWindowListener(this);
        Image img = toolkit.getImage(System.getProperty("user.home") + "\\IdeaProjects\\Flappy_eesc\\src\\main\\resources\\bird.png");
        fenetre.setIconImage(img);

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
        this.addComponentListener(this);


        //On indique que le raffraichissement de l'ecran doit être fait manuellement.
        createBufferStrategy(2);
        setIgnoreRepaint(true);
        this.setFocusable(false);


        demarrer();
    }

    private void initComponents() {


    }

    void playSound(String soundFile) {
        File f = new File("src/main/resources/bk_island_ambience_02.wav" + soundFile);
        AudioInputStream audioIn = null;
        try {
            audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());

            Clip clip = null;

            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.loop(1);//Clip.LOOP_CONTINUOUSLY);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialiser() {
        playSound("");
        // Si cest la premiere initialisation
        etat = Etat.EN_COURS;
        point = 0;
        if (oiseau == null || windowsResized) {
            oiseau = new Oiseau(hauteurEcran);
            listDeplacable = new ArrayList<>();
            listMoustique = new ArrayList<>();
            listSprite = new ArrayList<>();

            for (int i = 0; i < 7; i++) {

                tuyauLargeur = (tuyauLargeur == largeurEcran + i * generateRandomNumber(100, 200)) ? (largeurEcran + i * generateRandomNumber(100, 200)) : (largeurEcran + i * generateRandomNumber(100, 200));
                //tuyauHauteur = (tuyauLargeur == hauteurEcran - i * generateRandomNumber(5, 50)) ? hauteurEcran - i * generateRandomNumber(5, 50) : hauteurEcran - i * generateRandomNumber(5, 50);

                tuyau = new Tuyau(121 + generateRandomNumber(70, 200), hauteurEcran, tuyauLargeur);
                listDeplacable.add(tuyau);
                listSprite.add(tuyau);
            }


            listDeplacable.add(oiseau);

            listSprite.add(oiseau);

            //tuyau = new Tuyau(300, hauteurEcran, largeurEcran + 50);
            nombreDeMoustique = generateRandomNumber(20, 30);
            for (int i = 0; i < nombreDeMoustique; i++) {
                moustique = new Moustique(largeurEcran, hauteurEcran);
                listDeplacable.add(moustique);
                listMoustique.add(moustique);
                point++;
            }
            for (int i = 0; i < 3; i++) {
                OiseauMechant oiseauMechant = new OiseauMechant(largeurEcran, hauteurEcran);
                listDeplacable.add(oiseauMechant);
                listSprite.add(oiseauMechant);

            }
            for (int i = 0; i < 10; i++) {
                Nuage nuage = new Nuage(largeurEcran, hauteurEcran);
                listDeplacable.add(nuage);
                listSprite.add(nuage);

            }
        } else {
            nombreDeMoustique = generateRandomNumber(20, 30);
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

            Image img = toolkit.getImage(System.getProperty("user.home") + "\\IdeaProjects\\Flappy_eesc\\src\\main\\resources\\background.jpg");
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
            dessin.drawString(MessageFormat.format(bundle.getString("mosquitosNumberLabel"), point), largeurEcran - 221, 50);


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
                        if (listDeplacable.indexOf(deplacable) % 2 != 0)
                            deplacable.deplacer(largeurEcran, hauteurEcran);
                        //deplacable.deplacer(largeurEcran, hauteurEcran);
                    }
                    for (Deplacable deplacable : listDeplacable) {
                        deplacable.deplacer(largeurEcran, hauteurEcran);
                    }
                    for (int i = 0; i < listSprite.size(); i++) {
                        if (listSprite.get(i) instanceof Tuyau || listSprite.get(i) instanceof OiseauMechant) {
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

                    dessin.drawString(bundle.getString("pauseLabel"), largeurEcran / 2 - textWidth / 2, hauteurEcran / 2);
                } else if (etat == Etat.PERDU) {
                    dessin.setColor(Color.yellow);
                    dessin.setFont(alert);
                    dessin.drawString(bundle.getString("lostLabel"), largeurEcran / 2 - textWidth / 2, hauteurEcran / 2);
                } else if (etat == Etat.GAGNE) {
                    nombreDeMoustique = generateRandomNumber(20, 30);
                    point = nombreDeMoustique;
                    dessin.setColor(Color.yellow);
                    dessin.setFont(alert);
                    dessin.drawString(bundle.getString("wonLebel"), largeurEcran / 2 - textWidth / 2, hauteurEcran / 2);
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
        if (e.getKeyCode() == 39) {
            oiseau.setX(oiseau.getX() + 10);
            //System.out.println("go right");
        }
        if (e.getKeyCode() == 37) {
            oiseau.setX(oiseau.getX() - 10);
            //System.out.println("go left");
        }
        if (e.getKeyCode() == 38) {
            oiseau.setY(oiseau.getY() - 30);
            //System.out.println("go up");
        }
        if (e.getKeyCode() == 40) {
            oiseau.setY(oiseau.getY() + 10);
            //System.out.println("go down");
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
            etat = etat == Etat.PAUSE ? Etat.EN_COURS : Etat.PAUSE;
        }

    }

    boolean dragged = false;

    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println("X: " + e.getLocationOnScreen().getX() + "Y: " + e.getLocationOnScreen().getY());
        oiseau.setY(oiseau.getY() - 70);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if ((oiseau.getX() <= e.getX() && oiseau.getX() <= oiseau.getX() + 40) && (oiseau.getY() <= e.getY() && oiseau.getY() <= oiseau.getY() + 40)) {
            dragged = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        dragged = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (dragged) {
            oiseau.setY(e.getY() - 20);
            oiseau.setX(e.getX() - 20);
        }
        //System.out.println("X: " + e.getX() + "Y: " + e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() == -1) {
            oiseau.setY(oiseau.getY() - 40);

        }
        if (e.getWheelRotation() == 1) {
            oiseau.setY(oiseau.getY() + 10);
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        //System.out.println("reinitializer");
        largeurEcran = this.getWidth();
        hauteurEcran = this.getHeight();
        windowsResized = true;
        initialiser();
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {
        System.out.println("opened");
        welcomeDialog.setModal(true);
        welcomeDialog.setVisible(true);
        welcomeDialog.setFocusableWindowState(true);
    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
