import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class User implements Serializable {
    protected String userName;

    protected ArrayList<UserHistory> userHistoriesList = new ArrayList<UserHistory>();

    public User(String userName) {

        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void addUserHistory(UserHistory... userHistories) {

        Collections.addAll(userHistoriesList, userHistories);
    }

    public ArrayList<UserHistory> getUserHistoriesList() {
        return userHistoriesList;
    }

    public void deleteUserHistoriesList() {
        this.userHistoriesList = new ArrayList<UserHistory>();
    }

    public void setUserHistoriesList(ArrayList<UserHistory> userHistoriesList) {
        this.userHistoriesList = userHistoriesList;
    }
}
