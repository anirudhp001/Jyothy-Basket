const { products, users, orders } = require('./db');

function getUser(req) {
  let cookie = req.headers.cookie || '';
  let match = cookie.match(/jb_user=([^;]+)/);
  if (match) {
    const uname = decodeURIComponent(match[1]);
    return users.find(u => u.username === uname);
  }
  return null;
}

module.exports = (req, res) => {
  res.setHeader('Content-Type', 'application/json');
  const user = getUser(req);

  if (!user || user.role !== 'ADMIN') {
    return res.status(403).json({ error: 'Access denied' });
  }

  if (req.method === 'GET') {
    const { action } = req.query;

    if (action === 'summary') {
      const lowStock = products.filter(p => p.stock < 5).length;
      const outOfStock = products.filter(p => p.stock === 0).length;
      const pending = orders.filter(o => o.status !== 'Delivered' && o.status !== 'Cancelled').length;
      const revenue = orders.filter(o => o.status === 'Delivered').reduce((sum, o) => sum + o.totalAmount, 0);

      return res.status(200).json({
        totalOrders: orders.length,
        pendingOrders: pending,
        totalCustomers: users.length,
        totalProducts: products.length,
        lowStockProducts: lowStock,
        outOfStockProducts: outOfStock,
        totalRevenue: revenue
      });
    }

    if (action === 'orders') {
      return res.status(200).json(orders.map(o => ({
        orderId: o.orderId,
        customerUsername: o.customerUsername,
        customerName: o.customerName,
        orderDate: o.orderDate,
        totalAmount: o.totalAmount,
        status: o.status,
        itemCount: o.items.length
      })));
    }

    if (action === 'inventory') {
      return res.status(200).json(products.map(p => ({
        id: p.id,
        name: p.name,
        category: p.category,
        unit: p.unit,
        price: p.price,
        stock: p.stock,
        inStock: p.stock > 0
      })));
    }

    if (action === 'customers') {
      return res.status(200).json(users.map(u => ({
        username: u.username,
        email: u.email,
        phone: u.phone,
        role: u.role,
        registeredDate: u.registeredDate,
        totalOrders: u.orders ? u.orders.length : 0
      })));
    }

    return res.status(400).json({ error: 'Unknown action' });
  }

  if (req.method === 'POST') {
    let body = req.body || {};
    if (typeof body === 'string') {
      try { body = JSON.parse(body); } catch (e) {
        body = Object.fromEntries(new URLSearchParams(body));
      }
    }

    const action = body.action || req.query.action;

    if (action === 'updateStatus') {
      const { orderId, status } = body;
      const order = orders.find(o => o.orderId === orderId);
      if (order) {
        order.status = status;
        const stageMap = { 'Order Placed': 1, 'Processing': 2, 'Out for Delivery': 3, 'Delivered': 4, 'Cancelled': 0 };
        order.stageIndex = stageMap[status] || 1;
        return res.status(200).json({ success: true });
      }
      return res.status(404).json({ success: false, message: 'Order not found' });
    }

    if (action === 'addStock') {
      const productId = parseInt(body.productId);
      const quantity = parseInt(body.quantity);
      const prod = products.find(p => p.id === productId);
      if (prod) {
        prod.stock += quantity;
        return res.status(200).json({ success: true, newStock: prod.stock });
      }
      return res.status(404).json({ success: false, message: 'Product not found' });
    }

    return res.status(400).json({ success: false, message: 'Unknown action' });
  }

  return res.status(405).json({ error: 'Method not allowed' });
};
