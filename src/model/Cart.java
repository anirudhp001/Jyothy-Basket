package model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;
    
    public Cart() {
        items = new ArrayList<>();
    }
    
    public synchronized boolean addItem(Product p, int qty) {
        if (p == null || qty <= 0) return false;
        
        for (CartItem item : items) {
            if (item.getProduct().getId() == p.getId()) {
                int newQty = item.getQuantity() + qty;
                if (newQty > p.getStock()) {
                    newQty = p.getStock(); // Cap to available stock
                }
                item.setQuantity(newQty);
                return true;
            }
        }
        
        // Not in cart yet
        if (p.getStock() >= qty) {
            items.add(new CartItem(p, qty));
            return true;
        } else if (p.isInStock()) {
            items.add(new CartItem(p, p.getStock()));
            return true;
        }
        return false;
    }

    public synchronized boolean addItem(Product p) {
        return addItem(p, 1);
    }
    
    public synchronized void removeItem(int productId) {
        items.removeIf(item -> item.getProduct().getId() == productId);
    }

    public synchronized void updateQuantity(int productId, int qty) {
        if (qty <= 0) {
            removeItem(productId);
            return;
        }
        for (CartItem item : items) {
            if (item.getProduct().getId() == productId) {
                int capped = Math.min(qty, item.getProduct().getStock());
                item.setQuantity(capped);
                return;
            }
        }
    }
    
    public List<CartItem> getItems() {
        return items;
    }
    
    public double getTotal() {
        return items.stream().mapToDouble(CartItem::getSubtotal).sum();
    }

    public int getTotalItemCount() {
        return items.stream().mapToInt(CartItem::getQuantity).sum();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }
}
