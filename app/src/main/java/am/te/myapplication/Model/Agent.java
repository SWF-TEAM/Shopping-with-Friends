package am.te.myapplication.Model;

/**
 * The parent of all agentic organisms in the application.
 *
 * @author Mitchell Manguno
 * @version 1.0
 * @since 2015 March 18
 */
public abstract class Agent {

    private static User loggedIn;
    private static String uniqueIDofCurrentlyLoggedIn;
    private String email;
    private String username;
    private String password;
    private String name;
    private String id;
    private boolean isBanned;

    public static String getUniqueIDofCurrentlyLoggedIn() {
        return uniqueIDofCurrentlyLoggedIn;
    }

    public static void setUniqueIDofCurrentlyLoggedIn(String param) {
        uniqueIDofCurrentlyLoggedIn = param;
    }

    public static User getLoggedIn() {
        return loggedIn;
    }

    public static void setLoggedIn(User loggedInParam) {
        loggedIn = loggedInParam;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

}
