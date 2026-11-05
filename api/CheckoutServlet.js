const { products, users, orders, getCart, recalculateCart, generateOrderId } = require('./db');

function getSessionId(req) {
  let cookie = req.headers.cookie || '';
  let match = cookie.match(/jb_sid=([^;]+)/);
  return match ? match[1] : 'default';
}

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
  if (req.method !== 'POST') {
    return res.redirect(302, '/cart.html');
  }

  let body = req.body || {};
  if (typeof body === 'string') {
    try { body = JSON.parse(body); } catch (e) {
      body = Object.fromEntries(new URLSearchParams(body));
    }
  }

  const sid = getSessionId(req);
  const cart = getCart(sid);

  if (!cart.items || cart.items.length === 0) {
    return res.redirect(302, '/cart.html');
  }

  // Pre-checkout stock check
  for (const item of cart.items) {
    const p = products.find(prod => prod.id === item.id);
    if (!p || p.stock < item.quantity) {
      res.setHeader('Content-Type', 'text/html');
      return res.status(200).send(`
        <!DOCTYPE html><html><head><title>Item Unavailable</title><link rel="stylesheet" href="/css/style.css"></head>
        <body><header><h1>Jyothy Basket</h1></header><main><div class="checkout-form" style="text-align:center;">
          <h2>Item Unavailable</h2><p>Sorry, ${item.name} is currently out of stock.</p>
          <a href="/cart.html" class="btn">Return to Cart</a>
        </div></main></body></html>
      `);
    }
  }

  const user = getUser(req);
  const customerName = (body.name || (user ? user.username : 'Guest Customer')).trim();
  const address = (body.address || 'Local Delivery Address').trim();
  const phone = (body.phone || '+91 98765 43210').trim();

  // Deduct inventory
  const orderItems = [];
  cart.items.forEach(item => {
    const p = products.find(prod => prod.id === item.id);
    if (p) p.stock -= item.quantity;
    orderItems.push({
      productId: item.id,
      productName: item.name,
      quantity: item.quantity,
      unit: item.unit,
      price: item.price,
      subtotal: item.subtotal
    });
  });

  const now = new Date().toISOString().replace('T', ' ').substring(0, 16);
  const deliveryDate = new Date(Date.now() + 2 * 24 * 60 * 60 * 1000).toLocaleDateString('en-IN', { day: '2-digit', month: 'short', year: 'numeric' });
  const orderId = generateOrderId();

  const newOrder = {
    orderId,
    customerUsername: user ? user.username : 'guest',
    customerName,
    deliveryAddress: address,
    phone,
    orderDate: now,
    estimatedDelivery: deliveryDate,
    status: 'Order Placed',
    stageIndex: 1,
    totalAmount: cart.total,
    items: orderItems
  };

  orders.unshift(newOrder);

  if (user) {
    user.orders.unshift(`[${orderId}] ${now} — ₹${cart.total.toFixed(2)} — ${cart.itemCount} item(s)`);
  }

  // Clear cart
  cart.items = [];
  recalculateCart(cart);

  // Return confirmation HTML page
  res.setHeader('Content-Type', 'text/html; charset=UTF-8');
  return res.status(200).send(`
    <!DOCTYPE html><html lang="en"><head><meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Confirmed | Jyothy Basket</title>
    <link rel="stylesheet" href="/css/style.css"><script src="/js/main.js"></script></head><body>
    <header><h1>Jyothy Basket</h1><nav id="nav-links"></nav></header>
    <main><div class="checkout-form" style="text-align:center; max-width:620px; margin: 0 auto;">
      <div style="font-size:4rem; margin-bottom:1rem;">✅</div>
      <h2 style="color:#22c55e; margin-top:0; font-size:1.75rem;">Order Placed Successfully!</h2>
      <p style="color:var(--text-secondary); font-size:1.05rem;">Thank you, <strong style="color:var(--text-primary);">${customerName}</strong>! Your grocery order is confirmed.</p>
      <div style="background:var(--bg-surface); border:1px solid var(--border-color); border-radius:12px; padding:1.5rem; margin:1.5rem 0; text-align:left;">
        <p style="margin:0.4rem 0;"><strong>🧾 Order ID:</strong> <span style="font-family:monospace; font-size:1.15rem; color:var(--accent); font-weight:700;">${orderId}</span></p>
        <p style="margin:0.4rem 0;"><strong>💰 Total Paid:</strong> ₹${newOrder.totalAmount.toFixed(2)}</p>
        <p style="margin:0.4rem 0;"><strong>📍 Shipping To:</strong> ${address}</p>
        <p style="margin:0.4rem 0;"><strong>📞 Phone:</strong> ${phone}</p>
        <p style="margin:0.4rem 0;"><strong>🚚 Estimated Delivery:</strong> ${deliveryDate}</p>
      </div>
      <div style="display:flex; gap:1rem; justify-content:center; flex-wrap:wrap;">
        <a href="/track-order.html?orderId=${orderId}" class="btn"><span>🔍 Live Order Tracking</span></a>
        <a href="/index.html" class="btn" style="background:transparent; border:1px solid var(--border-color); color:var(--text-primary);"><span>Continue Shopping</span></a>
      </div>
    </div></main></body></html>
  `);
};
