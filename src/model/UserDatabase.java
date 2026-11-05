package model;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UserDatabase {
    // Thread-safe user storage
    private static final Map<String, User> users = new ConcurrentHashMap<>();
    
    static {
        // Pre-populate admin and a sample customer
        User admin = new User("admin", "admin123", "ADMIN", "admin@jyothybasket.com", "+91 80012 34567");
        User sampleCustomer = new User("anirudh", "ani123", "CUSTOMER", "anirudh@example.com", "+91 98765 43210");
        
        users.put(admin.getUsername().toLowerCase(), admin);
        users.put(sampleCustomer.getUsername().toLowerCase(), sampleCustomer);
    }
    
    public static boolean registerUser(String username, String password) {
        return registerUser(username, password, username + "@example.com", "+91 90000 00000");
    }

    public static boolean registerUser(String username, String password, String email, String phone) {
        if (username == null || username.trim().isEmpty()) return false;
        String key = username.trim().toLowerCase();
        if (users.containsKey(key)) {
            return false;
        }
        User newUser = new User(username.trim(), password, "CUSTOMER", email, phone);
        users.put(key, newUser);
        return true;
    }
    
    public static User authenticateParams(String username, String password) {
        if (username == null || password == null) return null;
        User user = users.get(username.trim().toLowerCase());
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    
    public static User getUser(String username) {
        if (username == null) return null;
        return users.get(username.trim().toLowerCase());
    }

    public static List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}
