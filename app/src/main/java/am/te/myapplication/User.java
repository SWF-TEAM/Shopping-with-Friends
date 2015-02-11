package am.te.myapplication;

/**
 * Created by elimonent on 2/9/15.
 */
public class User {
    private String username;
    private String password;
    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String pass) {
        this.password = pass;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof User)) {
            return false;
        }

        if (other == this) {
            return true;
        }
        User theOther = (User) other;
        return theOther.getUsername().equals(this.getUsername()) && theOther.getPassword().equals(this.getPassword());
    }
    @Override
    public int hashCode() {
        int hash = 5;
        hash += 37 * hash + getUsername().hashCode();
        hash += 37 * hash + getPassword().hashCode();
        return hash;
    }
    @Override
    public String toString() {
        return getUsername() + " " + getPassword();
    }
}

