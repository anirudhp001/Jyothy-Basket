module.exports = (req, res) => {
  res.setHeader('Set-Cookie', 'jb_user=; Path=/; Expires=Thu, 01 Jan 1970 00:00:00 GMT');
  return res.redirect(302, '/index.html');
};
