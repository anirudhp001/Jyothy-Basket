package model;

public class OrderItem {
    private int productId;
    private String productName;
    private int quantity;
    private String unit;
    private double price;
    private double subtotal;

    public OrderItem(int productId, String productName, int quantity, String unit, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
        this.subtotal = price * quantity;
    }

    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public String getUnit() { return unit; }
    public double getPrice() { return price; }
    public double getSubtotal() { return subtotal; }
}
