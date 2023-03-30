package Model;

public class User {

    private int userID;
    private String userName;

    public User() {
        userID = -1;
        userName = "";
    }
    public User(int userID, String userName) {
        this.userID = userID;
        this.userName = userName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String toString() {
        return this.userName + " " + this.userID;
    }
}
