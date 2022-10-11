import java.util.ListResourceBundle;

public class LanguageResource_en_US extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"userInfoMenu", "User Info"},
                {"userLastPlayLabel", "Last game played"},
                {"userGameLevel", "User game level"},
                {"userLastPlayDate", "Date played"},
                {"deleteUser", "Delete your user data"},
                {"helpMenu", "Help"},
                {"controlKeys", "Basic Control Keys"},
                {"jumpKeyLabel", "Press Space key to jump"},
                {"resetKeyLabel", "Press Enter to rest game"},
                {"mouseDragLabel", "Use mouse to drag Happy Bird"},
                {"mouseWheelLabel", "Use mouse wheel to move Happy Bird UP and DOWN"},
                {"arrowsKeysLabel", "Use Arrows keys to move Happy Bird"},
                {"languageMenu", "Language"},
                {"userNameLabel", "User Name"},
                {"settingLabel", "Setting"},
                {"themeLabel", "Choose your theme"},
                {"themeList", new String[]{"Light", "Dark", "Winter"}},
                {"levelLabel", "Choose game level"},
                {"gameLevelList", new String[]{"First Level", "Second Level", "Third Level", "Final Level"}},
                {"windowsTitle", "Happy Bird"},
                {"mosquitosNumberLabel", "{0,number} mosquitos left"},
                {"lostLabel", "LOST"},
                {"wonLebel", "WON"},
                {"pauseLabel", "PAUSE"},
                {"welcomeLabel", "Welcome To Happy Bird Game"},
                {"welcomeUserLabel", "Enter your Username or a new Username to register please"},
                {"welcomeMessageLabel", "Type only letters (a-z or A-Z) for username."},
                {"loginButton", "Start"}
        };
    }
}
