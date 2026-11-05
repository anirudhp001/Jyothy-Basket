package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
    private String username;
    private String password;
    private String role; // "ADMIN" or "CUSTOMER"
    private String email;
    private String phone;
    private String registeredDate;
    private List<String> orderHistory;

    public User(String username, String password) {
        this(username, password, "CUSTOMER", username + "@example.com", "+91 98450 12345");
    }

    public User(String username, String password, String role, String email, String phone) {
        this.username = username;
        this.password = password;
        this.role = role != null ? role.toUpperCase() : "CUSTOMER";
        this.email = email;
        this.phone = phone;
        this.registeredDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        this.orderHistory = new ArrayList<>();
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getRegisteredDate() { return registeredDate; }
    public List<String> getOrderHistory() { return orderHistory; }

    public void addOrder(String orderDetails) {
        orderHistory.add(orderDetails);
    }

    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(role);
    }
}
