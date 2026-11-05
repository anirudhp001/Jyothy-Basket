const { orders, users } = require('./db');

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
  const { action, orderId } = req.query;

  if (action === 'myOrders') {
    const user = getUser(req);
    if (!user) {
      return res.status(200).json([]);
    }
    const myOrders = orders.filter(o => o.customerUsername.toLowerCase() === user.username.toLowerCase());
    return res.status(200).json(myOrders);
  }

  if (!orderId) {
    return res.status(400).json({ error: 'Order ID required' });
  }

  const order = orders.find(o => o.orderId.toUpperCase() === orderId.trim().toUpperCase());
  if (!order) {
    return res.status(404).json({ error: 'Order not found' });
  }

  return res.status(200).json(order);
};
