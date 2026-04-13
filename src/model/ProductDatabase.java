package model;
import java.util.ArrayList;
import java.util.List;

public class ProductDatabase {
    private static List<Product> products = new ArrayList<>();
    
    static {
        // ── Electronics ──
        products.add(new Product(101, "Smartphone Pro Max", 69999.00, "Latest 5G flagship with 120Hz AMOLED display and pro-grade 200MP cameras.", "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?auto=format&fit=crop&q=80&w=400", 4.8, "Electronics"));
        products.add(new Product(102, "UltraHD Smart TV 55\"", 32999.00, "Immersive 4K experience with Dolby Vision and built-in streaming apps.", "https://images.unsplash.com/photo-1593359677879-a4bb92f829d1?auto=format&fit=crop&q=80&w=400", 4.5, "Electronics"));
        products.add(new Product(103, "Wireless Noise-Canceling Headphones", 14999.00, "Industry-leading ANC with 30-hour battery and Hi-Res Audio.", "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?auto=format&fit=crop&q=80&w=400", 4.7, "Electronics"));
        products.add(new Product(109, "Bluetooth Portable Speaker", 3499.00, "Waterproof 20W speaker with deep bass and 12-hour playtime.", "https://images.unsplash.com/photo-1608043152269-423dbba4e7e1?auto=format&fit=crop&q=80&w=400", 4.4, "Electronics"));
        products.add(new Product(110, "Wireless Gaming Mouse", 2499.00, "Ultra-fast 1ms response time with customizable RGB lighting.", "https://images.unsplash.com/photo-1527814050087-3793815479db?auto=format&fit=crop&q=80&w=400", 4.3, "Electronics"));
        products.add(new Product(111, "Mechanical Gaming Keyboard", 4499.00, "Cherry MX switches with per-key RGB and aluminum frame.", "https://images.unsplash.com/photo-1618384887929-16ec33fab9ef?auto=format&fit=crop&q=80&w=400", 4.6, "Electronics"));
        products.add(new Product(112, "Smartwatch Fitness Tracker", 8999.00, "AMOLED display with SpO2, heart rate, GPS and 7-day battery.", "https://images.unsplash.com/photo-1579586337278-3befd40fd17a?auto=format&fit=crop&q=80&w=400", 4.5, "Electronics"));
        products.add(new Product(113, "Laptop Ultrabook 14\"", 52999.00, "Intel i7, 16GB RAM, 512GB SSD in a sleek aluminum body.", "https://images.unsplash.com/photo-1496181133206-80ce9b88a853?auto=format&fit=crop&q=80&w=400", 4.7, "Electronics"));
        products.add(new Product(114, "Tablet 10.5\" Display", 22999.00, "Crisp 2K display with stylus support and split-screen multitasking.", "https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?auto=format&fit=crop&q=80&w=400", 4.4, "Electronics"));

        // ── Fashion ──
        products.add(new Product(105, "Mens Casual Sneakers", 2999.00, "Lightweight and breathable for daily wear with memory foam sole.", "https://images.unsplash.com/photo-1542291026-7eec264c27ff?auto=format&fit=crop&q=80&w=400", 4.1, "Fashion"));
        products.add(new Product(106, "Womens Designer Handbag", 5999.00, "Elegant vegan leather bag with spacious compartments.", "https://images.unsplash.com/photo-1548036328-c9fa89d128fa?auto=format&fit=crop&q=80&w=400", 4.6, "Fashion"));
        products.add(new Product(115, "Classic Aviator Sunglasses", 1299.00, "UV400 polarized lenses with lightweight metal frame.", "https://images.unsplash.com/photo-1572635196237-14b3f281503f?auto=format&fit=crop&q=80&w=400", 4.2, "Fashion"));
        products.add(new Product(116, "Mens Leather Wallet", 899.00, "Genuine leather bi-fold wallet with RFID protection.", "https://images.unsplash.com/photo-1627123424574-724758594e93?auto=format&fit=crop&q=80&w=400", 4.3, "Fashion"));
        products.add(new Product(117, "Womens Running Shoes", 3499.00, "Responsive cushioning with breathable knit upper.", "https://images.unsplash.com/photo-1595950653106-6c9ebd614d3a?auto=format&fit=crop&q=80&w=400", 4.5, "Fashion"));

        // ── Furniture ──
        products.add(new Product(104, "Ergonomic Office Chair", 11999.00, "Premium lumbar support and breathable mesh material.", "https://images.unsplash.com/photo-1580480055273-228ff5388ef8?auto=format&fit=crop&q=80&w=400", 4.3, "Furniture"));
        products.add(new Product(118, "Minimalist Wooden Desk", 8999.00, "Solid pine wood desk with cable management and drawer.", "https://images.unsplash.com/photo-1518455027359-f3f8164ba6bd?auto=format&fit=crop&q=80&w=400", 4.4, "Furniture"));

        // ── Appliances ──
        products.add(new Product(108, "Automatic Coffee Maker", 4999.00, "Brews a perfect cup every morning with a programmable timer.", "https://images.unsplash.com/photo-1517668808822-9ebb02f2a0e6?auto=format&fit=crop&q=80&w=400", 4.4, "Appliances"));
        products.add(new Product(119, "Air Purifier with HEPA Filter", 7499.00, "Removes 99.97% of allergens and pollutants silently.", "https://images.unsplash.com/photo-1585771724684-38269d6639fd?auto=format&fit=crop&q=80&w=400", 4.6, "Appliances"));
        products.add(new Product(120, "Electric Kettle 1.5L", 1299.00, "Rapid boil stainless steel kettle with auto shut-off.", "https://images.unsplash.com/photo-1556228578-8c89e6adf883?auto=format&fit=crop&q=80&w=400", 4.2, "Appliances"));

        // ── Grocery ──
        products.add(new Product(107, "Organic Green Tea (100 Bags)", 349.00, "Pure, antioxidant-rich green tea from premium Darjeeling gardens.", "https://images.unsplash.com/photo-1556881286-fc6915169721?auto=format&fit=crop&q=80&w=400", 4.9, "Grocery"));
        products.add(new Product(121, "Cold Pressed Extra Virgin Olive Oil 1L", 599.00, "First cold pressed from Mediterranean olives.", "https://images.unsplash.com/photo-1474979266404-7eaacbcd87c5?auto=format&fit=crop&q=80&w=400", 4.7, "Grocery"));
        products.add(new Product(122, "Premium Basmati Rice 5kg", 449.00, "Aged long-grain basmati rice with rich aroma.", "https://images.unsplash.com/photo-1536304929831-ee1ca9d44906?auto=format&fit=crop&q=80&w=400", 4.8, "Grocery"));

        // ── Books ──
        products.add(new Product(123, "The Psychology of Money", 299.00, "Timeless lessons on wealth, greed, and happiness by Morgan Housel.", "https://images.unsplash.com/photo-1524995997946-a1c2e315a42f?auto=format&fit=crop&q=80&w=400", 4.8, "Books"));
        products.add(new Product(124, "Atomic Habits", 349.00, "Proven framework for building good habits by James Clear.", "https://images.unsplash.com/photo-1497633762265-9d179a990aa6?auto=format&fit=crop&q=80&w=400", 4.9, "Books"));

        // ── Sports ──
        products.add(new Product(125, "Yoga Mat Premium 6mm", 999.00, "Non-slip eco-friendly TPE mat with carrying strap.", "https://images.unsplash.com/photo-1601925260368-ae2f83cf8b7f?auto=format&fit=crop&q=80&w=400", 4.5, "Sports"));
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
}
