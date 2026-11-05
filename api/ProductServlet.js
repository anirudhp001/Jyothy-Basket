const { products } = require('./db');

module.exports = (req, res) => {
  res.setHeader('Content-Type', 'application/json');
  const { category } = req.query;
  if (category && category !== 'All') {
    const filtered = products.filter(p => p.category.toLowerCase() === category.toLowerCase());
    return res.status(200).json(filtered);
  }
  return res.status(200).json(products);
};
