package am.te.myapplication.model;


/**
 * The parent of all agents in the application.
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

    public String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getName() { return name;}

    void setName() { this.name = name;}

    void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

}
