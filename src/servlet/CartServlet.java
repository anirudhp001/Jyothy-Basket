package servlet;

import model.Cart;
import model.CartItem;
import model.Product;
import model.ProductDatabase;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        StringBuilder json = new StringBuilder("{");
        json.append("\"total\":").append(cart.getTotal()).append(",");
        json.append("\"itemCount\":").append(cart.getTotalItemCount()).append(",");
        json.append("\"items\":[");

        List<CartItem> items = cart.getItems();
        for (int i = 0; i < items.size(); i++) {
            CartItem ci = items.get(i);
            Product p = ci.getProduct();
            json.append("{")
                .append("\"id\":").append(p.getId()).append(",")
                .append("\"name\":\"").append(p.getName().replace("\"", "\\\"")).append("\",")
                .append("\"price\":").append(p.getPrice()).append(",")
                .append("\"unit\":\"").append(p.getUnit()).append("\",")
                .append("\"imageUrl\":\"").append(p.getImageUrl()).append("\",")
                .append("\"stock\":").append(p.getStock()).append(",")
                .append("\"quantity\":").append(ci.getQuantity()).append(",")
                .append("\"subtotal\":").append(ci.getSubtotal())
                .append("}");
            if (i < items.size() - 1) json.append(",");
        }
        json.append("]}");

        out.print(json.toString());
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String productIdStr = request.getParameter("productId");
        String quantityStr = request.getParameter("quantity");
        String returnUrl = request.getParameter("returnUrl");

        if (action != null && productIdStr != null) {
            try {
                int productId = Integer.parseInt(productIdStr);
                int qty = (quantityStr != null && !quantityStr.isEmpty()) ? Integer.parseInt(quantityStr) : 1;

                HttpSession session = request.getSession();
                Cart cart = (Cart) session.getAttribute("cart");
                if (cart == null) {
                    cart = new Cart();
                    session.setAttribute("cart", cart);
                }

                if ("add".equalsIgnoreCase(action)) {
                    Product p = ProductDatabase.getProductById(productId);
                    if (p != null) {
                        cart.addItem(p, qty);
                    }
                } else if ("remove".equalsIgnoreCase(action)) {
                    cart.removeItem(productId);
                } else if ("update".equalsIgnoreCase(action)) {
                    cart.updateQuantity(productId, qty);
                }
            } catch (NumberFormatException ignored) {}
        }

        if (returnUrl != null && !returnUrl.isEmpty()) {
            response.sendRedirect(returnUrl);
        } else {
            response.sendRedirect("cart.html");
        }
    }
}
