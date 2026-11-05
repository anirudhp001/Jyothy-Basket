package model;

import java.util.List;

public class Order {
    private String orderId;
    private String customerUsername;
    private String customerName;
    private String deliveryAddress;
    private String phone;
    private List<OrderItem> items;
    private double totalAmount;
    private String orderDate;
    private String estimatedDelivery;
    private String status; // "Order Placed", "Processing", "Out for Delivery", "Delivered", "Cancelled"

    public Order(String orderId, String customerUsername, String customerName, String deliveryAddress, String phone,
                 List<OrderItem> items, double totalAmount, String orderDate, String estimatedDelivery, String status) {
        this.orderId = orderId;
        this.customerUsername = customerUsername;
        this.customerName = customerName;
        this.deliveryAddress = deliveryAddress;
        this.phone = phone;
        this.items = items;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.estimatedDelivery = estimatedDelivery;
        this.status = status;
    }

    public String getOrderId() { return orderId; }
    public String getCustomerUsername() { return customerUsername; }
    public String getCustomerName() { return customerName; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public String getPhone() { return phone; }
    public List<OrderItem> getItems() { return items; }
    public double getTotalAmount() { return totalAmount; }
    public String getOrderDate() { return orderDate; }
    public String getEstimatedDelivery() { return estimatedDelivery; }
    public String getStatus() { return status; }

    public synchronized void setStatus(String status) {
        this.status = status;
    }

    public int getStageIndex() {
        switch (status) {
            case "Order Placed":
                return 1;
            case "Processing":
                return 2;
            case "Out for Delivery":
                return 3;
            case "Delivered":
                return 4;
            default:
                return 1;
        }
    }
}
