const { users } = require('./db');

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
  if (user) {
    return res.status(200).json({
      loggedIn: true,
      username: user.username,
      role: user.role,
      email: user.email,
      orders: user.orders || []
    });
  }
  return res.status(200).json({ loggedIn: false });
};
