package echo;

import java.util.HashMap;
import java.util.Map;

public class SecurityProxy extends ProxyHandler {
    private static UserTable userTable;
    private boolean loggedIn = false;
    private String currentUser = null;

    public SecurityProxy() {
        userTable = UserTable.getInstance();
    }

    @Override
    protected String response(String msg) throws Exception {
        String[] parts = msg.trim().split("\\s+", 3);

        if (parts.length >= 3 && "new".equals(parts[0])) {
            // Handle new user registration
            String username = parts[1];
            String password = parts[2];

            if (userTable.hasUser(username)) {
                return "ERROR: User already exists";
            } else {
                userTable.addUser(username, password);
                return "User " + username + " created successfully. Session ended.";
            }
        } else if (parts.length >= 3 && "login".equals(parts[0])) {
            // Handle login request
            String username = parts[1];
            String password = parts[2];

            if (userTable.verifyUser(username, password)) {
                loggedIn = true;
                currentUser = username;
                return "Login successful. You may now send requests.";
            } else {
                loggedIn = false;
                return "ERROR: Invalid username or password. Session terminated.";
            }
        } else if (loggedIn) {
            // Forward request to peer if logged in
            peer.send(msg);
            return peer.receive();
        } else {
            // Not logged in and not a login/new command
            return "ERROR: You must login first. Session terminated.";
        }
    }
}

/**
 * Singleton class that maintains the user table with usernames and passwords.
 */
class UserTable {
    private static UserTable instance;
    private Map<String, String> users; // Maps usernames to passwords

    private UserTable() {
        users = new HashMap<>();
        // Initialize with default users
        users.put("jones", "abc");
        users.put("smith", "xyz");
    }

    public static UserTable getInstance() {
        if (instance == null) {
            instance = new UserTable();
        }
        return instance;
    }

    public boolean hasUser(String username) {
        return users.containsKey(username);
    }

    public void addUser(String username, String password) {
        users.put(username, password);
        System.out.println("Added user: " + username);
    }

    public boolean verifyUser(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }
}