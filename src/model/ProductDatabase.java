package model;
import java.util.ArrayList;
import java.util.List;

public class ProductDatabase {
    private static List<Product> products = new ArrayList<>();
    
    static {
        // ── Fresh Produce ──
        products.add(new Product(201, "Organic Shimla Apples", 180.00, "Crisp, sweet, and naturally grown apples handpicked from Himalayan orchards.", "https://images.unsplash.com/photo-1560806887-1e4cd0b6cbd6?auto=format&fit=crop&q=80&w=400", 4.8, "Fresh Produce", 25, "1 kg"));
        products.add(new Product(202, "Fresh Cavendish Bananas", 55.00, "Naturally ripened, potassium-rich fresh yellow bananas.", "https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?auto=format&fit=crop&q=80&w=400", 4.7, "Fresh Produce", 40, "1 Dozen"));
        products.add(new Product(203, "Farm Fresh Hybrid Tomatoes", 35.00, "Firm, juicy red tomatoes freshly harvested from local organic farms.", "https://images.unsplash.com/photo-1592924357228-91a4daadcfea?auto=format&fit=crop&q=80&w=400", 4.5, "Fresh Produce", 50, "1 kg"));
        products.add(new Product(204, "Tender Baby Spinach (Palak)", 30.00, "Cleaned, pesticide-free baby spinach leaves packed with iron and vitamins.", "https://images.unsplash.com/photo-1576045057995-568f588f82fb?auto=format&fit=crop&q=80&w=400", 4.6, "Fresh Produce", 30, "250 g"));
        products.add(new Product(205, "Fresh Red Onions", 42.00, "Premium quality pungent onions essential for everyday Indian cooking.", "https://images.unsplash.com/photo-1618512496248-a07fe83aa8cb?auto=format&fit=crop&q=80&w=400", 4.4, "Fresh Produce", 45, "1 kg"));
        products.add(new Product(206, "Fresh Green Bell Peppers", 60.00, "Crunchy and vibrant green capsicum rich in Vitamin C and antioxidants.", "https://images.unsplash.com/photo-1563565375-f3fdfdbefa83?auto=format&fit=crop&q=80&w=400", 4.5, "Fresh Produce", 20, "500 g"));

        // ── Dairy & Bakery ──
        products.add(new Product(207, "Farm Fresh Whole Milk", 68.00, "Pasteurized, homogenized whole cow milk rich in calcium and natural proteins.", "https://images.unsplash.com/photo-1550583724-b2692b85b150?auto=format&fit=crop&q=80&w=400", 4.9, "Dairy & Bakery", 35, "1 L"));
        products.add(new Product(208, "Organic Brown Eggs", 95.00, "Free-range farm fresh brown eggs high in Omega-3 and protein.", "https://images.unsplash.com/photo-1516467508483-a7212febe31a?auto=format&fit=crop&q=80&w=400", 4.8, "Dairy & Bakery", 30, "Pack of 6"));
        products.add(new Product(209, "Artisanal Sourdough Whole Bread", 65.00, "Freshly baked preservative-free whole wheat bread with seeds.", "https://images.unsplash.com/photo-1509440159596-0249088772ff?auto=format&fit=crop&q=80&w=400", 4.7, "Dairy & Bakery", 15, "400 g"));
        products.add(new Product(210, "Pure Salted Butter", 125.00, "Creamy, golden table butter churned from pure cow milk cream.", "https://images.unsplash.com/photo-1589985270826-4b7bb135bc9d?auto=format&fit=crop&q=80&w=400", 4.9, "Dairy & Bakery", 22, "200 g"));
        products.add(new Product(211, "Greek Style Plain Curd / Yogurt", 75.00, "Thick, probiotic-rich natural yogurt for digestive health.", "https://images.unsplash.com/photo-1488477181946-6428a0291777?auto=format&fit=crop&q=80&w=400", 4.6, "Dairy & Bakery", 18, "400 g"));

        // ── Pantry & Cooking Staples ──
        products.add(new Product(212, "Royal Aged Basmati Rice", 480.00, "Long-grain aromatic aged basmati rice perfect for biryanis and pulao.", "https://images.unsplash.com/photo-1586201375761-83865001e31c?auto=format&fit=crop&q=80&w=400", 4.9, "Pantry & Staples", 25, "5 kg"));
        products.add(new Product(213, "Cold-Pressed Extra Virgin Olive Oil", 649.00, "First cold-pressed Mediterranean olive oil for heart-healthy cooking.", "https://images.unsplash.com/photo-1474979266404-7eaacbcd87c5?auto=format&fit=crop&q=80&w=400", 4.8, "Pantry & Staples", 16, "1 L"));
        products.add(new Product(214, "Organic Sharbati Whole Wheat Atta", 290.00, "Stone ground 100% whole grain wheat flour for extra soft rotis.", "https://images.unsplash.com/photo-1509440159596-0249088772ff?auto=format&fit=crop&q=80&w=400", 4.7, "Pantry & Staples", 20, "5 kg"));
        products.add(new Product(215, "Traditional Bilona Desi Cow Ghee", 720.00, "Aromatic, golden Vedic ghee prepared using traditional wooden churns.", "https://images.unsplash.com/photo-1628088062854-d1870b4553da?auto=format&fit=crop&q=80&w=400", 4.9, "Pantry & Staples", 12, "500 ml"));
        products.add(new Product(216, "Organic Toor Dal (Pigeon Pea)", 145.00, "Unpolished, nutrient-dense yellow lentils rich in dietary fiber and protein.", "https://images.unsplash.com/photo-1585994192701-f1a505c817ea?auto=format&fit=crop&q=80&w=400", 4.6, "Pantry & Staples", 30, "1 kg"));
        products.add(new Product(217, "Natural Pink Himalayan Rock Salt", 85.00, "Pure, unrefined mineral-rich pink salt for healthy cooking.", "https://images.unsplash.com/photo-1518110925495-5fe2fda0442c?auto=format&fit=crop&q=80&w=400", 4.7, "Pantry & Staples", 40, "1 kg"));

        // ── Snacks & Beverages ──
        products.add(new Product(218, "Darjeeling Pure Green Tea", 299.00, "Antioxidant-rich whole leaf green tea directly sourced from Darjeeling.", "https://images.unsplash.com/photo-1556881286-fc6915169721?auto=format&fit=crop&q=80&w=400", 4.8, "Beverages & Snacks", 28, "100 Tea Bags"));
        products.add(new Product(219, "Coorg Premium Roast Coffee Beans", 340.00, "Medium-dark roasted Arabica beans with hints of cocoa and caramel.", "https://images.unsplash.com/photo-1514432324607-a09d9b4aefdd?auto=format&fit=crop&q=80&w=400", 4.7, "Beverages & Snacks", 15, "250 g"));
        products.add(new Product(220, "California Raw Almonds (Badam)", 420.00, "Crunchy, premium graded whole almonds packed with healthy fats.", "https://images.unsplash.com/photo-1508061252224-b045430b31ef?auto=format&fit=crop&q=80&w=400", 4.8, "Beverages & Snacks", 25, "500 g"));
        products.add(new Product(221, "Raw Multi-Flora Forest Honey", 249.00, "100% pure unfiltered wild forest honey rich in natural pollen.", "https://images.unsplash.com/photo-1587049352846-4a222e784d38?auto=format&fit=crop&q=80&w=400", 4.9, "Beverages & Snacks", 18, "500 g"));
    }
    
    public static List<Product> getAllProducts() {
        return products;
    }
    
    public static Product getProductById(int id) {
        for (Product p : products) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public static synchronized boolean updateProductStock(int id, int newStock) {
        Product p = getProductById(id);
        if (p != null) {
            p.setStock(newStock);
            return true;
        }
        return false;
    }

    public static synchronized boolean addProductStock(int id, int quantity) {
        Product p = getProductById(id);
        if (p != null) {
            p.addStock(quantity);
            return true;
        }
        return false;
    }
}
