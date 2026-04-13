package model;

import java.util.HashMap;
import java.util.Map;

public class UserDatabase {
    // In-memory user database
    private static Map<String, User> users = new HashMap<>();
    
    static {
        // Pre-populate with a test user
        users.put("admin", new User("admin", "admin123"));
    }
    
    public static boolean registerUser(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }
        users.put(username, new User(username, password));
        return true;
    }
    
    public static User authenticateParams(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    
    public static User getUser(String username) {
        return users.get(username);
    }
}
