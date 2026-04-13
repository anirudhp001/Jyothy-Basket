package model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private List<String> orderHistory;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.orderHistory = new ArrayList<>();
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public List<String> getOrderHistory() { return orderHistory; }
    public void addOrder(String orderDetails) {
        orderHistory.add(orderDetails);
    }
}
