package model;

public class Product {
    private int id;
    private String name;
    private double price;
    private String description;
    private String imageUrl;
    private double rating;
    private String category;
    private int stock;
    private String unit;
    
    public Product(int id, String name, double price, String description, String imageUrl, double rating, String category, int stock, String unit) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.category = category;
        this.stock = stock;
        this.unit = unit;
    }
    
    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public double getRating() { return rating; }
    public String getCategory() { return category; }
    public int getStock() { return stock; }
    public String getUnit() { return unit; }

    public synchronized void setStock(int stock) {
        this.stock = Math.max(0, stock);
    }

    public synchronized boolean reduceStock(int qty) {
        if (stock >= qty) {
            stock -= qty;
            return true;
        }
        return false;
    }

    public synchronized void addStock(int qty) {
        if (qty > 0) {
            stock += qty;
        }
    }

    public boolean isInStock() {
        return stock > 0;
    }
}
