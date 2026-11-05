const { users } = require('./db');

module.exports = (req, res) => {
  let body = req.body || {};
  if (typeof body === 'string') {
    try { body = JSON.parse(body); } catch (e) {
      body = Object.fromEntries(new URLSearchParams(body));
    }
  }

  const username = (body.username || req.query.username || '').trim();
  const password = (body.password || req.query.password || '').trim();
  const email = (body.email || req.query.email || `${username}@example.com`).trim();
  const phone = (body.phone || req.query.phone || '+91 98450 00000').trim();

  if (!username || !password) {
    return res.redirect(302, '/register.html?error=invalid');
  }

  const exists = users.find(u => u.username.toLowerCase() === username.toLowerCase());
  if (exists) {
    return res.redirect(302, '/register.html?error=exists');
  }

  users.push({
    username,
    password,
    email,
    phone,
    role: 'CUSTOMER',
    registeredDate: new Date().toISOString().substring(0, 10),
    orders: []
  });

  return res.redirect(302, '/login.html?success=registered');
};
