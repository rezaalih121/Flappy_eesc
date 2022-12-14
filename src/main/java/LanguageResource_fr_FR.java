import java.util.ListResourceBundle;

public class LanguageResource_fr_FR extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"userInfoMenu", "Infos utilisateur"},
                {"userHistoryTablaHeader", new String[]{"État du jeu", "Niveau de jeu", "Date & heure"}},
                {"deleteUser", "Supprimer vos données utilisateur"},
                {"deleteUserMessage", "Voulez-vous vraiment supprimer votre historique de jeu?!?"},
                {"deleteUserRespond", new String[]{"Oui", "Non"}},
                {"helpMenu", "Aide"},
                {"aboutUs", "développé par Reza Ai."},
                {"aboutUsMenu", "Qui sommes-nous"},
                {"controlKeys", "Touches de contrôle de base"},
                {"jumpKeyLabel", "Appuyez sur la touche Espace pour sauter"},
                {"resetKeyLabel", "Appuyez sur Entrée pour arrêter le jeu"},
                {"mouseDragLabel", "Utiliser la souris pour faire glisser Happy Bird"},
                {"mouseWheelLabel", "Utilisez la molette de la souris pour déplacer Happy Bird vers le HAUT et vers le BAS"},
                {"arrowsKeysLabel", "Utilisez les touches fléchées pour déplacer Happy Bird"},
                {"languageMenu", "Langue"},
                {"userNameLabel", "Nom d'utilisateur"},
                {"settingLabel", "Paramètre"},
                {"themeLabel", "Choisissez votre thème"},
                {"levelLabel", "Choisir le niveau de jeu"},
                {"themeList", new String[]{"Lumière", "Sombre", "Hiver"}},
                {"gameLevelList", new String[]{"Premier niveau", "Deuxième niveau", "Troisième niveau", "Niveau final"}},
                {"windowsTitle", "Happy Bird (Oiseau Joyeux)"},
                {"mosquitosNumberLabel", "{0,number} moustiques restants"},
                {"lostLabel", "PERDU"},
                {"wonLebel", "GAGNÉ"},
                {"pauseLabel", "PAUSE"},
                {"welcomeLabel", "Bienvenue dans le jeu Happy Bird (Oiseau Joyeux)"},
                {"welcomeUserLabel", "Entrez votre nom d'utilisateur ou un nouveau nom d'utilisateur pour vous inscrire"},
                {"welcomeMessageLabel", "Tapez uniquement des lettres (a-z or A-Z) pour le nom d'utilisateur."},
                {"loginButton", "Démarrer"},
                {"loginExit", "Fermer"},
                {"exitAlertMessage", "Voulez-vous vraiment quitter?!? :-("}
        };
    }
}
