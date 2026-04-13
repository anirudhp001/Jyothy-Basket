package model;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> items;
    
    public Cart() {
        items = new ArrayList<>();
    }
    
    public void addItem(Product p) {
        items.add(p);
    }
    
    public void removeItem(int productId) {
        items.removeIf(p -> p.getId() == productId);
    }
    
    public List<Product> getItems() {
        return items;
    }
    
    public double getTotal() {
        return items.stream().mapToDouble(Product::getPrice).sum();
    }
}
