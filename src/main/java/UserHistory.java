import java.io.Serializable;

public class UserHistory implements Serializable {
    protected String gameResult;
    protected String datePlayed;
    protected String gameLevel;

    public UserHistory(String gameResult, String datePlayed, String gameLevel) {

        this.gameResult = gameResult;
        this.datePlayed = datePlayed;
        this.gameLevel = gameLevel;
    }

    public String getGameResult() {
        return gameResult;
    }

    public void setGameResult(String gameResult) {
        this.gameResult = gameResult;
    }

    public String getDatePlayed() {
        return datePlayed;
    }

    public void setDatePlayed(String datePlayed) {
        this.datePlayed = datePlayed;
    }

    public String getGameLevel() {
        return gameLevel;
    }

    public void setGameLevel(String gameLevel) {
        this.gameLevel = gameLevel;
    }
    public Object[] getRowTable(){
        return new Object[]{
                getGameResult(),
                getGameLevel(),
                getDatePlayed(),
                ""
        };
    }
}
