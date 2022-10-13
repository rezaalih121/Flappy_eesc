import java.util.Collections;
import java.util.HashSet;

public class User {
    protected String userName;

    protected HashSet<UserHistory> userHistoriesList = new HashSet<UserHistory>();

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

    public HashSet<UserHistory> getUserHistoriesList() {
        return userHistoriesList;
    }

    public void deleteUserHistoriesList() {
        this.userHistoriesList = new HashSet<UserHistory>();
    }
}
