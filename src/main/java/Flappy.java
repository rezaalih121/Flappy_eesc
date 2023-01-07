import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLOutput;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Flappy extends Canvas implements KeyListener, EventListener, MouseListener, MouseMotionListener, MouseWheelListener, ComponentListener, WindowListener {

    protected User curentUser;
    protected static int largeurEcran = 600;
    protected static int hauteurEcran = 600;
    protected boolean windowsResized = false;
    protected Oiseau oiseau;
    protected Tuyau tuyau;
    protected Moustique moustique;
    protected int tuyauLargeur;
    protected long point = 0;
    protected int nombreDeMoustique;
    protected int nombreDeMechantOiseau;
    protected int nombreDeNuage;
    protected int gameLevel = 0;
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    LocalDateTime localDateTime = LocalDateTime.now();
    protected int textWidth = 0;
    protected Toolkit toolkit = Toolkit.getDefaultToolkit();
    protected ArrayList<Deplacable> listDeplacable = new ArrayList<>();
    protected ArrayList<Sprite> listSprite = new ArrayList<>();
    protected ArrayList<Moustique> listMoustique = new ArrayList<>();
    private static final Random RANDOMISER = new Random();
    protected Etat etat = Etat.PAUSE;
    protected JDialog welcomeDialog, userInfoDialog, settingDialog;
    protected XmlDbFileHandler xmlDbFileHandler;

    protected UserDataFileHandler userDataFileHandler;
    protected Image backgroundImage;
    protected JMenuBar jMenuBar;
    protected String filesPath = "src/main/resources/";
    protected SoundHandler soundHandler = new SoundHandler(filesPath);
    //loading locale language
    private static final int DEFAULT_LOCALE = 0;
    private ResourceBundle bundle;
    public static Locale locale;
    public static final Locale[] supportedLocales = {
            new Locale("en", "US"),
            new Locale("fr", "FR")
    };
    private int currentTheme = 0;
    private int currentLevel = 0;

    //---------- Function to create a random number between 2 specified numbers ---------
    public static int generateRandomNumber(int from, int to) {
        return RANDOMISER.nextInt((to + 1) - from) + from;
    }

    //----------- Function to set windows theme  ------------
    public void setWindowsTheme(int theme) {
        try {
            currentTheme = theme;
            if (theme == 0) {
                UIManager.setLookAndFeel(new FlatLightLaf());
                backgroundImage = toolkit.getImage(this.getClass().getClassLoader().getResource("background.jpg"));
            } else if (theme == 1) {
                UIManager.setLookAndFeel(new FlatDarkLaf());
                backgroundImage = toolkit.getImage(this.getClass().getClassLoader().getResource("NightBackground.jpg"));
            } else if (theme == 2) {
                UIManager.setLookAndFeel(new FlatDarculaLaf());
                backgroundImage = toolkit.getImage(this.getClass().getClassLoader().getResource("WinterBackground.jpg"));
            }
            jMenuBar.repaint();
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
    }
    //----------- Function to set the current game level ------------
    public void setGameLevel(int level) {
        currentLevel = level;
        try {
            gameLevel = level;
            if (level == 0) {
                nombreDeMoustique = generateRandomNumber(10, 20);
                nombreDeMechantOiseau = 3;
                nombreDeNuage = 10;
                UIManager.setLookAndFeel(new FlatLightLaf());
                backgroundImage = toolkit.getImage(this.getClass().getClassLoader().getResource("background.jpg"));
            } else if (level == 1) {
                nombreDeMoustique = generateRandomNumber(20, 30);
                nombreDeMechantOiseau = 5;
                nombreDeNuage = 14;
                UIManager.setLookAndFeel(new FlatDarculaLaf());
                backgroundImage = toolkit.getImage(this.getClass().getClassLoader().getResource("NightBackground.jpg"));

            } else if (level == 2) {
                nombreDeMoustique = generateRandomNumber(30, 40);
                nombreDeMechantOiseau = 7;
                nombreDeNuage = 21;
                UIManager.setLookAndFeel(new FlatDarculaLaf());
                backgroundImage = toolkit.getImage(this.getClass().getClassLoader().getResource("WinterBackground.jpg"));

            } else if (level == 3) {
                nombreDeMoustique = generateRandomNumber(40, 50);
                nombreDeMechantOiseau = 14;
                nombreDeNuage = 27;
                UIManager.setLookAndFeel(new FlatDarculaLaf());
                backgroundImage = toolkit.getImage(this.getClass().getClassLoader().getResource("WinterBackground.jpg"));
            }
            SwingUtilities.updateComponentTreeUI(this);
            this.repaint();
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
    }

    public Flappy() throws InterruptedException {

        
        FlatLightLaf.setup();

        //----------- Set the default local language ------------
        for (Locale locale1 : supportedLocales) {
            if (locale1.getLanguage().equals(Locale.getDefault().getLanguage())) {
                locale = locale1;
                break;
            } else {
                locale = supportedLocales[DEFAULT_LOCALE];
            }
        }

        bundle = ResourceBundle.getBundle("LanguageResource", locale);

        //----------- Loading application Database from both an xml and a serialised file ------------

        xmlDbFileHandler = new XmlDbFileHandler(bundle);
        userDataFileHandler =new UserDataFileHandler();

        //----------- Setting up main window ------------
        JFrame fenetre = new JFrame(bundle.getString("windowsTitle"));
        //On récupère le panneau de la fenetre principale
        JPanel panneau = (JPanel) fenetre.getContentPane();
        //On définie la hauteur / largeur de l'écran
        panneau.setPreferredSize(new Dimension(largeurEcran, hauteurEcran));
        setBounds(0, 0, largeurEcran, hauteurEcran);
        //On ajoute cette classe (qui hérite de Canvas) comme composant du panneau principal
        panneau.add(this);
        backgroundImage = toolkit.getImage(this.getClass().getClassLoader().getResource("background.jpg"));
        Image img = toolkit.getImage(this.getClass().getClassLoader().getResource("bird.png"));


        //----------- Setting up window's menu ------------
        jMenuBar = new JMenuBar();
        addMouseEnteredExitedListener(jMenuBar);

        //----------- Setting up user game history page ------------
        userInfoDialog = new JDialog(fenetre, bundle.getString("userInfoMenu"));
        userInfoDialog.setVisible(false);
        userInfoDialog.setFocusableWindowState(true);
        userInfoDialog.setSize(500, 500);
        userInfoDialog.setResizable(false);
        userInfoDialog.setLocationRelativeTo(fenetre);
        userInfoDialog.setLayout(new BorderLayout());

        JLabel userInfoLabel = new JLabel();
        userInfoLabel.setFont(new Font("Arial", Font.BOLD, 21));

        userInfoDialog.add(FormCreator.generateRow(userInfoLabel, 10, 10, 10, 10, FormCreator.ALIGN_CENTER), BorderLayout.NORTH);
        //----------- Loading a table with user game history ------------
        TableHandler tableHandler = new TableHandler();
        tableHandler.setEnabled(true);

        JScrollPane jScrollPane = new JScrollPane(tableHandler);
        JPanel jPanelTable = new JPanel(new BorderLayout());
        jPanelTable.add(FormCreator.generateRow(jScrollPane, 0, 0, 0, 0, 0), BorderLayout.CENTER);
        userInfoDialog.add(FormCreator.generateRow(jPanelTable, 10, 10, 10, 10, FormCreator.ALIGN_CENTER), BorderLayout.CENTER);

        //----------- A button to delete all user game history data ------------
        JButton deleteUserInfoButton = new JButton(bundle.getString("deleteUser"));
        deleteUserInfoButton.addActionListener(e -> {
            String[] choices = bundle.getStringArray("deleteUserRespond");
            String defaultChoice = choices[0];
            if (JOptionPane.showOptionDialog(userInfoDialog, bundle.getString("deleteUserMessage"), bundle.getString("deleteUserMessage"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, defaultChoice) == JOptionPane.YES_OPTION) {
                curentUser.deleteUserHistoriesList();
                xmlDbFileHandler.deleteUserGameHistory(curentUser);
                userDataFileHandler.deleteUserGameHistory(curentUser);
                curentUser = xmlDbFileHandler.getUserFromXmlFile(curentUser.userName);
                curentUser = userDataFileHandler.getUserFromFileStream(curentUser.userName);
                curentUser.getUserHistoriesList();
                //System.out.println(curentUser.getUserHistoriesList().size());
                tableHandler.setCurentUser(curentUser);
                tableHandler.setBundle(bundle);
                tableHandler.setTable();
                tableHandler.repaint();
                userInfoDialog.repaint();
            }
        });
        userInfoDialog.add(FormCreator.generateRow(deleteUserInfoButton, 10, 10, 10, 10, FormCreator.ALIGN_CENTER), BorderLayout.SOUTH);
        deleteUserInfoButton.setAlignmentX(CENTER_ALIGNMENT);
        
        JMenu jMenuUserInfo = new JMenu(bundle.getString("userInfoMenu"));
        addMouseEnteredExitedListener(jMenuBar);
        jMenuUserInfo.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tableHandler.setCurentUser(curentUser);
                tableHandler.setBundle(bundle);
                tableHandler.setTable();
                userInfoLabel.repaint();
                deleteUserInfoButton.repaint();
                userInfoDialog.repaint();
                userInfoDialog.setVisible(true);
            }
            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                etat = Etat.PAUSE;
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        JMenu jMenuSetting = new JMenu(bundle.getString("settingLabel"));
        addMouseEnteredExitedListener(jMenuSetting);
        JMenu jMenuItemTheme = new JMenu(bundle.getString("themeLabel"));
        addMouseEnteredExitedListener(jMenuItemTheme);
        jMenuItemTheme.setMnemonic(KeyEvent.VK_S);
        for (int i = 0; i < bundle.getStringArray("themeList").length; i++) {
            JMenuItem jSubMenuTheme = new JMenuItem(bundle.getStringArray("themeList")[i]);
            addMouseEnteredExitedListener(jSubMenuTheme);
            int finalI = i;
            jSubMenuTheme.addActionListener(e -> {
                setWindowsTheme(finalI);
            });
            jMenuItemTheme.add(jSubMenuTheme);
        }
        jMenuSetting.add(jMenuItemTheme);

        jMenuSetting.addSeparator();

        JMenu jMenuItemLevel = new JMenu(bundle.getString("levelLabel"));
        addMouseEnteredExitedListener(jMenuItemLevel);
        jMenuItemTheme.setMnemonic(KeyEvent.VK_S);
        for (int i = 0; i < bundle.getStringArray("gameLevelList").length; i++) {
            JMenuItem jSubMenuLevel = new JMenuItem(bundle.getStringArray("gameLevelList")[i]);
            addMouseEnteredExitedListener(jSubMenuLevel);
            int finalI = i;
            jSubMenuLevel.addActionListener(e -> {
                setGameLevel(finalI);
            });
            jMenuItemLevel.add(jSubMenuLevel);
        }

        jMenuSetting.add(jMenuItemLevel);
        JMenu jMenuHelp = new JMenu(bundle.getString("helpMenu"));
        addMouseEnteredExitedListener(jMenuHelp);
        JMenuItem gameControlsMenu = new JMenuItem(bundle.getString("controlKeys"));
        addMouseEnteredExitedListener(gameControlsMenu);
        JMenuItem aboutUsMenu = new JMenuItem(bundle.getString("aboutUsMenu"));
        addMouseEnteredExitedListener(aboutUsMenu);

        jMenuHelp.add(gameControlsMenu);

        jMenuHelp.add(aboutUsMenu);
        aboutUsMenu.addActionListener(e -> {
            JOptionPane.showMessageDialog(fenetre, bundle.getString("windowsTitle") + " " + bundle.getString("aboutUs"), bundle.getString("aboutUsMenu"), JOptionPane.INFORMATION_MESSAGE);
        });
        gameControlsMenu.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    fenetre,
                    bundle.getString("controlKeys") + "\n" +
                            bundle.getString("jumpKeyLabel") + "\n" +
                            bundle.getString("resetKeyLabel") + "\n" +
                            bundle.getString("mouseDragLabel") + "\n" +
                            bundle.getString("mouseWheelLabel") + "\n" +
                            bundle.getString("arrowsKeysLabel") + "\n",
                    bundle.getString("helpMenu"), JOptionPane.INFORMATION_MESSAGE
            );
        });
        JMenu jMenuLanguage = new JMenu(bundle.getString("languageMenu") + " : " + locale.getDisplayLanguage());
        addMouseEnteredExitedListener(jMenuLanguage);
        JMenuItem jMenuItemEn = new JMenuItem(supportedLocales[0].getDisplayLanguage());
        addMouseEnteredExitedListener(jMenuItemEn);
        JMenuItem jMenuItemFr = new JMenuItem(supportedLocales[1].getDisplayLanguage());
        addMouseEnteredExitedListener(jMenuItemFr);
        jMenuItemFr.addActionListener(e -> {

            locale = supportedLocales[1];

            bundle = ResourceBundle.getBundle("LanguageResource_fr_FR");

            //System.out.println("FR : " + bundle.getLocale().getDisplayLanguage() + " local : " + locale.getDisplayLanguage());

            jMenuLanguage.setText(bundle.getString("languageMenu") + " : " + locale.getDisplayLanguage());
            jMenuUserInfo.setText(bundle.getString("userInfoMenu"));
            jMenuSetting.setText(bundle.getString("settingLabel"));
            jMenuHelp.setText(bundle.getString("helpMenu"));
            fenetre.setTitle(bundle.getString("windowsTitle"));
            jMenuBar.repaint();
            userInfoDialog.repaint();
            deleteUserInfoButton.repaint();
            jMenuItemLevel.repaint();
            userInfoLabel.repaint();

        });
        jMenuItemEn.addActionListener(e -> {

            locale = supportedLocales[0];

            bundle = ResourceBundle.getBundle("LanguageResource_en_US");

            //System.out.println("EN : " + bundle.getLocale().getDisplayLanguage() + " local : " + locale.getDisplayLanguage());

            jMenuLanguage.setText(bundle.getString("languageMenu") + " : " + locale.getDisplayLanguage());
            jMenuUserInfo.setText(bundle.getString("userInfoMenu"));
            jMenuSetting.setText(bundle.getString("settingLabel"));
            jMenuHelp.setText(bundle.getString("helpMenu"));
            fenetre.setTitle(bundle.getString("windowsTitle"));
            fenetre.repaint();
            jMenuBar.repaint();
            userInfoDialog.repaint();
            deleteUserInfoButton.repaint();
            jMenuItemLevel.repaint();
            userInfoLabel.repaint();
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
        welcomeDialog.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel(bundle.getString("welcomeUserLabel"));
        welcomeLabel.setFont(new Font("Calibri", Font.BOLD, 12));
        JLabel loginLabel = new JLabel(bundle.getString("userNameLabel"));
        JLabel loginMessageLabel = new JLabel();

        loginMessageLabel.setBackground(Color.yellow);

        JTextField loginTextField = new JTextField();
        loginTextField.setMaximumSize(new Dimension(121, 20));
        JButton loginButton = new JButton(bundle.getString("loginButton"));
        JButton exitButton = new JButton(bundle.getString("loginExit"));
        loginTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginButton.setFocusable(true);
                }
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
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginButton.setFocusable(true);
                }
            }
        });

        loginLabel.setLabelFor(loginTextField);
        exitButton.addActionListener(e -> {
            if (JOptionPane.showOptionDialog(welcomeDialog, bundle.getString("exitAlertMessage"), bundle.getString("exitAlertMessage"), JOptionPane.YES_NO_OPTION, JOptionPane.YES_NO_OPTION, null, bundle.getStringArray("deleteUserRespond"), 0) == 0) {
                welcomeDialog.setVisible(false);
                fenetre.dispose();
                System.exit(0);
            } else {

            }
        });
        loginButton.addActionListener(e -> {

            if (loginTextField.getText().length() > 0) {
                etat = Etat.EN_COURS;

                curentUser = xmlDbFileHandler.getUserFromXmlFile(loginTextField.getText());
                curentUser = userDataFileHandler.getUserFromFileStream(loginTextField.getText());

                userInfoLabel.setText(bundle.getString("userNameLabel") + " : " + curentUser.getUserName());
                welcomeDialog.setVisible(false);
                //if (JOptionPane.showOptionDialog(welcomeDialog, bundle.getString("welcomeLabel") + "  " + curentUser.getUserName(), bundle.getString("welcomeLabel"), JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null) == JOptionPane.OK_OPTION)
                initialiser();

            } else {
                JOptionPane.showMessageDialog(welcomeDialog, bundle.getString("welcomeUserLabel"), bundle.getString("welcomeUserLabel"), JOptionPane.CLOSED_OPTION);
            }


        });

        welcomeDialog.setIconImage(img);
        Box boxFormulaire = Box.createVerticalBox();
        welcomeDialog.add(boxFormulaire, BorderLayout.CENTER);
        boxFormulaire.add(FormCreator.generateRow(welcomeLabel, 10, 10, 10, 10, FormCreator.ALIGN_CENTER));
        boxFormulaire.add(FormCreator.generateField(loginLabel, loginTextField, 40), CENTER_ALIGNMENT);
        boxFormulaire.add(FormCreator.generateRow(new JLabel(), 10, 10, 10, 10, FormCreator.ALIGN_CENTER));

        boxFormulaire.add(FormCreator.generateField(loginButton, exitButton, 20), CENTER_ALIGNMENT);
        boxFormulaire.add(FormCreator.generateRow(loginMessageLabel, 10, 10, 10, 10, FormCreator.ALIGN_CENTER));


        welcomeDialog.setSize(350, 200);
        welcomeDialog.setVisible(false);
        welcomeDialog.setFocusableWindowState(true);
        welcomeDialog.setResizable(false);
        welcomeDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        welcomeDialog.setLocationRelativeTo(fenetre);


        fenetre.pack();
        fenetre.setResizable(true);
        fenetre.setLocationRelativeTo(null);
        fenetre.setVisible(true);
        fenetre.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fenetre.requestFocus();
        fenetre.addKeyListener(this);
        fenetre.addWindowListener(this);
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

    // TODO loading images using bufferd Image later 
    public BufferedImage loadImage(String fileName) {

        BufferedImage buff = null;
        try {
            buff = ImageIO.read(getClass().getResourceAsStream(fileName));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return buff;

    }

    public void initialiser() {
        soundHandler.playSound("startSound.wav", false);
        soundHandler.playSound("mosquiteSound.wav" , false );
        setWindowsTheme(currentTheme);
        setGameLevel(currentLevel);
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



            for (int i = 0; i < nombreDeMechantOiseau; i++) {
                OiseauMechant oiseauMechant = new OiseauMechant(largeurEcran, hauteurEcran);
                listDeplacable.add(oiseauMechant);
                listSprite.add(oiseauMechant);

            }
            for (int i = 0; i < nombreDeNuage; i++) {
                Nuage nuage = new Nuage(largeurEcran, hauteurEcran);
                listDeplacable.add(nuage);
                listSprite.add(nuage);

            }
            for (int i = 0; i < nombreDeMoustique; i++) {
                moustique = new Moustique(largeurEcran, hauteurEcran);
                listDeplacable.add(moustique);
                listMoustique.add(moustique);
                point++;
            }
        } else {

            setWindowsTheme(currentTheme);
            setGameLevel(currentLevel);
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
        etat = Etat.PAUSE;
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

            dessin.drawImage(backgroundImage, 0, 0, largeurEcran, hauteurEcran, this);


            //oiseau.dessiner(dessin, this);
            for (Sprite sprite : listSprite) {
                sprite.dessiner(dessin, this);
            }
            for (Moustique moustique1 : listMoustique) {
                moustique1.dessiner(dessin, this);
            }
            //affichage HUD
            if (currentLevel == 1 || currentLevel == 2 || currentLevel == 3 || currentTheme == 1 || currentTheme == 2) {
                dessin.setColor(Color.WHITE);
            } else {
                dessin.setColor(Color.BLACK);
            }

            dessin.setFont(police);
            dessin.drawString(MessageFormat.format(bundle.getString("mosquitosNumberLabel"), point), largeurEcran - 221, 50);


            if (etat == Etat.EN_COURS) {
                if (oiseau.getY() > hauteurEcran - oiseau.getLargeur()) {
                    // si jamais l oiseau est tombe par terre
                    //System.out.println("perdu");
                    etat = Etat.PERDU;
                    soundHandler.playSound("sl02_wav.wav", false);

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
                                soundHandler.playSound("hit_someone.wav", false);
                            }
                        }
                    }

                    for (int i = 0; i < listMoustique.size(); i++) {

                        if (Sprite.testCollision(oiseau, listMoustique.get(i))) {
                            point--;
                            soundHandler.playSound("eatingMostique.wav", false);
                            listMoustique.remove(i);
                            if (point == 0) {
                                etat = Etat.GAGNE;
                                soundHandler.playSound("won_wav1.wav", false);
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
                    //soundHandler.playSound("perdu1_wav.wav");
                    dessin.setColor(Color.yellow);
                    dessin.setFont(alert);
                    dessin.drawString(bundle.getString("lostLabel"), largeurEcran / 2 - textWidth / 2, hauteurEcran / 2);


                } else if (etat == Etat.GAGNE) {

                    dessin.setColor(Color.yellow);
                    dessin.setFont(alert);
                    dessin.drawString(bundle.getString("wonLebel"), largeurEcran / 2 - textWidth / 2, hauteurEcran / 2);

                }
            }
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
            /*if (oiseau.getY() > hauteurEcran / 2 )
                soundHandler.playSound("happyBird.wav", false);*/
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            setGameLevel(gameLevel);
            if (Etat.GAGNE == etat) {
                curentUser.addUserHistory(new UserHistory(bundle.getString("wonLebel"), dateTimeFormatter.format(localDateTime), bundle.getStringArray("gameLevelList")[gameLevel]));
                if (currentLevel < 3)
                    currentLevel++;
            } else if (Etat.PERDU == etat) {
                curentUser.addUserHistory(new UserHistory(bundle.getString("lostLabel"), dateTimeFormatter.format(localDateTime), bundle.getStringArray("gameLevelList")[gameLevel]));
            }

            setWindowsTheme(currentTheme);
            setGameLevel(currentLevel);
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
        setGameLevel(currentLevel);
        setWindowsTheme(currentTheme);
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
        welcomeDialog.setModal(true);
        welcomeDialog.setVisible(true);
        welcomeDialog.setFocusableWindowState(true);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        xmlDbFileHandler.updateUserInToXmlFile(curentUser);
        userDataFileHandler.saveNewUserDataToFileStream(curentUser);
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

    protected void addMouseEnteredExitedListener(Component component) {
        component.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                etat = Etat.PAUSE;
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }
}