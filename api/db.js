// In-memory shared data store for Vercel serverless deployment
// Mirrors Java model databases (ProductDatabase, UserDatabase, OrderDatabase)

let products = [
  // Fresh Produce
  { id: 201, name: "Organic Shimla Apples", price: 180.0, description: "Crisp, sweet, and naturally grown apples handpicked from Himalayan orchards.", imageUrl: "https://images.unsplash.com/photo-1560806887-1e4cd0b6cbd6?auto=format&fit=crop&q=80&w=400", rating: 4.8, category: "Fresh Produce", stock: 25, unit: "1 kg" },
  { id: 202, name: "Fresh Cavendish Bananas", price: 55.0, description: "Naturally ripened, potassium-rich fresh yellow bananas.", imageUrl: "https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?auto=format&fit=crop&q=80&w=400", rating: 4.7, category: "Fresh Produce", stock: 40, unit: "1 Dozen" },
  { id: 203, name: "Farm Fresh Hybrid Tomatoes", price: 35.0, description: "Firm, juicy red tomatoes freshly harvested from local organic farms.", imageUrl: "https://images.unsplash.com/photo-1592924357228-91a4daadcfea?auto=format&fit=crop&q=80&w=400", rating: 4.5, category: "Fresh Produce", stock: 50, unit: "1 kg" },
  { id: 204, name: "Tender Baby Spinach (Palak)", price: 30.0, description: "Cleaned, pesticide-free baby spinach leaves packed with iron and vitamins.", imageUrl: "https://images.unsplash.com/photo-1576045057995-568f588f82fb?auto=format&fit=crop&q=80&w=400", rating: 4.6, category: "Fresh Produce", stock: 30, unit: "250 g" },
  { id: 205, name: "Fresh Red Onions", price: 42.0, description: "Premium quality pungent onions essential for everyday Indian cooking.", imageUrl: "https://images.unsplash.com/photo-1618512496248-a07fe83aa8cb?auto=format&fit=crop&q=80&w=400", rating: 4.4, category: "Fresh Produce", stock: 45, unit: "1 kg" },
  { id: 206, name: "Fresh Green Bell Peppers", price: 60.0, description: "Crunchy and vibrant green capsicum rich in Vitamin C and antioxidants.", imageUrl: "https://images.unsplash.com/photo-1563565375-f3fdfdbefa83?auto=format&fit=crop&q=80&w=400", rating: 4.5, category: "Fresh Produce", stock: 20, unit: "500 g" },

  // Dairy & Bakery
  { id: 207, name: "Farm Fresh Whole Milk", price: 68.0, description: "Pasteurized, homogenized whole cow milk rich in calcium and natural proteins.", imageUrl: "https://images.unsplash.com/photo-1550583724-b2692b85b150?auto=format&fit=crop&q=80&w=400", rating: 4.9, category: "Dairy & Bakery", stock: 35, unit: "1 L" },
  { id: 208, name: "Organic Brown Eggs", price: 95.0, description: "Free-range farm fresh brown eggs high in Omega-3 and protein.", imageUrl: "https://images.unsplash.com/photo-1582722872445-44dc5f7e3c8f?auto=format&fit=crop&q=80&w=400", rating: 4.8, category: "Dairy & Bakery", stock: 30, unit: "Pack of 6" },
  { id: 209, name: "Artisanal Sourdough Whole Bread", price: 65.0, description: "Freshly baked preservative-free whole wheat bread with seeds.", imageUrl: "https://images.unsplash.com/photo-1509440159596-0249088772ff?auto=format&fit=crop&q=80&w=400", rating: 4.7, category: "Dairy & Bakery", stock: 15, unit: "400 g" },
  { id: 210, name: "Pure Salted Butter", price: 125.0, description: "Creamy, golden table butter churned from pure cow milk cream.", imageUrl: "https://images.unsplash.com/photo-1589985270826-4b7bb135bc9d?auto=format&fit=crop&q=80&w=400", rating: 4.9, category: "Dairy & Bakery", stock: 22, unit: "200 g" },
  { id: 211, name: "Greek Style Plain Curd / Yogurt", price: 75.0, description: "Thick, probiotic-rich natural yogurt for digestive health.", imageUrl: "https://images.unsplash.com/photo-1488477181946-6428a0291777?auto=format&fit=crop&q=80&w=400", rating: 4.6, category: "Dairy & Bakery", stock: 18, unit: "400 g" },

  // Pantry & Staples
  { id: 212, name: "Royal Aged Basmati Rice", price: 480.0, description: "Long-grain aromatic aged basmati rice perfect for biryanis and pulao.", imageUrl: "https://images.unsplash.com/photo-1586201375761-83865001e31c?auto=format&fit=crop&q=80&w=400", rating: 4.9, category: "Pantry & Staples", stock: 25, unit: "5 kg" },
  { id: 213, name: "Cold-Pressed Extra Virgin Olive Oil", price: 649.0, description: "First cold-pressed Mediterranean olive oil for heart-healthy cooking.", imageUrl: "https://images.unsplash.com/photo-1474979266404-7eaacbcd87c5?auto=format&fit=crop&q=80&w=400", rating: 4.8, category: "Pantry & Staples", stock: 16, unit: "1 L" },
  { id: 214, name: "Organic Sharbati Whole Wheat Atta", price: 290.0, description: "Stone ground 100% whole grain wheat flour for extra soft rotis.", imageUrl: "https://images.unsplash.com/photo-1574323347407-f5e1ad6d020b?auto=format&fit=crop&q=80&w=400", rating: 4.7, category: "Pantry & Staples", stock: 20, unit: "5 kg" },
  { id: 215, name: "Traditional Bilona Desi Cow Ghee", price: 720.0, description: "Aromatic, golden Vedic ghee prepared using traditional wooden churns.", imageUrl: "https://plus.unsplash.com/premium_photo-1723672873127-7b8b8f9f1411?auto=format&fit=crop&q=80&w=400", rating: 4.9, category: "Pantry & Staples", stock: 12, unit: "500 ml" },
  { id: 216, name: "Organic Toor Dal (Pigeon Pea)", price: 145.0, description: "Unpolished, nutrient-dense yellow lentils rich in dietary fiber and protein.", imageUrl: "https://plus.unsplash.com/premium_photo-1700842181817-e7f4c9fa828a?auto=format&fit=crop&q=80&w=400", rating: 4.6, category: "Pantry & Staples", stock: 30, unit: "1 kg" },
  { id: 217, name: "Natural Pink Himalayan Rock Salt", price: 85.0, description: "Pure, unrefined mineral-rich pink salt for healthy cooking.", imageUrl: "https://images.unsplash.com/photo-1518110925495-5fe2fda0442c?auto=format&fit=crop&q=80&w=400", rating: 4.7, category: "Pantry & Staples", stock: 40, unit: "1 kg" },

  // Beverages & Snacks
  { id: 218, name: "Darjeeling Pure Green Tea", price: 299.0, description: "Antioxidant-rich whole leaf green tea directly sourced from Darjeeling.", imageUrl: "https://images.unsplash.com/photo-1576092768241-dec231879fc3?auto=format&fit=crop&q=80&w=400", rating: 4.8, category: "Beverages & Snacks", stock: 28, unit: "100 Tea Bags" },
  { id: 219, name: "Coorg Premium Roast Coffee Beans", price: 340.0, description: "Medium-dark roasted Arabica beans with hints of cocoa and caramel.", imageUrl: "https://images.unsplash.com/photo-1514432324607-a09d9b4aefdd?auto=format&fit=crop&q=80&w=400", rating: 4.7, category: "Beverages & Snacks", stock: 15, unit: "250 g" },
  { id: 220, name: "California Raw Almonds (Badam)", price: 420.0, description: "Crunchy, premium graded whole almonds packed with healthy fats.", imageUrl: "https://images.unsplash.com/photo-1508779018996-601e37fa274e?auto=format&fit=crop&q=80&w=400", rating: 4.8, category: "Beverages & Snacks", stock: 25, unit: "500 g" },
  { id: 221, name: "Raw Multi-Flora Forest Honey", price: 249.0, description: "100% pure unfiltered wild forest honey rich in natural pollen.", imageUrl: "https://images.unsplash.com/photo-1558642452-9d2a7deb7f62?auto=format&fit=crop&q=80&w=400", rating: 4.9, category: "Beverages & Snacks", stock: 18, unit: "500 g" }
];

let users = [
  { username: "admin", password: "admin123", email: "admin@jyothybasket.com", phone: "+91 99999 00000", role: "ADMIN", registeredDate: "2026-01-01", orders: [] },
  { username: "anirudh", password: "ani123", email: "anirudh@example.com", phone: "+91 98765 43210", role: "CUSTOMER", registeredDate: "2026-02-15", orders: ["[ORD-1001] 2026-11-05 09:30 — ₹591.00 — 5 item(s)"] }
];

let orders = [
  {
    orderId: "ORD-1001",
    customerUsername: "anirudh",
    customerName: "Anirudh P",
    deliveryAddress: "42, Green Park Avenue, Bangalore, Karnataka - 560068",
    phone: "+91 98765 43210",
    orderDate: "2026-11-05 09:30",
    estimatedDelivery: "Today by 2:00 PM",
    status: "Processing",
    stageIndex: 2,
    totalAmount: 591.0,
    items: [
      { productId: 201, productName: "Organic Shimla Apples", quantity: 2, unit: "1 kg", price: 180.0, subtotal: 360.0 },
      { productId: 207, productName: "Farm Fresh Whole Milk", quantity: 2, unit: "1 L", price: 68.0, subtotal: 136.0 },
      { productId: 208, productName: "Organic Brown Eggs", quantity: 1, unit: "Pack of 6", price: 95.0, subtotal: 95.0 }
    ]
  }
];

// In-memory cart store per session ID
let carts = {};
let orderCounter = 1001;

function getCart(sessionId) {
  if (!carts[sessionId]) {
    carts[sessionId] = { items: [], total: 0, itemCount: 0 };
  }
  return carts[sessionId];
}

function recalculateCart(cart) {
  let total = 0;
  let count = 0;
  cart.items.forEach(ci => {
    ci.subtotal = ci.price * ci.quantity;
    total += ci.subtotal;
    count += ci.quantity;
  });
  cart.total = total;
  cart.itemCount = count;
}

module.exports = {
  products,
  users,
  orders,
  getCart,
  recalculateCart,
  generateOrderId: () => {
    orderCounter++;
    return "ORD-" + orderCounter;
  }
};
