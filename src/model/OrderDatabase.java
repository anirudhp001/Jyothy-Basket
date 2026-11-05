package model;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class OrderDatabase {
    private static final Map<String, Order> orders = new ConcurrentHashMap<>();
    private static int orderCounter = 1000;

    static {
        // Seed initial sample order for demonstration
        List<OrderItem> sampleItems = new ArrayList<>();
        sampleItems.add(new OrderItem(201, "Organic Shimla Apples", 2, "1 kg", 180.00));
        sampleItems.add(new OrderItem(207, "Farm Fresh Whole Milk", 2, "1 L", 68.00));
        sampleItems.add(new OrderItem(208, "Organic Brown Eggs", 1, "Pack of 6", 95.00));
        
        Order sampleOrder = new Order(
            "ORD-1001",
            "admin",
            "Anirudh P",
            "42, Green Park Avenue, Bangalore, Karnataka - 560068",
            "+91 98765 43210",
            sampleItems,
            591.00,
            "2026-11-05 09:30",
            "Today by 2:00 PM",
            "Processing"
        );
        orders.put(sampleOrder.getOrderId(), sampleOrder);
    }

    public static synchronized String generateNextOrderId() {
        orderCounter++;
        return "ORD-" + orderCounter;
    }

    public static void saveOrder(Order order) {
        orders.put(order.getOrderId(), order);
    }

    public static Order getOrder(String orderId) {
        if (orderId == null) return null;
        return orders.get(orderId.trim().toUpperCase());
    }

    public static List<Order> getOrdersByUser(String username) {
        List<Order> userOrders = new ArrayList<>();
        if (username == null) return userOrders;
        for (Order o : orders.values()) {
            if (username.equalsIgnoreCase(o.getCustomerUsername())) {
                userOrders.add(o);
            }
        }
        // Sort newest first
        userOrders.sort((a, b) -> b.getOrderId().compareTo(a.getOrderId()));
        return userOrders;
    }

    public static List<Order> getAllOrders() {
        List<Order> all = new ArrayList<>(orders.values());
        all.sort((a, b) -> b.getOrderId().compareTo(a.getOrderId()));
        return all;
    }

    public static boolean updateOrderStatus(String orderId, String newStatus) {
        Order order = getOrder(orderId);
        if (order != null) {
            order.setStatus(newStatus);
            return true;
        }
        return false;
    }
}
