package servlet;

import model.Cart;
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
        
        // Manual JSON generation for the active cart
        StringBuilder json = new StringBuilder("{");
        json.append("\"total\":").append(cart.getTotal()).append(",");
        json.append("\"items\":[");
        
        List<Product> items = cart.getItems();
        for (int i = 0; i < items.size(); i++) {
            Product p = items.get(i);
            json.append("{")
                .append("\"id\":").append(p.getId()).append(",")
                .append("\"name\":\"").append(p.getName()).append("\",")
                .append("\"price\":").append(p.getPrice())
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
        String returnUrl = request.getParameter("returnUrl");
        
        if (action != null && productIdStr != null) {
            int productId = Integer.parseInt(productIdStr);
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");
            
            if (cart == null) {
                cart = new Cart();
                session.setAttribute("cart", cart);
            }
            
            if (action.equals("add")) {
                Product p = ProductDatabase.getProductById(productId);
                if (p != null) {
                    cart.addItem(p);
                }
            } else if (action.equals("remove")) {
                cart.removeItem(productId);
            }
        }
        
        if (returnUrl != null && !returnUrl.isEmpty()) {
            response.sendRedirect(returnUrl);
        } else {
            response.sendRedirect("cart.html");
        }
    }
}
