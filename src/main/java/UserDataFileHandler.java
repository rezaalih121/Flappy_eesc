import javax.naming.Context;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class UserDataFileHandler {

    ArrayList<User> usersList = new ArrayList<>();

    ArrayList<UserHistory> userHistoriesList = new ArrayList<>();
    FileOutputStream userDataFileOutputStream;
    FileInputStream userDataFileInputStream;
    ObjectInputStream ois = null;
    ObjectOutputStream oos = null;
    boolean isNewUser = false;

    public UserDataFileHandler() {
        try {

            if (fileExists("userDataFile.raidata")) {
                this.userDataFileInputStream = new FileInputStream("userDataFile.raidata");
                loadDataFromFileStream();
            } else {
                isNewUser = true;
                saveNewUserDataToFileStream(new User("FirstUserToCreateDataFile"));
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not exist application opened for the first time");
            throw new RuntimeException(e);
        }
    }

    public boolean fileExists(String filename) {
        File file = new File(filename);
        if (file == null || !file.exists()) {
            return false;
        }
        return true;
    }

    protected void loadDataFromFileStream() {
        try {
            ois = new ObjectInputStream(userDataFileInputStream);
            usersList = (ArrayList<User>) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            System.out.println("Première fois qu'on ouvre l'application");
        } catch (IOException e) {
            System.out.println("Impossible d'ouvrir le fichier : " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    protected User getUserFromFileStream(String userName) {
        User currentUser = null;
        for (User user : usersList) {
            //System.out.println("in file "+user.getUserName() + user.getUserHistoriesList().size());
            if (user.getUserName().equals(userName))
                currentUser = user;
        }
        if (currentUser != null) {
            return currentUser;
        } else {
            isNewUser = true;
            currentUser = new User(userName);
        }
        System.out.println(currentUser.getUserName() + currentUser.getUserHistoriesList().size());
        return currentUser;
    }

    protected void saveNewUserDataToFileStream(User user) {
        if (isNewUser) {
            usersList.add(user);
        } else {
            usersList.get(usersList.indexOf(user)).setUserHistoriesList(user.getUserHistoriesList());
        }
        try {
            this.userDataFileOutputStream = new FileOutputStream("userDataFile.raidata");
            oos = new ObjectOutputStream(userDataFileOutputStream);
            oos.writeObject(usersList);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void deleteUserGameHistory(User user) {
        usersList.get(usersList.indexOf(user)).deleteUserHistoriesList();
        saveNewUserDataToFileStream(user);
    }
}
