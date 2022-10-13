import java.util.Locale;
import java.util.ResourceBundle;

public class Setting {
    protected ResourceBundle bundle;
    protected String[] gameLevels;
    protected String theme;

    public Setting(ResourceBundle bundle, String theme) {
        this.bundle = bundle;
        this.gameLevels = bundle.getStringArray("gameLevels");
        this.theme = theme;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public String[] getGameLevels() {
        return gameLevels;
    }

    public void setGameLevels(String[] gameLevels) {
        this.gameLevels = gameLevels;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
