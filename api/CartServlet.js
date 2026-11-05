const { products, getCart, recalculateCart } = require('./db');

function getSessionId(req, res) {
  let cookie = req.headers.cookie || '';
  let match = cookie.match(/jb_sid=([^;]+)/);
  if (match) return match[1];
  let sid = 's_' + Math.random().toString(36).substring(2) + Date.now();
  res.setHeader('Set-Cookie', `jb_sid=${sid}; Path=/; HttpOnly; SameSite=Lax`);
  return sid;
}

module.exports = (req, res) => {
  const sid = getSessionId(req, res);
  const cart = getCart(sid);

  if (req.method === 'GET') {
    res.setHeader('Content-Type', 'application/json');
    return res.status(200).json(cart);
  }

  if (req.method === 'POST') {
    let body = req.body || {};
    if (typeof body === 'string') {
      try { body = JSON.parse(body); } catch (e) {
        body = Object.fromEntries(new URLSearchParams(body));
      }
    }
    const action = body.action || req.query.action;
    const productId = parseInt(body.productId || req.query.productId);
    const quantity = parseInt(body.quantity || req.query.quantity || 1);

    if (action === 'add' && productId) {
      const prod = products.find(p => p.id === productId);
      if (prod && prod.stock > 0) {
        let item = cart.items.find(ci => ci.id === productId);
        if (item) {
          item.quantity = Math.min(item.quantity + quantity, prod.stock);
        } else {
          cart.items.push({
            id: prod.id,
            name: prod.name,
            price: prod.price,
            unit: prod.unit,
            imageUrl: prod.imageUrl,
            stock: prod.stock,
            quantity: Math.min(quantity, prod.stock),
            subtotal: prod.price * Math.min(quantity, prod.stock)
          });
        }
      }
    } else if (action === 'remove' && productId) {
      cart.items = cart.items.filter(ci => ci.id !== productId);
    } else if (action === 'update' && productId) {
      if (quantity <= 0) {
        cart.items = cart.items.filter(ci => ci.id !== productId);
      } else {
        let item = cart.items.find(ci => ci.id === productId);
        if (item) {
          const prod = products.find(p => p.id === productId);
          const maxStock = prod ? prod.stock : 99;
          item.quantity = Math.min(quantity, maxStock);
        }
      }
    }

    recalculateCart(cart);

    if (req.headers['content-type']?.includes('application/x-www-form-urlencoded') && body.returnUrl) {
      return res.redirect(302, body.returnUrl);
    }
    return res.status(200).json({ success: true, cart });
  }

  return res.status(405).json({ error: 'Method not allowed' });
};
