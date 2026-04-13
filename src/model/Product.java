package model;

public class Product {
    private int id;
    private String name;
    private double price;
    private String description;
    private String imageUrl;
    private double rating;
    private String category;
    
    public Product(int id, String name, double price, String description, String imageUrl, double rating, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.category = category;
    }
    
    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public double getRating() { return rating; }
    public String getCategory() { return category; }
}
