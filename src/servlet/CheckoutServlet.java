package servlet;

import model.Cart;
import model.Product;
import model.User;
import model.UserDatabase;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        User sessionUser = (User) session.getAttribute("user");
        
        if (sessionUser != null && cart != null && !cart.getItems().isEmpty()) {
            User dbUser = UserDatabase.getUser(sessionUser.getUsername());
            if (dbUser != null) {
                String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
                String itemsStr = cart.getItems().stream().map(Product::getName).collect(Collectors.joining(", "));
                String orderNote = String.format("Order placed on %s. Total: \u20B9%,.2f. Items: %s", dateStr, cart.getTotal(), itemsStr);
                dbUser.addOrder(orderNote);
            }
        }
        
        // Only remove the cart, but NOT invalidate the entire session (otherwise they get logged out!)
        session.removeAttribute("cart");
        
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html lang='en'><head><meta charset='UTF-8'><title>Order Confirmation | Jyothy Basket</title>");
        out.println("<link rel='stylesheet' href='css/style.css'><script src='js/main.js'></script></head><body>");
        out.println("<header><h1>Jyothy Basket</h1><nav id='nav-links'></nav></header>");
        out.println("<main><div class='checkout-form' style='text-align:center;'>");
        out.println("<h2 style='color:#22c55e; margin-top:0;'>Order Placed Successfully!</h2>");
        out.println("<p style='color:#334155; font-size: 1.1rem;'>Thank you, <strong>" + request.getParameter("name") + "</strong>! Your order has been securely processed.</p>");
        out.println("<p style='color:#64748b; margin-bottom: 2rem;'><strong>Shipping to:</strong> " + request.getParameter("address") + "</p>");
        out.println("<a href='portfolio.html' class='btn'>View My Portfolio</a>");
        out.println("</div></main></body></html>");
    }
}
