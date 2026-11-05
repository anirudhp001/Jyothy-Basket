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

  const user = users.find(u => u.username.toLowerCase() === username.toLowerCase() && u.password === password);

  if (user) {
    res.setHeader('Set-Cookie', `jb_user=${encodeURIComponent(user.username)}; Path=/; HttpOnly; SameSite=Lax`);
    if (user.role === 'ADMIN') {
      return res.redirect(302, '/admin.html');
    }
    return res.redirect(302, '/index.html');
  }

  return res.redirect(302, '/login.html?error=true');
};
